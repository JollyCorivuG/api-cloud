package bupt.edu.jhc.apicloud.common.domain.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 错误响应
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SDKErrorResp {
    private String msg;
    private Integer code;
}
