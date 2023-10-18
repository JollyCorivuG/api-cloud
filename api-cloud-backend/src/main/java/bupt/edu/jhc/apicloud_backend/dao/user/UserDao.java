package bupt.edu.jhc.apicloud_backend.dao.user;

import bupt.edu.jhc.apicloud_backend.mapper.UserMapper;
import bupt.edu.jhc.apicloud_backend.model.entity.User;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.ThrowUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Description: 用户数据访问对象
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Repository
public class UserDao extends ServiceImpl<UserMapper, User> {

    /**
     * 保存用户
     * @param user
     * @return
     */
    public User saveUser(User user) {
        this.save(user);
        return user;
    }


    /**
     * 根据 ID 获取用户
     * @param id
     * @return
     */
    public User getUserById(Long id) {
        return this.getById(id);
    }

    /**
     * 根据手机号获取用户
     * @param phone
     * @return
     */
    public User getUserByPhone(String phone) {
        List<User> userList = this.lambdaQuery().eq(User::getPhone, phone).list();
        ThrowUtils.throwIf(userList.size() > 1, ErrorStatus.SYSTEM_ERROR, "手机号重复");
        return userList.isEmpty() ? null : userList.get(0);
    }

    /**
     * 根据昵称模糊搜索用户
     * @param vagueName
     * @return
     */
    public List<User> searchUserByVagueName(String vagueName) {
        return this.lambdaQuery().like(User::getNickName, vagueName).list();
    }

    /**
     * 自定义条件查询
     * @param initWrapper
     * @return
     */
    public User getOneByMapper(Consumer<LambdaQueryWrapper<User>> initWrapper) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        initWrapper.accept(wrapper);
        List<User> users = this.baseMapper.selectList(wrapper);
        return users.isEmpty() ? null : users.get(0);
    }
}
