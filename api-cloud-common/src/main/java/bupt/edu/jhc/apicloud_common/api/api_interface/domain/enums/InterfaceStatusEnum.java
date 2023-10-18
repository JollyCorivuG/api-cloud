package bupt.edu.jhc.apicloud_common.api.api_interface.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 接口状态枚举
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Getter
@AllArgsConstructor
public enum InterfaceStatusEnum {
    CLOSE(0, "接口关闭"),
    OPEN(1, "接口开放"),
    ;

    private final Integer status;
    private final String desc;
}
