package bupt.edu.jhc.apicloud.model.vo.api_interface;

import bupt.edu.jhc.apicloud.model.vo.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 接口信息
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceInfo {
    private Long id;
    private String name;
    private String description;
    private String url;
    private String method;
    private String requestParams;
    private String requestHeader;
    private String responseHeader;
    private Integer status;
    private Integer totalReqCnt;
    private UserInfo creator;
    private Date createTime;
}
