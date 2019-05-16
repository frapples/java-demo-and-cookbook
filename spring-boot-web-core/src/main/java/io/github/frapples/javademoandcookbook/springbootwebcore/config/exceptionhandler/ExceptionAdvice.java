package io.github.frapples.javademoandcookbook.springbootwebcore.config.exceptionhandler;

import io.github.frapples.javademoandcookbook.springbootwebcore.base.vo.ResponseVo;
import io.github.frapples.javademoandcookbook.springbootwebcore.base.exception.ErrorCode;
import io.github.frapples.javademoandcookbook.springbootwebcore.base.exception.APIException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/23
 *
 * ControllerAdivce全局异常处理
 * 对应Controller加上 @ExceptionErrorCode 后，即可生效
 */
@ControllerAdvice(annotations = {ExceptionToErrorCode.class})
public class ExceptionAdvice {

    /**
     * 演示Controller异常处理，处理业务异常和系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVo<?> exceptionHandler(Exception e) {
        return exceptionToResponseVo(e);
    }

    public static ResponseVo<?> exceptionToResponseVo(Exception e) {
        if (e instanceof APIException) {
            APIException errCodeException = (APIException) e;
            if ((errCodeException.getErrorCode().equals(ErrorCode.SYSTEM_ERROR))) {
                e.printStackTrace();
            }
            return ResponseVo.ofErrorCodeWrapperException(errCodeException);
        } else {
            e.printStackTrace();
            return ResponseVo.ofSystemError();
        }
    }
}

