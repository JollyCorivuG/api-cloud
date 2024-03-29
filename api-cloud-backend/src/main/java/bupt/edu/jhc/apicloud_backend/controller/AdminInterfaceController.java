package bupt.edu.jhc.apicloud_backend.controller;

import bupt.edu.jhc.apicloud_backend.annotation.RoleCheck;
import bupt.edu.jhc.apicloud_backend.common.domain.vo.resp.BasicResponse;
import bupt.edu.jhc.apicloud_backend.model.dto.api_interface.CreateInterfaceReq;
import bupt.edu.jhc.apicloud_backend.model.vo.api_interface.InterfaceInfo;
import bupt.edu.jhc.apicloud_backend.service.IInterfaceService;
import bupt.edu.jhc.apicloud_backend.utils.RequestHolder;
import bupt.edu.jhc.apicloud_common.api.user.domain.enums.RoleEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 管理接口控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@RestController
@RequestMapping("/bapi/interface")
@Tag(name = "接口管理相关接口")
public class AdminInterfaceController {
    @Resource
    private IInterfaceService interfaceService;

    @PostMapping("/create")
    @Operation(summary = "创建接口")
    @RoleCheck(mustRole = RoleEnum.ADMIN)
    public BasicResponse<InterfaceInfo> createInterface(@RequestBody CreateInterfaceReq req) {
        Long userId = RequestHolder.get().getUid();
        return BasicResponse.success(interfaceService.createInterface(userId, req));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除接口")
    @RoleCheck(mustRole = RoleEnum.ADMIN)
    public BasicResponse<Void> deleteInterface(@PathVariable("id") Long id) {
        interfaceService.deleteInterface(id);
        return BasicResponse.success(null);
    }

    @PostMapping("/online/{id}")
    @Operation(summary = "上线接口")
    @RoleCheck(mustRole = RoleEnum.ADMIN)
    public BasicResponse<Void> onlineInterface(@PathVariable("id") Long id) {
        interfaceService.onlineInterface(id);
        return BasicResponse.success(null);
    }

    @PostMapping("/offline/{id}")
    @Operation(summary = "下线接口")
    @RoleCheck(mustRole = RoleEnum.ADMIN)
    public BasicResponse<Void> offlineInterface(@PathVariable("id") Long id) {
        interfaceService.offlineInterface(id);
        return BasicResponse.success(null);
    }

}
