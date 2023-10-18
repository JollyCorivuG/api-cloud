package bupt.edu.jhc.apicloud_gateway.constants;

/**
 * @Description: redis 常量类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
public class GatewayRedisConstants {
    // 用户调用接口时判断剩余次数的锁的名称
    public static final String INTERFACE_INVOKE_LOCK_PREFIX = "interface:invoke:lock:";
}
