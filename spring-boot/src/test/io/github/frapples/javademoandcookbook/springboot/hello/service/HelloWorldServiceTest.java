package io.github.frapples.javademoandcookbook.springboot.business.hello.service;

import static org.junit.Assert.*;

import io.github.frapples.javademoandcookbook.springboot.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */
public class HelloWorldServiceTest extends BaseTest {

    @Autowired
    private HelloWorldService helloWorldService;

    @Test
    public void hello() {
        System.out.println(helloWorldService.hello());
    }
}