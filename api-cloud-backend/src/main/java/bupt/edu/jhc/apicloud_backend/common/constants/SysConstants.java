package bupt.edu.jhc.apicloud_backend.common.constants;

/**
 * @Description: 系统常量类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
public class SysConstants {
    // 成功码
    public static final Integer SUCCESS_CODE = 0;
    // No Auth 码
    public static final Integer NO_AUTH_CODE = 401;
    // 默认昵称前缀
    public static final String DEFAULT_NICK_NAME_PREFIX = "user_";
    // 用户状态
    public static final Integer USER_STATUS_NORMAL = 0;
    public static final Integer USER_STATUS_BLACK = 1;

    // 接口状态
    public static final Integer INTERFACE_STATUS_CLOSE = 0;
    public static final Integer INTERFACE_STATUS_OPEN = 1;

    // 调用接口状态
    public static final Integer INVOKE_INTERFACE_STATUS_NORMAL = 0;
    public static final Integer INVOKE_INTERFACE_STATUS_LIMIT = 1;

    // 用户初始调用接口次数
    public static final Integer INIT_INVOKE_CNT = 100;

}
