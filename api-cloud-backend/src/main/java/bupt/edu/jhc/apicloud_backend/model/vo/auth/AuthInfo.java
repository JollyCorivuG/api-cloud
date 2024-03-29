package bupt.edu.jhc.apicloud_backend.model.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 认证信息类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo {
    private String accessToken;
    private String refreshToken;
}
