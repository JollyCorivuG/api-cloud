package bupt.edu.jhc.apicloud.model.dto.api_interface;

import lombok.Data;

import java.util.Map;

/**
 * @Description: 调用接口请求
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/10
 */
@Data
public class InvokeInterfaceReq {
    private Long interfaceId;
    private Map<String, Object> userReqParams;
}
