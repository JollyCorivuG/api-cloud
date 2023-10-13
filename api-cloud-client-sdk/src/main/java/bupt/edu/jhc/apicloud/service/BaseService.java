package bupt.edu.jhc.apicloud.service;

import bupt.edu.jhc.apicloud.client.ApiCloudClient;
import bupt.edu.jhc.apicloud.common.domain.enums.ErrorStatus;
import bupt.edu.jhc.apicloud.common.domain.enums.ReqMethodEnum;
import bupt.edu.jhc.apicloud.common.domain.resp.SDKCommonResp;
import bupt.edu.jhc.apicloud.common.domain.resp.SDKErrorResp;
import bupt.edu.jhc.apicloud.common.exception.SDKException;
import bupt.edu.jhc.apicloud.model.req.BaseReq;
import bupt.edu.jhc.apicloud.utils.SignUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 基础服务抽象类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/11
 */
@Data
@Slf4j
public abstract class BaseService implements IApiService {
    private ApiCloudClient apiCloudClient;
    private String gatewayHost;

    /**
     * 检查配置
     * @param apiCloudClient
     */
    private void checkConfig(ApiCloudClient apiCloudClient) {
        if (apiCloudClient == null && this.getApiCloudClient() == null) {
            throw new SDKException(ErrorStatus.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
        }
        if (apiCloudClient != null && StrUtil.isNotBlank(apiCloudClient.getAccessKey()) && StrUtil.isNotBlank(apiCloudClient.getSecretKey())) {
            this.setApiCloudClient(apiCloudClient);
        }
    }

    /**
     * 根据请求路径获取真实的 host
     * @param path
     * @return
     */
    private String getRealHostByPath(String path) {
        try {
            URL url = new URL(path);
            String host = url.getHost();
            String protocol = url.getProtocol();
            int port = url.getPort();
            if (port != -1) {
                host = host + ":" + port;
            }
            host = protocol + "://" + host;
            return host;
        } catch (MalformedURLException e) {
            throw new SDKException(ErrorStatus.OPERATION_ERROR, e.getMessage());
        }
    }

    /**
     * 构建 GET 请求的 URL
     * @param req
     * @param path
     * @return
     * @param <O>
     * @param <T>
     */
    private <O, T extends SDKCommonResp> String buildGetReqUrl(BaseReq<O, T> req, String path) {
        StringBuilder urlBuilder = new StringBuilder(gatewayHost);
        if (urlBuilder.toString().endsWith("/") && path.startsWith("/")) {
            urlBuilder.setLength(urlBuilder.length() - 1);
        }
        urlBuilder.append(path);
        if (!req.getRequestParams().isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, Object> entry : req.getRequestParams().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                urlBuilder.append(key).append("=").append(value).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        return urlBuilder.toString();
    }

    /**
     * 构建请求头
     * @param body
     * @param apiCloudClient
     * @return
     */
    private Map<String, String> getHeaders(String realHost, String body, ApiCloudClient apiCloudClient) {
        Map<String, String> headers = new HashMap<>(5);
        headers.put("realHost", realHost);
        headers.put("accessKey", apiCloudClient.getAccessKey());
        String encodedBody = SecureUtil.md5(body);
        headers.put("body", encodedBody);
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headers.put("sign", SignUtils.generateSign(encodedBody, apiCloudClient.getSecretKey()));

        return headers;
    }

    private <O, T extends SDKCommonResp> HttpResponse doRequest(BaseReq<O, T> req) throws SDKException {
        try (HttpResponse httpResponse = getHttpReq(req).execute()) {
            return httpResponse;
        } catch (Exception e) {
            throw new SDKException(ErrorStatus.OPERATION_ERROR, e.getMessage());
        }
    }

    private <O, T extends SDKCommonResp> HttpRequest getHttpReq(BaseReq<O, T> req) throws SDKException {
        // 1.打印请求日志
        String reqMethod = req.getMethod().toUpperCase();
        String reqPath = req.getPath();
        if (!reqPath.startsWith("http://") && !reqPath.startsWith("https://")) {
            reqPath = "http://" + reqPath;
        }
        String realHost = getRealHostByPath(reqPath);
        Map<String, Object> reqParams = req.getRequestParams();
        log.info("请求方法：{}，请求路径：{}，请求参数：{}", reqMethod, reqPath, reqParams);

        // 2.构建请求
        HttpRequest httpRequest;
        switch (ReqMethodEnum.of(reqMethod)) {
            case GET: {
                httpRequest = HttpRequest.get(buildGetReqUrl(req, reqPath.substring(realHost.length())));
                break;
            }
            case POST: {
                httpRequest = HttpRequest.post(gatewayHost + reqPath.substring(realHost.length()));
                httpRequest.body(JSONUtil.toJsonStr(req.getRequestParams()));
                break;
            }
            default: {
                throw new SDKException(ErrorStatus.OPERATION_ERROR, "不支持的请求类型!");
            }
        }

        // 3.添加请求头
        return httpRequest.addHeaders(getHeaders(realHost, JSONUtil.toJsonStr(req), apiCloudClient));
    }


    @Override
    public <O, T extends SDKCommonResp> T request(BaseReq<O, T> req) throws SDKException {
        if (apiCloudClient == null || StrUtil.isBlank(apiCloudClient.getAccessKey()) || StrUtil.isBlank(apiCloudClient.getSecretKey())) {
            throw new SDKException(ErrorStatus.NO_AUTH_ERROR, "请先配置密钥AccessKey/SecretKey");
        }
        T resp;
        try {
            resp = req.getResponseClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new SDKException(ErrorStatus.SYSTEM_ERROR, "实例化响应对象失败");
        }
        HttpResponse httpResponse = doRequest(req);
        String body = httpResponse.body();
        Map<String, Object> data = new HashMap<>();
        if (httpResponse.getStatus() != HttpStatus.HTTP_OK) {
            SDKErrorResp errorResp = JSONUtil.toBean(body, SDKErrorResp.class);
            data.put("errorMessage", errorResp.getMsg());
            data.put("code", errorResp.getCode());
        } else {
            try {
                // 尝试解析为JSON对象
                data = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {
                }.getType());
            } catch (JsonSyntaxException e) {
                // 解析失败，将body作为普通字符串处理
                data.put("value", body);
            }
        }
        resp.setData(data);
        return resp;
    }

    @Override
    public <O, T extends SDKCommonResp> T request(ApiCloudClient apiCloudClient, BaseReq<O, T> req) throws SDKException {
        checkConfig(apiCloudClient);
        return request(req);
    }
}
