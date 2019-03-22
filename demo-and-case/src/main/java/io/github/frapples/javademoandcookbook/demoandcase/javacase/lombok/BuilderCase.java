package io.github.frapples.javademoandcookbook.demoandcase.javacase.lombok;

import com.google.common.base.CaseFormat;
import io.github.frapples.javademoandcookbook.commonutils.utils.collection.ResponseMap;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/21
 */
public class BuilderCase {

    /**
     * 演示了使用Builder实现命名参数和默认参数
     * 1. 对于默认参数，两种方案：什么都不干，就是java默认值（0、fasle和null）；或重定义Builder类，设置默认值。
     * 2. 对于必选参数，两种方案：使用NonNull注解运行期检查；或重新定义builder函数，强制要求传递必传参数。
     *
     * 似乎重定义Builder后，虽然能够编译运行，但是IDE对泛型的检查会有问题。
     */
    @Builder(builderMethodName = "beanToMapHelper", buildMethodName = "beanToMap", builderClassName = "BeanToMapBuilder")
    public static <T> Map<String, Object> beanToMap(@lombok.NonNull T bean, Boolean filterNull, Boolean trimString, CaseFormat caseFormat) {
        trimString = trimString == null ? false : trimString;
        return new ResponseMap()
            .fPut("bean", bean)
            .fPut("filterNull", filterNull)
            .fPut("trimString", trimString)
            .fPut("caseFormat", caseFormat);
    }

    public static class BeanToMapBuilder<T> {

        private Boolean filterNull = false;

    }


    public static void main(String[] args) {
        Object o = new Object();
        // Map<String, Object> r = BuilderCase.beanToMapHelper().bean(o).trimString(true).beanToMap();
        // System.out.println(r);

        // r = beanToMapHelper().trimString(true).beanToMap();
        // System.out.println(r);

        Person.builder().name("Tom").build();
        Person person = Person.builder().addNumber(1).numbers(Arrays.asList(2, 3)).build();
        System.out.println(person);
    }

}

/**
 * 1. Singular 可修饰构造函数参数，增强对容器的支持
 * 2. lombok自动生成的类、函数，可直接重新定义。重定义后的类，不是覆盖，而是合并。lombok会将类中的方法添加到生成的类中。
 */
@Getter
@ToString
class Person {

    private String name;

    private Date created;

    private List<Integer> numbers;

    @Builder
    public Person(String name, Date created, @Singular("addNumber") List<Integer> numbers) {
        this.name = name;
        this.created = created;
        this.numbers = numbers;
    }

    public static class PersonBuilder {

        PersonBuilder name(String name) {
            this.name = name.trim();
            return this;
        }

    }
}



