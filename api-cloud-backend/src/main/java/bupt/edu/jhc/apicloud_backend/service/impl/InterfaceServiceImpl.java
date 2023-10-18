package bupt.edu.jhc.apicloud_backend.service.impl;

import bupt.edu.jhc.apicloud_backend.common.constants.SysConstants;
import bupt.edu.jhc.apicloud_backend.dao.api_interface.InterfaceDao;
import bupt.edu.jhc.apicloud_backend.dao.api_interface.UserInterfaceDao;
import bupt.edu.jhc.apicloud_backend.model.dto.api_interface.CreateInterfaceReq;
import bupt.edu.jhc.apicloud_backend.model.dto.api_interface.InvokeInterfaceReq;
import bupt.edu.jhc.apicloud_backend.model.entity.ApiInterface;
import bupt.edu.jhc.apicloud_backend.model.entity.UserInterface;
import bupt.edu.jhc.apicloud_backend.model.vo.api_interface.InterfaceInfo;
import bupt.edu.jhc.apicloud_backend.model.vo.user.UserInfo;
import bupt.edu.jhc.apicloud_backend.service.IInterfaceService;
import bupt.edu.jhc.apicloud_backend.service.IUserService;
import bupt.edu.jhc.apicloud_backend.service.adapter.InterfaceAdapter;
import bupt.edu.jhc.apicloud_backend.utils.RedissonLockUtils;
import bupt.edu.jhc.apicloud_client_sdk.client.ApiCloudClient;
import bupt.edu.jhc.apicloud_client_sdk.common.domain.req.SDKCommonReq;
import bupt.edu.jhc.apicloud_client_sdk.common.domain.resp.SDKCommonResp;
import bupt.edu.jhc.apicloud_client_sdk.service.IApiService;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.ThrowUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    @Resource
    private UserInterfaceDao userInterfaceDao;

    @Resource
    private RedissonLockUtils redissonLockUtils;
    
    @Resource
    private IUserService userService;

    @Resource
    private IApiService apiService;


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

    @Override
    public void onlineInterface(Long id) {
        // 1.先查询出来接口信息
        ApiInterface interfaceInfo = interfaceDao.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorStatus.PARAMS_ERROR, "接口不存在!");
        ThrowUtils.throwIf(interfaceInfo.getStatus().equals(SysConstants.INTERFACE_STATUS_OPEN), ErrorStatus.OPERATION_ERROR, "接口已经上线!");

        // 2.TODO 判断接口能否是否能调用成功

        // 3.更改数据库字段
        interfaceInfo.setStatus(SysConstants.INTERFACE_STATUS_OPEN);
        ThrowUtils.throwIf(!interfaceDao.updateInterface(interfaceInfo), ErrorStatus.SYSTEM_ERROR);
    }

    @Override
    public void offlineInterface(Long id) {
        // 1.先查询出来接口信息
        ApiInterface interfaceInfo = interfaceDao.getById(id);
        ThrowUtils.throwIf(interfaceInfo == null, ErrorStatus.PARAMS_ERROR, "接口不存在!");
        ThrowUtils.throwIf(interfaceInfo.getStatus().equals(SysConstants.INTERFACE_STATUS_CLOSE), ErrorStatus.OPERATION_ERROR, "接口已经下线!");

        // 2.更改数据库字段
        interfaceInfo.setStatus(SysConstants.INTERFACE_STATUS_CLOSE);
        ThrowUtils.throwIf(!interfaceDao.updateInterface(interfaceInfo), ErrorStatus.SYSTEM_ERROR);
    }

    @Override
    public Map<String, Object> invokeInterface(Long userId, InvokeInterfaceReq req) {
        // 1.先查询出来接口
        ApiInterface apiInterface = interfaceDao.getById(req.getInterfaceId());
        ThrowUtils.throwIf(apiInterface == null, ErrorStatus.PARAMS_ERROR, "该接口不存在!");
        ThrowUtils.throwIf(apiInterface.getStatus().equals(SysConstants.INTERFACE_STATUS_CLOSE), ErrorStatus.OPERATION_ERROR, "该接口已经下线!");

        // 2.通过客户端 SDK 调用接口
        // 2.1创建 apiCloudClient 客户端
        UserInfo userInfo = userService.getUserInfoById(userId);
        ApiCloudClient apiCloudClient = new ApiCloudClient(userInfo.getAccessKey(), userInfo.getSecretKey());
        // 2.2创建入参
        SDKCommonReq sdkReq = new SDKCommonReq();
        sdkReq.setPath(apiInterface.getHost() + apiInterface.getUrl());
        sdkReq.setMethod(apiInterface.getMethod());
        sdkReq.setRequestParams(req.getUserReqParams());
        // 2.3调用 sdk 并返回结果
        SDKCommonResp sdkResp = apiService.request(apiCloudClient, sdkReq);
        return sdkResp.getData();
    }

    @Override
    public InterfaceInfo getInterfaceByUrlAndMethod(String url, String method) {
        ApiInterface apiInterface = interfaceDao.getOneByMapper(wrapper -> wrapper.eq(ApiInterface::getUrl, url).eq(ApiInterface::getMethod, method));
        return apiInterface == null ? null : interfaceAdapter.buildInterfaceInfoResp(apiInterface);
    }

    @Override
    public boolean isLeftInvokeCntEnough(Long userId, Long interfaceId) {
        return userInterfaceDao.isLeftInvokeCntEnough(userId, interfaceId);
    }

    @Override
    public boolean invokeInterfaceSuccess(Long userId, Long interfaceId) {
        UserInterface userInterface = userInterfaceDao.getOneByMapper(wrapper -> wrapper.eq(UserInterface::getUserId, userId)
                .eq(UserInterface::getInterfaceId, interfaceId));
        if (userInterface == null) {
            userInterface = UserInterface.builder()
                    .userId(userId)
                    .interfaceId(interfaceId)
                    .invokeCnt(1)
                    .leftCnt(SysConstants.INIT_INVOKE_CNT - 1)
                    .status(SysConstants.INVOKE_INTERFACE_STATUS_NORMAL)
                    .build();
            return userInterfaceDao.save(userInterface);
        }
        userInterface.setInvokeCnt(userInterface.getInvokeCnt() + 1);
        userInterface.setLeftCnt(userInterface.getLeftCnt() - 1);
        return userInterfaceDao.updateById(userInterface);
    }
}
