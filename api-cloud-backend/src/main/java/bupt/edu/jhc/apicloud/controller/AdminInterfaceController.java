package bupt.edu.jhc.apicloud.controller;

import bupt.edu.jhc.apicloud.annotation.RoleCheck;
import bupt.edu.jhc.apicloud.common.domain.vo.resp.BasicResponse;
import bupt.edu.jhc.apicloud.model.dto.api_interface.CreateInterfaceReq;
import bupt.edu.jhc.apicloud.model.enums.user.RoleEnum;
import bupt.edu.jhc.apicloud.model.vo.api_interface.InterfaceInfo;
import bupt.edu.jhc.apicloud.service.IInterfaceService;
import bupt.edu.jhc.apicloud.utils.RequestHolder;
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

}
