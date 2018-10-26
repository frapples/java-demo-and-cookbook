package io.github.frapples.javademoandcookbook.springboot.base;

import io.github.frapples.javademoandcookbook.springboot.web.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/10/26
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {

}
