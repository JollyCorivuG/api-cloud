package bupt.edu.jhc.apicloud_backend.service.adapter;

import bupt.edu.jhc.apicloud_backend.common.constants.SysConstants;
import bupt.edu.jhc.apicloud_backend.model.dto.api_interface.CreateInterfaceReq;
import bupt.edu.jhc.apicloud_backend.model.entity.ApiInterface;
import bupt.edu.jhc.apicloud_backend.model.vo.api_interface.InterfaceInfo;
import bupt.edu.jhc.apicloud_backend.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @Description: 接口适配器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@Component
public class InterfaceAdapter {
    @Resource
    private IUserService userService;

    public ApiInterface buildInterfaceSave(Long userId, CreateInterfaceReq req) {
        return ApiInterface.builder()
                .name(req.getName())
                .description(req.getDescription())
                .host(req.getHost())
                .url(req.getUrl())
                .method(req.getMethod())
                .requestParams(req.getRequestParams())
                .requestHeader(req.getRequestHeader())
                .responseHeader(req.getResponseHeader())
                .status(SysConstants.INTERFACE_STATUS_CLOSE)
                .creatorId(userId)
                .build();
    }

    public InterfaceInfo buildInterfaceInfoResp(ApiInterface apiInterface) {
        return InterfaceInfo.builder()
                .id(apiInterface.getId())
                .name(apiInterface.getName())
                .description(apiInterface.getDescription())
                .url(apiInterface.getUrl())
                .method(apiInterface.getMethod())
                .requestParams(apiInterface.getRequestParams())
                .requestHeader(apiInterface.getRequestHeader())
                .responseHeader(apiInterface.getResponseHeader())
                .status(apiInterface.getStatus())
                .totalReqCnt(apiInterface.getTotalReqCnt())
                .creator(userService.getUserInfoById(apiInterface.getCreatorId()))
                .createTime(apiInterface.getCreateTime())
                .build();
    }
}
