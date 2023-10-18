package bupt.edu.jhc.apicloud_gateway.filter;

import bupt.edu.jhc.apicloud_client_sdk.utils.SignUtils;
import bupt.edu.jhc.apicloud_common.api.api_interface.IRemoteInterfaceService;
import bupt.edu.jhc.apicloud_common.api.api_interface.domain.dto.InterfaceDTO;
import bupt.edu.jhc.apicloud_common.api.api_interface.domain.dto.InterfaceReqParamsField;
import bupt.edu.jhc.apicloud_common.api.api_interface.domain.enums.InterfaceStatusEnum;
import bupt.edu.jhc.apicloud_common.api.user.IRemoteUserService;
import bupt.edu.jhc.apicloud_common.api.user.domain.dto.UserDTO;
import bupt.edu.jhc.apicloud_common.api.user.domain.enums.RoleEnum;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.ThrowUtils;
import bupt.edu.jhc.apicloud_gateway.constants.GatewayRedisConstants;
import bupt.edu.jhc.apicloud_gateway.utils.RedissonLockUtils;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description: 网关全局过滤器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/13
 */
@Component
@Slf4j
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    private static final long FIVE_MINUTES = 5L * 60;

    @DubboReference
    private IRemoteUserService remoteUserService;

    @DubboReference
    private IRemoteInterfaceService remoteInterfaceService;

    @Resource
    private RedissonLockUtils redissonLockUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一id：" + request.getId());
        log.info("请求参数：" + request.getQueryParams());
        log.info("请求方法：" + request.getMethod());
        log.info("请求路径：" + request.getPath());
        log.info("网关本地地址：" + request.getLocalAddress());
        log.info("请求远程地址：" + request.getRemoteAddress());
        log.info("url:" + request.getURI());
        return verifyParameters(exchange, chain);
    }

    private Mono<Void> verifyParameters(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.校验请求头参数的完整性
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String body = headers.getFirst("body");
        String accessKey = headers.getFirst("accessKey");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        ThrowUtils.throwIf(!StrUtil.isAllNotBlank(body, accessKey, timestamp, sign), ErrorStatus.PARAMS_ERROR, "请求头参数不完整!");

        // 2.校验 timestamp 是否是在五分钟之内的 (防重发 XHR)
        long currentTime = System.currentTimeMillis() / 1000;
        assert timestamp != null;
        ThrowUtils.throwIf(currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES, ErrorStatus.FORBIDDEN_ERROR, "会话已过期!");

        // 3.校验接口
        String totalUrl = request.getURI().toString();
        String url = totalUrl.substring(totalUrl.indexOf("/", 8));
        InterfaceDTO interfaceInfo = remoteInterfaceService.getInterfaceByUrlAndMethod(url, request.getMethod().toString());
        ThrowUtils.throwIf(interfaceInfo == null, ErrorStatus.PARAMS_ERROR, "接口不存在!");
        ThrowUtils.throwIf(interfaceInfo.getStatus().equals(InterfaceStatusEnum.CLOSE.getStatus()), ErrorStatus.OPERATION_ERROR, "接口已下线!");

        // 4.校验用户
        // 4.1用户是否是正常用户
        UserDTO userInfo = remoteUserService.getUserByAccessKey(accessKey);
        ThrowUtils.throwIf(userInfo == null || userInfo.getRole().equals(RoleEnum.BAN.getValue()), ErrorStatus.FORBIDDEN_ERROR, "非法访问!");
        // 4.1用户传入的 secretKey 是否正确
        ThrowUtils.throwIf(!SignUtils.generateSign(body, userInfo.getSecretKey()).equals(sign), ErrorStatus.FORBIDDEN_ERROR, "请传入正确的secretKey!");
        // 4.2用户是否有剩余的调用次数 (需要用到分布式锁)
        Long userId = userInfo.getId();
        redissonLockUtils.distributedLocks(GatewayRedisConstants.INTERFACE_INVOKE_LOCK_PREFIX + userId.toString() + ":" + interfaceInfo.getId().toString(), () -> {
            ThrowUtils.throwIf(!remoteInterfaceService.isLeftInvokeCntEnough(userId, interfaceInfo.getId()), ErrorStatus.OPERATION_ERROR, "您的调用次数已达上限!");
        }, ErrorStatus.OPERATION_ERROR, "调用接口失败!");

        // 5.校验请求参数
        MultiValueMap<String, String> userReqParams = request.getQueryParams();
        String interfaceReqParams = interfaceInfo.getRequestParams();
        if (StrUtil.isNotBlank(interfaceReqParams)) {
            List<InterfaceReqParamsField> params = new Gson().fromJson(interfaceReqParams, new TypeToken<List<InterfaceReqParamsField>>() {
            }.getType());
            for (InterfaceReqParamsField field : params) {
                if (field.isRequired()) {
                    ThrowUtils.throwIf(StrUtil.isBlank(userReqParams.getFirst(field.getName())) || !userReqParams.containsKey(field.getName()),
                            ErrorStatus.PARAMS_ERROR, "请求参数有误!");
                }
            }
        }

        // 6.处理响应
        return handleResponse(exchange, chain, userInfo, interfaceInfo);
    }

    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, UserDTO userInfo, InterfaceDTO interfaceInfo) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        if (originalResponse.getStatusCode() == HttpStatus.OK) {
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                // 等调用完转发的接口后才会执行
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        // 往返回值里写数据
                        return super.writeWith(
                                fluxBody.map(dataBuffer -> {
                                    // TODO 剩余次数减 1 的逻辑
                                    boolean update = remoteInterfaceService.invokeInterfaceSuccess(userInfo.getId(), interfaceInfo.getId());


                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    // 释放掉内存
                                    DataBufferUtils.release(dataBuffer);
                                    String data = new String(content, StandardCharsets.UTF_8);
                                    // 打印日志
                                    log.info("响应结果：" + data);
                                    return bufferFactory.wrap(content);
                                }));
                    } else {
                        log.error("<--- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
