package io.github.frapples.utilscookbook.demo.dynamicproxy;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/27
 */
public class AService {

    private final BService bService = IOC.get(BService.class);

    void test() {
        int r = this.bService.add(1, 2);
        System.out.println(r);
    }

    public static void main(String[] args) {
        IOC.register(new Class<?>[]{
            AService.class,
            BService.class
        });
        IOC.done();
        AService aService = IOC.get(AService.class);
        aService.test();

    }

}
