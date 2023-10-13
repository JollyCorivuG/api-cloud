package bupt.edu.jhc.apicloud.service;

import bupt.edu.jhc.apicloud.model.dto.api_interface.CreateInterfaceReq;
import bupt.edu.jhc.apicloud.model.dto.api_interface.InvokeInterfaceReq;
import bupt.edu.jhc.apicloud.model.vo.api_interface.InterfaceInfo;

import java.util.Map;

/**
 * @Description: 接口服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
public interface IInterfaceService {
    /**
     * 创建接口
     * @param userId
     * @param req
     * @return
     */
    InterfaceInfo createInterface(Long userId, CreateInterfaceReq req);

    /**
     * 删除接口
     * @param id
     */
    void deleteInterface(Long id);

    /**
     * 上线接口
     * @param id
     */
    void onlineInterface(Long id);

    /**
     * 下线接口
     * @param id
     */
    void offlineInterface(Long id);

    /**
     * 调用接口
     * @param userId
     * @param req
     * @return
     */
    Map<String, Object> invokeInterface(Long userId, InvokeInterfaceReq req);
}
