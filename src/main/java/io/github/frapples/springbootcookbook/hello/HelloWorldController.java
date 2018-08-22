package io.github.frapples.springbootcookbook.hello;


import io.github.frapples.springbootcookbook.common.dto.ResponseDTO;
import io.github.frapples.springbootcookbook.common.exception.ErrorCode;
import io.github.frapples.springbootcookbook.common.exception.ErrorCodeWrapperException;
import io.github.frapples.springbootcookbook.hello.dao.PersonDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/hello")
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<Map<String, Object>> hello() {
        List<PersonDO> persons = helloWorldService.hello();
        Map<String, Object> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("persons", persons);
        return ResponseDTO.ofSuccess(map);
    }

    @RequestMapping(value = "/test-errorcode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDTO<Map<String, Object>> testErrorCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        throw new ErrorCodeWrapperException(ErrorCode.RECORD_EXISTS, "编号为XXX的记录已经存在");
    }

    /**
     * 演示Controller异常处理，处理业务异常和系统异常
     * @param e
     * @return
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
