package bupt.edu.jhc.apicloud_backend.service.impl;

import bupt.edu.jhc.apicloud_backend.common.constants.RedisConstants;
import bupt.edu.jhc.apicloud_backend.common.constants.SysConstants;
import bupt.edu.jhc.apicloud_backend.dao.user.UserDao;
import bupt.edu.jhc.apicloud_backend.model.dto.user.LoginForm;
import bupt.edu.jhc.apicloud_backend.model.dto.user.RandomUserInfo;
import bupt.edu.jhc.apicloud_backend.model.entity.User;
import bupt.edu.jhc.apicloud_backend.model.vo.auth.AuthInfo;
import bupt.edu.jhc.apicloud_backend.model.vo.user.UserInfo;
import bupt.edu.jhc.apicloud_backend.service.IAuthService;
import bupt.edu.jhc.apicloud_backend.service.IUserService;
import bupt.edu.jhc.apicloud_backend.service.adapter.UserAdapter;
import bupt.edu.jhc.apicloud_backend.utils.RandomUtils;
import bupt.edu.jhc.apicloud_backend.utils.RedisUtils;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.ThrowUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户服务实现
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserDao userDao;
    @Resource
    private IAuthService authService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public AuthInfo login(LoginForm loginForm) {
        // 1.先判断验证码是否正确
        String code = redisUtils.get(RedisConstants.CAPTCHA_PREFIX + loginForm.getCaptchaId().toString(), String.class);
        ThrowUtils.throwIf(code == null, ErrorStatus.PARAMS_ERROR, "验证码过期!");
        ThrowUtils.throwIf(!code.equals(loginForm.getCaptcha()), ErrorStatus.PARAMS_ERROR, "验证码错误!");

        // 2.再通过手机号查询用户
        User user = userDao.getUserByPhone(loginForm.getPhone());
        if (user != null) {
            ThrowUtils.throwIf(user.getStatus().equals(SysConstants.USER_STATUS_BLACK), ErrorStatus.FORBIDDEN_ERROR, "该账号已被封禁!");
            return authService.getTwoTokens(user.getId());
        }

        // 3.如果该手机号未注册, 则直接注册账号
        RandomUserInfo randomUserInfo = RandomUtils.getRandomUser();
        user = User.builder()
                .phone(loginForm.getPhone())
                .nickName(randomUserInfo.getNickName())
                .avatar(randomUserInfo.getAvatar())
                .status(SysConstants.USER_STATUS_NORMAL)
                .build();
        userDao.save(user);

        // 4.返回 token
        return authService.getTwoTokens(user.getId());
    }

    @Override
    public UserInfo getUserInfoById(Long userId) {
        // 1.先从数据库中查询用户
        User user = userDao.getUserById(userId);
        ThrowUtils.throwIf(user == null, ErrorStatus.PARAMS_ERROR, "用户不存在!");

        // 2.转换为 VO 对象返回
        return UserAdapter.buildUserInfoResp(user);
    }

    @Override
    public UserInfo getUserByAccessKey(String accessKey) {
        User user = userDao.getOneByMapper(wrapper -> wrapper.eq(User::getAccessKey, accessKey));
        return user == null ? null : UserAdapter.buildUserInfoResp(user);
    }
}
