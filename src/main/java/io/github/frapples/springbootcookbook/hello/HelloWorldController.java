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

}
