package bupt.edu.jhc.apicloud.config;

import bupt.edu.jhc.apicloud.client.ApiCloudClient;
import bupt.edu.jhc.apicloud.common.constants.SDKConstants;
import bupt.edu.jhc.apicloud.service.IApiService;
import bupt.edu.jhc.apicloud.service.impl.ApiServiceImpl;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 客户端配置类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/11
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "apicloud.client")
@ComponentScan
public class ApiCloudClientConfig {
    private String accessKey;
    private String secretKey;
    private String gatewayHost;

    @Bean
    public ApiCloudClient qiApiClient() {
        return new ApiCloudClient(accessKey, secretKey);
    }

    @Bean
    public IApiService apiService() {
        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.setGatewayHost(StrUtil.isNotBlank(gatewayHost) ? gatewayHost: SDKConstants.DEFAULT_GATEWAY_HOST);
        apiService.setApiCloudClient(new ApiCloudClient(accessKey, secretKey));
        return apiService;
    }
}
