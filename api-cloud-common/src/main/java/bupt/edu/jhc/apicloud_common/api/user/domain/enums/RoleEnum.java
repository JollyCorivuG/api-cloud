package bupt.edu.jhc.apicloud_common.api.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 用户角色枚举
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@AllArgsConstructor
@Getter
public enum RoleEnum {
    USER("user", "普通用户"),
    ADMIN("admin", "管理员"),
    BAN("ban", "封禁用户"),
    ;

    private final String value;
    private final String desc;

}
