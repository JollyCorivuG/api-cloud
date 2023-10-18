package bupt.edu.jhc.apicloud_interface;

import bupt.edu.jhc.apicloud_client_sdk.model.resp.PoisonousChickenSoupResp;
import bupt.edu.jhc.apicloud_client_sdk.service.IApiService;
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
    private IApiService apiService;

    @Test
    void testClientSDK() {
        PoisonousChickenSoupResp resp = null;

        resp = apiService.getPoisonousChickenSoup();
        System.out.println("resp = " + resp);

    }
}
