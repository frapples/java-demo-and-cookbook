package io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("person")
public class PersonDO {

    private Integer id;
    private String name;
    private Integer age;
}
