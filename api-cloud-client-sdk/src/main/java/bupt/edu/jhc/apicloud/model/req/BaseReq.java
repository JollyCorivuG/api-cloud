package bupt.edu.jhc.apicloud.model.req;

import bupt.edu.jhc.apicloud.common.domain.resp.SDKCommonResp;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 基础请求抽象类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/12
 */
public abstract class BaseReq<O, T extends SDKCommonResp> {
    private Map<String, Object> requestParams = new HashMap<>();

    /**
     * 获取请求类型
     * @return
     */
    public abstract String getMethod();

    /**
     * 获取请求路径
     * @return
     */
    public abstract String getPath();

    /**
     * 获取响应类
     * @return
     */
    public abstract Class<T> getResponseClass();

    @JsonAnyGetter
    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(O params) {
        requestParams = new Gson().fromJson(
                JSONUtil.toJsonStr(params),
                new TypeToken<Map<String, Object>>() {}.getType()
        );
    }
}
