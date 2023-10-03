package bupt.edu.jhc.apicloud.service.impl;

import bupt.edu.jhc.apicloud.model.vo.auth.AuthInfo;
import bupt.edu.jhc.apicloud.service.IAuthService;
import bupt.edu.jhc.apicloud.utils.JwtUtils;
import org.springframework.stereotype.Service;

/**
 * @Description: 权限服务实现
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@Service
public class AuthServiceImpl implements IAuthService {
    @Override
    public AuthInfo getTwoTokens(Long userId) {
        return AuthInfo.builder()
                .accessToken(JwtUtils.generateAToken(userId))
                .refreshToken(JwtUtils.generateRToken(userId))
                .build();
    }
}
