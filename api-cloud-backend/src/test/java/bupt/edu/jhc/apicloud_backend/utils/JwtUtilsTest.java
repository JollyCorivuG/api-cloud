package bupt.edu.jhc.apicloud_backend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description: Jwt 工具类测试
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@SpringBootTest
public class JwtUtilsTest {
    @Test
    void testGenerate() {
        // 正常生成 token
        System.out.println("得到 token 使用的 userId: 2");
        String token = JwtUtils.generateRToken(1L);
        System.out.println("生成的 token: " + token);
        System.out.println("解析 token 后得到的 userId: " + JwtUtils.getUIdFromToken(token));
    }
}
