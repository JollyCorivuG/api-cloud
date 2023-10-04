package bupt.edu.jhc.apicloud.service.impl;

import bupt.edu.jhc.apicloud.common.domain.enums.ErrorStatus;
import bupt.edu.jhc.apicloud.common.exception.ThrowUtils;
import bupt.edu.jhc.apicloud.dao.api_interface.InterfaceDao;
import bupt.edu.jhc.apicloud.model.dto.api_interface.CreateInterfaceReq;
import bupt.edu.jhc.apicloud.model.entity.ApiInterface;
import bupt.edu.jhc.apicloud.model.vo.api_interface.InterfaceInfo;
import bupt.edu.jhc.apicloud.service.IInterfaceService;
import bupt.edu.jhc.apicloud.service.adapter.InterfaceAdapter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Description: 接口服务实现类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@Service
public class InterfaceServiceImpl implements IInterfaceService {
    @Resource
    private InterfaceAdapter interfaceAdapter;

    @Resource
    private InterfaceDao interfaceDao;

    @Override
    public InterfaceInfo createInterface(Long userId, CreateInterfaceReq req) {
        // 1.构造接口实体
        ApiInterface interfaceInfo = interfaceAdapter.buildInterfaceSave(userId, req);

        // 2.保存接口
        interfaceDao.saveInterface(interfaceInfo);

        // 3.返回接口信息
        return interfaceAdapter.buildInterfaceInfoResp(interfaceInfo);
    }

    @Override
    public void deleteInterface(Long id) {
        ThrowUtils.throwIf(!interfaceDao.deleteInterface(id), ErrorStatus.SYSTEM_ERROR);
    }
}
