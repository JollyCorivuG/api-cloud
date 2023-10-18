package bupt.edu.jhc.apicloud_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bupt.edu.jhc.apicloud_backend.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户 Mapper
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
