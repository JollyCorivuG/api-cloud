package bupt.edu.jhc.apicloud_backend.common.domain.vo.resp;

import bupt.edu.jhc.apicloud_backend.common.constants.SysConstants;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 基础响应类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse<T> {
    private Integer statusCode;
    private String statusMsg;
    private T data;

    public static <T> BasicResponse<T> success(T data) {
        return BasicResponse.<T>builder()
                .statusCode(SysConstants.SUCCESS_CODE)
                .statusMsg("success")
                .data(data)
                .build();
    }

    public static <T> BasicResponse<T> fail(ErrorStatus errorStatus) {
        return BasicResponse.<T>builder()
                .statusCode(errorStatus.getCode())
                .statusMsg(errorStatus.getMessage())
                .build();
    }

    public static <T> BasicResponse<T> fail(Integer code, String message) {
        return BasicResponse.<T>builder()
                .statusCode(code)
                .statusMsg(message)
                .build();
    }
}
