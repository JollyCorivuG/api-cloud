package bupt.edu.jhc.apicloud;

import bupt.edu.jhc.apicloud.client.ApiCloudClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 客户端配置类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/5
 */
@Configuration
@ConfigurationProperties(prefix = "apicloud.client")
@Data
@ComponentScan
public class ApiCloudClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public ApiCloudClient apiCloudClient() {
        return new ApiCloudClient(accessKey, secretKey);
    }
}
