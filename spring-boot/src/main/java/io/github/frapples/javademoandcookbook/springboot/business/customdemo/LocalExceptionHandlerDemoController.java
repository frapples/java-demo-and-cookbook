package io.github.frapples.javademoandcookbook.springboot.business.customdemo;

import io.github.frapples.javademoandcookbook.springboot.common.base.vo.ResponseVo;
import io.github.frapples.javademoandcookbook.springboot.common.exception.ErrorCode;
import io.github.frapples.javademoandcookbook.springboot.common.exception.APIException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/23
 */

@Controller
@RequestMapping("/api/custom-demo/exception-handler/local")
public class LocalExceptionHandlerDemoController {

    @RequestMapping(value = "/test-errorcode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo<Map<String, Object>> testErrorCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        throw new APIException(ErrorCode.RECORD_EXISTS, "编号为XXX的记录已经存在");
    }

    /**
     * 演示Controller异常处理，处理业务异常和系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVo<?> exceptionHandler(Exception e) {
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
