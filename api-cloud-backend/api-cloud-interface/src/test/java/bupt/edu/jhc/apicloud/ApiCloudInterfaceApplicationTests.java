package bupt.edu.jhc.apicloud;

import bupt.edu.jhc.apicloud.client.ApiCloudClient;
import bupt.edu.jhc.apicloud.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: 接口应用测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/5
 */
@SpringBootTest
public class ApiCloudInterfaceApplicationTests {
    @Resource
    private ApiCloudClient apiCloudClient;

    @Test
    void testClientSDK() {
        User user = new User();
        user.setName("hqx and jhc");
        String resp = apiCloudClient.getUserNameByPOST(user);
        System.out.println("请求结果" + resp);
    }
}
