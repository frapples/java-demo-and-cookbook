package io.github.frapples.javademoandcookbook.demoandcase.demo.spring;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
class PersonForm {

    @Min(0)
    private int age;

    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$", message = "手机号码格式错误")
    private String phone = "23";

    @Valid
    private Point point;
}

@Data
class Point {

    @NotNull
    private Integer x;
    @NotNull
    private Integer y;
}

public class BeanValidationDemo {

    public static void main(String[] args) {
        PersonForm p = new PersonForm();
        p.setAge(-1);
        p.setPoint(new Point());

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersonForm>> set = validator.validate(p);
        set.stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
        // 如果是在Spring Bean中，Validator对象能够@Autowired直接注入。
    }
}

