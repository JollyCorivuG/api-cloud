package bupt.edu.jhc.apicloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bupt.edu.jhc.apicloud.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户 Mapper
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
