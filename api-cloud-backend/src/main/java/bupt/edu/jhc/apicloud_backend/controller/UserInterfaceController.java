package bupt.edu.jhc.apicloud_backend.controller;

import bupt.edu.jhc.apicloud_backend.common.domain.vo.resp.BasicResponse;
import bupt.edu.jhc.apicloud_backend.model.dto.api_interface.InvokeInterfaceReq;
import bupt.edu.jhc.apicloud_backend.service.IInterfaceService;
import bupt.edu.jhc.apicloud_backend.utils.RequestHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description: 用户接口控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/10
 */
@RestController
@RequestMapping("/capi/interface")
@Tag(name = "用户接口相关接口")
public class UserInterfaceController {
    @Resource
    private IInterfaceService interfaceService;

    @PostMapping("/invoke")
    @Operation(summary = "调用接口")
    public BasicResponse<Map<String, Object>> invokeInterface(@RequestBody InvokeInterfaceReq req) {
        Long userId = RequestHolder.get().getUid();
        return BasicResponse.success(interfaceService.invokeInterface(userId, req));
    }
}
