package io.github.frapples.javademoandcookbook.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 该注解作用分析： https://www.jianshu.com/p/4e1cab2d8431
@SpringBootApplication(scanBasePackages = "io.github.frapples.javademoandcookbook")
// 由于Service等Bean为防止冗余没有使用接口，因此需要用cglib启用AOP
@EnableTransactionManagement(proxyTargetClass = true)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
