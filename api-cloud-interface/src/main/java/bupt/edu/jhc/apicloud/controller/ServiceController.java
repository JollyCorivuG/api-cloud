package bupt.edu.jhc.apicloud.controller;

import bupt.edu.jhc.apicloud.utils.ReqUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: api 服务控制器
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/12
 */
@RestController
@RequestMapping("/")
public class ServiceController {
    @GetMapping("/poisonousChickenSoup")
    public String getPoisonousChickenSoup() {
        return ReqUtils.get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }
}
