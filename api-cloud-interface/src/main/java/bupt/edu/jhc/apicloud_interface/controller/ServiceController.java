package bupt.edu.jhc.apicloud_interface.controller;

import bupt.edu.jhc.apicloud_client_sdk.common.domain.resp.SDKCommonResp;
import bupt.edu.jhc.apicloud_client_sdk.model.params.WeatherParams;
import bupt.edu.jhc.apicloud_interface.utils.ReqUtils;
import bupt.edu.jhc.apicloud_interface.utils.RespUtils;
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

    @GetMapping("/weather")
    public SDKCommonResp getWeatherInfo(WeatherParams params) {
        return RespUtils.getResp("https://api.vvhan.com/api/weather", params);
    }
}
