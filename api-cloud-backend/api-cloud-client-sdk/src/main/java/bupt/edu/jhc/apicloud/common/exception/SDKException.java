package bupt.edu.jhc.apicloud.common.exception;

import bupt.edu.jhc.apicloud.common.domain.enums.ErrorStatus;
import lombok.Getter;

/**
 * @Description: 业务异常
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/11
 */
@Getter
public class SDKException extends RuntimeException {
    private final Integer code;

    public SDKException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.code = errorStatus.getCode();
    }

    public SDKException(ErrorStatus errorStatus, String message) {
        super(message);
        this.code = errorStatus.getCode();
    }
}
