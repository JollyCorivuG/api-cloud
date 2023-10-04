package bupt.edu.jhc.apicloud.annotation;

import bupt.edu.jhc.apicloud.model.enums.user.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 角色校验注解
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleCheck {

    /**
     * 必须有某个角色
     *
     * @return
     */
    RoleEnum mustRole();

}

