package bupt.edu.jhc.apicloud_backend.model.dto.api_interface;

import lombok.Data;

/**
 * @Description: 创建接口请求
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@Data
public class CreateInterfaceReq {
    private String name;
    private String description;
    private String host;
    private String url;
    private String method;
    private String requestParams;
    private String requestHeader;
    private String responseHeader;
}
