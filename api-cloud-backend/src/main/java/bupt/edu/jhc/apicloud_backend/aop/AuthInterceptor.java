package bupt.edu.jhc.apicloud_backend.aop;

import bupt.edu.jhc.apicloud_backend.annotation.RoleCheck;
import bupt.edu.jhc.apicloud_backend.model.vo.user.UserInfo;
import bupt.edu.jhc.apicloud_backend.service.IUserService;
import bupt.edu.jhc.apicloud_backend.utils.RequestHolder;
import bupt.edu.jhc.apicloud_common.api.user.domain.enums.RoleEnum;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.ThrowUtils;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * @Description: 权限校验拦截器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@Aspect
@Component
public class AuthInterceptor {
    @Resource
    private IUserService userService;

    /**
     * 身份校验
     * @param joinPoint
     * @param roleCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(roleCheck)")
    public Object doRoleCheck(ProceedingJoinPoint joinPoint, RoleCheck roleCheck) throws Throwable {
        // 1.看需要什么权限
        RoleEnum mustRole = roleCheck.mustRole();

        // 2.校验权限
        UserInfo currentUser = userService.getUserInfoById(RequestHolder.get().getUid());
        ThrowUtils.throwIf(!currentUser.getRole().equals(mustRole.getValue()), ErrorStatus.NO_AUTH_ERROR);

        // 3.执行原方法
        return joinPoint.proceed();
    }
}
