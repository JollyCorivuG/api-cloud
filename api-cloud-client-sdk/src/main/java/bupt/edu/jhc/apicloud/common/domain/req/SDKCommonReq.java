package bupt.edu.jhc.apicloud.common.domain.req;

import bupt.edu.jhc.apicloud.common.domain.resp.SDKCommonResp;
import bupt.edu.jhc.apicloud.model.req.BaseReq;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @Description: 通用请求
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/12
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SDKCommonReq extends BaseReq<Map<String, Object>, SDKCommonResp> {
    private String method;
    private String path;

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Class<SDKCommonResp> getResponseClass() {
        return SDKCommonResp.class;
    }
}
