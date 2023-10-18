package bupt.edu.jhc.apicloud_common.api.api_interface.domain.dto;

import lombok.Data;

/**
 * @Description: 接口请求参数字段
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Data
public class InterfaceReqParamsField {
    private String name;
    private String type;
    private String desc;
    private boolean isRequired;
}
