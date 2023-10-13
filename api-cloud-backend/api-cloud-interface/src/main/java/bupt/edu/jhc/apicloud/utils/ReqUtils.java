package bupt.edu.jhc.apicloud.utils;

import bupt.edu.jhc.apicloud.common.domain.enums.ErrorStatus;
import bupt.edu.jhc.apicloud.common.exception.SDKException;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @Description: 请求工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/12
 */
@Slf4j
public class ReqUtils {
    /**
     * 生成 url
     * @param baseUrl
     * @param params
     * @return
     * @param <T>
     * @throws ApiException
     */
    public static <T> String buildUrl(String baseUrl, T params) throws SDKException {
        StringBuilder url = new StringBuilder(baseUrl);
        Field[] fields = params.getClass().getDeclaredFields();
        boolean isFirstParam = true;
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            // 跳过serialVersionUID属性
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            try {
                Object value = field.get(params);
                if (value != null) {
                    if (isFirstParam) {
                        url.append("?").append(name).append("=").append(value);
                        isFirstParam = false;
                    } else {
                        url.append("&").append(name).append("=").append(value);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new SDKException(ErrorStatus.OPERATION_ERROR, "构建 url 异常");
            }
        }
        return url.toString();
    }

    /**
     * get请求
     *
     * @param baseUrl 基本url
     * @param params  params
     * @return {@link String}
     * @throws ApiException api异常
     */
    public static <T> String get(String baseUrl, T params) throws SDKException {
        return get(buildUrl(baseUrl, params));
    }

    /**
     * get请求
     *
     * @param url url
     * @return {@link String}
     */
    public static String get(String url) {
        String body = HttpRequest.get(url).execute().body();
        log.info("【interface】：请求地址：{}，响应数据：{}", url, body);
        return body;
    }
}
