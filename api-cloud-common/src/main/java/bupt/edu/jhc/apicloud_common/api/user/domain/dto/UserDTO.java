package bupt.edu.jhc.apicloud_common.api.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户数据传输对象
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String phone;
    private String nickName;
    private String avatar;
    private Integer status;
    private String role;
    private String accessKey;
    private String secretKey;
}
