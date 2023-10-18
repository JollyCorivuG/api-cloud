package bupt.edu.jhc.apicloud_common.api.api_interface;

import bupt.edu.jhc.apicloud_common.api.api_interface.domain.dto.InterfaceDTO;

/**
 * @Description: 远程接口服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/15
 */
public interface IRemoteInterfaceService {

    InterfaceDTO getInterfaceByUrlAndMethod(String url, String method);

    boolean isLeftInvokeCntEnough(Long userId, Long interfaceId);

    boolean invokeInterfaceSuccess(Long userId, Long interfaceId);
}
