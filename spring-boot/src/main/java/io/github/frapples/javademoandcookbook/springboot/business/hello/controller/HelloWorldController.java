package io.github.frapples.javademoandcookbook.springboot.business.hello.controller;


import io.github.frapples.javademoandcookbook.commonutils.utils.collection.fluentmap.ResponseMap;
import io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dao.PersonDO;
import io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dto.PersonVo;
import io.github.frapples.javademoandcookbook.springboot.business.hello.service.HelloWorldService;
import io.github.frapples.javademoandcookbook.springboot.common.utils.mybatisplus.QueryUtils;
import io.github.frapples.javademoandcookbook.springbootwebcore.base.vo.ResponseVo;
import io.github.frapples.javademoandcookbook.springbootwebcore.config.exceptionhandler.ExceptionToErrorCode;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */
@Controller
@RequestMapping("/api/hello")
@ExceptionToErrorCode
public class HelloWorldController {

    private final HelloWorldService helloWorldService;

    @Autowired
    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo hello() {
        List<PersonVo> persons = helloWorldService.hello();

        Map<String, Object> r = ResponseMap.of()
            .fPut("persons", persons)
            .fPut("a", QueryUtils.columnToString(PersonDO::getAge))
            .fPut("b", QueryUtils.columnToString(PersonDO::getId))
            .fPut("c", QueryUtils.column(PersonDO::getName));

        return ResponseVo.ofSuccess(r);
    }

}
