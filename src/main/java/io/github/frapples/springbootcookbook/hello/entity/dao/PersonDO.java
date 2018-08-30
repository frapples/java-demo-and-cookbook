package io.github.frapples.springbootcookbook.hello.entity.dao;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("person")
public class PersonDO {

    private Integer id;
    private String name;
    private Integer age;
}
