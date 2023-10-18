package bupt.edu.jhc.apicloud_common.api.user;

import bupt.edu.jhc.apicloud_common.api.user.domain.dto.UserDTO;

/**
 * @Description: 远程用户服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/15
 */
public interface IRemoteUserService {
    String getUser();

    UserDTO getUserByAccessKey(String accessKey);
}
