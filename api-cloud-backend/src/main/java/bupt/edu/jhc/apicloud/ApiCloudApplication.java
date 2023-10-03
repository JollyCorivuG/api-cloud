package bupt.edu.jhc.apicloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Description: Spring Boot 启动类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/11
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ApiCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiCloudApplication.class, args);
    }
}
