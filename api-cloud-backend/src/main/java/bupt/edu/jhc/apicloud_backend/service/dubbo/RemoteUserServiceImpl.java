package bupt.edu.jhc.apicloud_backend.service.dubbo;

import bupt.edu.jhc.apicloud_backend.model.vo.user.UserInfo;
import bupt.edu.jhc.apicloud_backend.service.IUserService;
import bupt.edu.jhc.apicloud_common.api.user.IRemoteUserService;
import bupt.edu.jhc.apicloud_common.api.user.domain.dto.UserDTO;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Description: 远程用户服务实现类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/15
 */
@DubboService
public class RemoteUserServiceImpl implements IRemoteUserService {
    @Override
    public String getUser() {
        return "user";
    }

    @Resource
    private IUserService userService;

    @Override
    public UserDTO getUserByAccessKey(String accessKey) {
        UserInfo userInfo = userService.getUserByAccessKey(accessKey);
        return BeanUtil.copyProperties(userInfo, UserDTO.class);
    }
}
