package bupt.edu.jhc.apicloud_backend.common.exception;

import bupt.edu.jhc.apicloud_backend.common.domain.vo.resp.BasicResponse;
import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局异常处理
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/9/12
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public BasicResponse<Void> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return BasicResponse.fail(ErrorStatus.SYSTEM_ERROR);
    }

    @ExceptionHandler(BizException.class)
    public BasicResponse<Void> bizExceptionHandler(BizException e) {
        log.error("BizException", e);
        return BasicResponse.fail(e.getCode(), e.getMessage());
    }
}
