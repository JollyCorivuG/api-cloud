package bupt.edu.jhc.apicloud_gateway;

import bupt.edu.jhc.apicloud_common.api.user.IRemoteUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 网关启动类测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/15
 */
@SpringBootTest
public class GatewayApplicationTests {
    @DubboReference
    private IRemoteUserService remoteUserService;

    @Test
    void testGrpc() {
        System.out.println(remoteUserService.getUser());
    }
}
