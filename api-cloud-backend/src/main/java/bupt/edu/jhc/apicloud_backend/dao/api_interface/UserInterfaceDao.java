package bupt.edu.jhc.apicloud_backend.dao.api_interface;

import bupt.edu.jhc.apicloud_backend.mapper.UserInterfaceMapper;
import bupt.edu.jhc.apicloud_backend.model.entity.UserInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Description: 用户接口数据访问对象
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/10
 */
@Repository
public class UserInterfaceDao extends ServiceImpl<UserInterfaceMapper, UserInterface> {
    /**
     * 判断用户是否有足够的调用次数
     * @param userId
     * @param interfaceId
     * @return
     */
    public boolean isLeftInvokeCntEnough(Long userId, Long interfaceId) {
        List<UserInterface> l = this.lambdaQuery()
                .eq(UserInterface::getUserId, userId)
                .eq(UserInterface::getInterfaceId, interfaceId)
                .list();
        return l.isEmpty() || l.get(0).getLeftCnt() > 0;
    }

    /**
     * 自定义条件查询
     * @param initWrapper
     * @return
     */
    public UserInterface getOneByMapper(Consumer<LambdaQueryWrapper<UserInterface>> initWrapper) {
        LambdaQueryWrapper<UserInterface> wrapper = new LambdaQueryWrapper<>();
        initWrapper.accept(wrapper);
        List<UserInterface> userInterfaces = this.baseMapper.selectList(wrapper);
        return userInterfaces.isEmpty() ? null : userInterfaces.get(0);
    }
}
