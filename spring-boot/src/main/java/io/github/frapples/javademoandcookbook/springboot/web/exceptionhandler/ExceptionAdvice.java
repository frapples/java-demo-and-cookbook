package io.github.frapples.javademoandcookbook.springboot.web.exceptionhandler;

import io.github.frapples.javademoandcookbook.springboot.common.dto.ResponseDTO;
import io.github.frapples.javademoandcookbook.springboot.common.exception.ErrorCode;
import io.github.frapples.javademoandcookbook.springboot.common.exception.ErrorCodeWrapperException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/23
 *
 * 演示ControllerAdivce全局异常处理
 * 只对实现GlobalExceptionHandlerMixin接口的Controller有效
 */
@ControllerAdvice(assignableTypes = {GlobalExceptionHandlerMixin.class})
public class ExceptionAdvice {

    /**
     * 演示Controller异常处理，处理业务异常和系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseDTO<?> exceptionHandler(Exception e) {
        if (e instanceof ErrorCodeWrapperException) {
            ErrorCodeWrapperException errCodeException = (ErrorCodeWrapperException) e;
            if ((errCodeException.getErrorCode().equals(ErrorCode.SYSTEM_ERROR))) {
                e.printStackTrace();
            }
            return ResponseDTO.ofErroCodeWrapperException(errCodeException);
        } else {
            e.printStackTrace();
            return ResponseDTO.ofSystemError();
        }
    }
}

