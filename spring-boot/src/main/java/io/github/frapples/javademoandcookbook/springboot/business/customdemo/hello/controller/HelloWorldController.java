package io.github.frapples.javademoandcookbook.springboot.business.customdemo.hello.controller;


import io.github.frapples.javademoandcookbook.springboot.business.customdemo.hello.entity.dto.PersonVo;
import io.github.frapples.javademoandcookbook.springboot.business.customdemo.hello.service.HelloWorldService;
import io.github.frapples.javademoandcookbook.springboot.common.vo.ResponseVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/hello")
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
        return ResponseVo.ofSuccess(persons);
    }

}
