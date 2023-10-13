package bupt.edu.jhc.apicloud.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 错误状态码枚举类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/11
 */
@Getter
@AllArgsConstructor
public enum ErrorStatus {
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    ;

    private final Integer code;
    private final String message;
}