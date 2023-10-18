package bupt.edu.jhc.apicloud_common.api.api_interface.domain.dto;

import bupt.edu.jhc.apicloud_common.api.user.domain.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 接口数据传输对象
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceDTO {
    private Long id;
    private String name;
    private String description;
    private String host;
    private String url;
    private String method;
    private String requestParams;
    private String requestHeader;
    private String responseHeader;
    private Integer status;
    private Integer totalReqCnt;
    private UserDTO creator;
    private Date createTime;
}
