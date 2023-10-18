package bupt.edu.jhc.apicloud_backend.service;

import bupt.edu.jhc.apicloud_backend.model.dto.user.LoginForm;
import bupt.edu.jhc.apicloud_backend.model.vo.auth.AuthInfo;
import bupt.edu.jhc.apicloud_backend.model.vo.user.UserInfo;

/**
 * @Description: 用户服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
public interface IUserService {
    /**
     * 登录
     * @param loginForm
     * @return
     */
    AuthInfo login(LoginForm loginForm);


    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    UserInfo getUserInfoById(Long userId);

    /**
     * 根据用户 accessKey 获取用户信息
     * @param accessKey
     * @return
     */
    UserInfo getUserByAccessKey(String accessKey);
}
