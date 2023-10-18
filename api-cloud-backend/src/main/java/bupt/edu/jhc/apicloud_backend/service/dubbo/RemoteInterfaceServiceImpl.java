package bupt.edu.jhc.apicloud_backend.service.dubbo;

import bupt.edu.jhc.apicloud_backend.model.vo.api_interface.InterfaceInfo;
import bupt.edu.jhc.apicloud_backend.service.IInterfaceService;
import bupt.edu.jhc.apicloud_common.api.api_interface.IRemoteInterfaceService;
import bupt.edu.jhc.apicloud_common.api.api_interface.domain.dto.InterfaceDTO;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Description: 远程接口服务实现类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@DubboService
public class RemoteInterfaceServiceImpl implements IRemoteInterfaceService {
    @Resource
    private IInterfaceService interfaceService;

    @Override
    public InterfaceDTO getInterfaceByUrlAndMethod(String url, String method) {
        InterfaceInfo interfaceInfo = interfaceService.getInterfaceByUrlAndMethod(url, method);
        return BeanUtil.copyProperties(interfaceInfo, InterfaceDTO.class);
    }

    @Override
    public boolean isLeftInvokeCntEnough(Long userId, Long interfaceId) {
        return interfaceService.isLeftInvokeCntEnough(userId, interfaceId);
    }

    @Override
    public boolean invokeInterfaceSuccess(Long userId, Long interfaceId) {
        return interfaceService.invokeInterfaceSuccess(userId, interfaceId);
    }
}
