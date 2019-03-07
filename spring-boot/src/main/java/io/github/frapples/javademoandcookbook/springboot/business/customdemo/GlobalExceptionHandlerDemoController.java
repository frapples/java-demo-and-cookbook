package io.github.frapples.javademoandcookbook.springboot.business.customdemo;

import io.github.frapples.javademoandcookbook.springboot.common.vo.ResponseVo;
import io.github.frapples.javademoandcookbook.springboot.common.exception.ErrorCode;
import io.github.frapples.javademoandcookbook.springboot.common.exception.ErrorCodeWrapperException;
import io.github.frapples.javademoandcookbook.springboot.web.exceptionhandler.GlobalExceptionHandlerMixin;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/23
 */

@Controller
@RequestMapping("/api/custom-demo/exception-handler/global")
public class GlobalExceptionHandlerDemoController implements GlobalExceptionHandlerMixin  {

    @RequestMapping(value = "/test-errorcode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo<Map<String, Object>> testErrorCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        throw new ErrorCodeWrapperException(ErrorCode.RECORD_EXISTS, "编号为XXX的记录已经存在");
    }

}
