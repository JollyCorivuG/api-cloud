package bupt.edu.jhc.apicloud.common.constants;

/**
 * @Description: Redis 常量类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/16
 */
public class RedisConstants {
    // 验证码过期时间 60 s
    public static final Integer CAPTCHA_EXPIRE_TIME = 60;
    // 验证码前缀
    public static final String CAPTCHA_PREFIX = "captcha:";
    // 验证码自增 id
    public static final String CAPTCHA_ID_KEY = "captcha:id";

    // 用户调用接口时判断剩余次数的锁的名称
    public static final String INTERFACE_INVOKE_LOCK_PREFIX = "interface:invoke:lock:";
}
