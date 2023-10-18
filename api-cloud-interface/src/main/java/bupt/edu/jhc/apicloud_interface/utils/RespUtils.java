package bupt.edu.jhc.apicloud_interface.utils;

import bupt.edu.jhc.apicloud_client_sdk.common.domain.enums.SDKErrorStatus;
import bupt.edu.jhc.apicloud_client_sdk.common.domain.resp.SDKCommonResp;
import bupt.edu.jhc.apicloud_client_sdk.common.exception.SDKException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * @Description: 响应工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/18
 */
public class RespUtils {
    public static Map<String, Object> respToMap(String response) {
        return new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static <T> SDKCommonResp getResp(String baseUrl, T params) {
        String resp = null;
        try {
            resp = ReqUtils.get(baseUrl, params);
            Map<String, Object> fromResp = respToMap(resp);
            boolean success = (boolean) fromResp.get("success");
            SDKCommonResp baseResp = new SDKCommonResp();
            if (!success) {
                baseResp.setData(fromResp);
                return baseResp;
            }
            fromResp.remove("success");
            baseResp.setData(fromResp);
            return baseResp;
        } catch (SDKException e) {
            throw new SDKException(SDKErrorStatus.OPERATION_ERROR, "构建url异常");
        }
    }
}
