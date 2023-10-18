package bupt.edu.jhc.apicloud_backend.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 展示的用户信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private String phone;
    private String nickName;
    private String avatar;
    private Integer status;
    private String role;
    private String accessKey;
    private String secretKey;
}
