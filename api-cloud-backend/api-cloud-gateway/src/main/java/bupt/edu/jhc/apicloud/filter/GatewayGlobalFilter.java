package bupt.edu.jhc.apicloud.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Description: 网关全局过滤器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/13
 */
@Component
@Slf4j
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    private static final long FIVE_MINUTES = 5L * 60;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 日志
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
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String body = headers.getFirst("body");
        String accessKey = headers.getFirst("accessKey");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");

        // 请求头中参数必须完整
        if (!StrUtil.isAllNotBlank(body, accessKey, timestamp, sign)) {
            // TODO
            log.info("请求头中参数不完整!");
        }

        // 防重发 XHR
        long currentTime = System.currentTimeMillis() / 1000;
//        assert timestamp != null;
//        if (currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
//            // TODO
//            log.info("会话已过期, 请重试!");
//        }

        // TODO 校验用户

        return handleResponse(exchange, chain);

    }

    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        // 缓存数据的工厂
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 拿到响应码
        if (originalResponse.getStatusCode() == HttpStatus.OK) {
            // 装饰，增强能力
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
                        // 8. 调用失败，返回一个规范的错误码
                        log.error("<--- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            // 设置 response 对象为装饰过的
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        // 降级处理返回数据
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
