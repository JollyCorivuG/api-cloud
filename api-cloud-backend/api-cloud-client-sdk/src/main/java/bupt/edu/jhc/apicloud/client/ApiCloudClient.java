package bupt.edu.jhc.apicloud.client;

import bupt.edu.jhc.apicloud.model.User;
import bupt.edu.jhc.apicloud.utils.SignUtils;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 客户端
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/5
 */
public class ApiCloudClient {
    private static final String API_URL = "http://localhost:8082/api";
    private final String accessKey;
    private final String secretKey;

    public ApiCloudClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private Map<String, String> buildReqHeader(String body) {
        Map<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);
//        header.put("secretKey", secretKey); // 由于secretKey是私密信息，不应该在请求头中传输，所以这里注释掉
        header.put("nonce", RandomUtil.randomNumbers(4));
        header.put("body", body);
        header.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        header.put("sign", SignUtils.generateSign(body, secretKey));
        return header;
    }

    public String getUserNameByPOST(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse resp = HttpRequest.post(API_URL + "/user/name")
                .addHeaders(buildReqHeader(json))
                .body(json)
                .execute();
        return resp.body();
    }
}
