package bupt.edu.jhc.apicloud_backend.controller;

import bupt.edu.jhc.apicloud_backend.common.constants.RedisConstants;
import bupt.edu.jhc.apicloud_backend.common.domain.vo.resp.BasicResponse;
import bupt.edu.jhc.apicloud_backend.model.dto.user.LoginForm;
import bupt.edu.jhc.apicloud_backend.model.vo.auth.AuthInfo;
import bupt.edu.jhc.apicloud_backend.model.vo.user.CaptchaImgInfo;
import bupt.edu.jhc.apicloud_backend.model.vo.user.UserInfo;
import bupt.edu.jhc.apicloud_backend.service.IUserService;
import bupt.edu.jhc.apicloud_backend.utils.RedisUtils;
import bupt.edu.jhc.apicloud_backend.utils.RequestHolder;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.BizException;
import com.google.code.kaptcha.Producer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 用户控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/16
 */
@RestController
@RequestMapping("/capi/users")
@Tag(name = "用户相关接口")
public class UserController {
    @Resource
    private Producer captchaProducer;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IUserService userService;

    @GetMapping("/public/captcha")
    @Operation(summary = "得到验证码")
    public BasicResponse<CaptchaImgInfo> captcha() {
        // 1.生成验证码并保存到 redis
        String captchaText = captchaProducer.createText();
        Long captchaId = redisUtils.getIncId(RedisConstants.CAPTCHA_ID_KEY);
        redisUtils.setWithExpireTime(RedisConstants.CAPTCHA_PREFIX + captchaId.toString(), captchaText, RedisConstants.CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        BufferedImage captchaImage = captchaProducer.createImage(captchaText);

        // 2.将验证码图片转换为base64
        String base64 = null;
        try {
            File tempFile = Files.createTempFile("captcha", ".png").toFile();
            ImageIO.write(captchaImage, "png", tempFile);
            base64 = "data:image/png;base64," + Base64.encodeBase64String(Files.readAllBytes(tempFile.toPath()));
        } catch (Exception e) {
            throw new BizException(ErrorStatus.SYSTEM_ERROR, "生成验证码失败");
        }

        // 3.返回验证码图片信息
        return BasicResponse.success(CaptchaImgInfo.builder()
                .id(captchaId)
                .base64(base64)
                .width(captchaImage.getWidth())
                .height(captchaImage.getHeight())
                .build());
    }

    @PostMapping("/public/login")
    @Operation(summary = "登录")
    public BasicResponse<AuthInfo> login(@RequestBody LoginForm loginForm) {
        AuthInfo authInfo = userService.login(loginForm);
        return BasicResponse.success(authInfo);
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public BasicResponse<UserInfo> getMe() {
        Long userId = RequestHolder.get().getUid();
        return BasicResponse.success(userService.getUserInfoById(userId));
    }
}
