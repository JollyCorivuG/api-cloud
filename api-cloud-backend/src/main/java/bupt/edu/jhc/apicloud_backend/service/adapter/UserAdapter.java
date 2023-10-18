package bupt.edu.jhc.apicloud_backend.service.adapter;

import bupt.edu.jhc.apicloud_backend.model.entity.User;
import bupt.edu.jhc.apicloud_backend.model.vo.user.UserInfo;

/**
 * @Description: 用户适配器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
public class UserAdapter {
    public static UserInfo buildUserInfoResp(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .nickName(user.getNickName())
                .avatar(user.getAvatar())
                .status(user.getStatus())
                .role(user.getRole())
                .accessKey(user.getAccessKey())
                .secretKey(user.getSecretKey())
                .build();
    }
}
