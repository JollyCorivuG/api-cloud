package bupt.edu.jhc.apicloud_backend.controller;

import bupt.edu.jhc.apicloud_backend.common.domain.vo.resp.BasicResponse;
import bupt.edu.jhc.apicloud_backend.model.vo.auth.AuthInfo;
import bupt.edu.jhc.apicloud_backend.service.IAuthService;
import bupt.edu.jhc.apicloud_backend.utils.JwtUtils;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.ThrowUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 认证控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/14
 */
@RestController
@RequestMapping("/capi/auth")
@Tag(name = "用户认证相关接口")
public class AuthController {
    @Resource
    private IAuthService authService;

    @GetMapping("/public/refresh/{rToken}")
    @Operation(summary = "刷新令牌")
    public BasicResponse<AuthInfo> refreshToken(@PathVariable("rToken") String rToken) {
        // 1.判断刷新令牌是否过期, 如果过期就必须重新登录
        ThrowUtils.throwIf(JwtUtils.isTokenExpired(rToken), ErrorStatus.FORBIDDEN_ERROR, "刷新令牌已过期");

        // 2.判断刷新令牌是否达到阈值, 如果达到阈值就重新生成刷新令牌
        Long userId = JwtUtils.getUIdFromToken(rToken);
        if (JwtUtils.isTokenReachThreshold(rToken, JwtUtils.R_TOKEN_THRESHOLD)) {
            return BasicResponse.success(authService.getTwoTokens(userId));
        }

        // 3.没达到阈值就只刷新aToken
        return BasicResponse.success(AuthInfo.builder()
                .accessToken(JwtUtils.generateAToken(userId))
                .refreshToken(rToken)
                .build());
    }
}
