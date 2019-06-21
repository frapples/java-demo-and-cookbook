package io.github.frapples.javademoandcookbook.demoandcase.demo.classloader;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/6/12
 */
public class DemoClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
