package io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dto;

import io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dao.PersonDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/30
 */
@Data
public class PersonVo {

    private Integer id;
    private String name;
    private Integer age;

    public static PersonDO convertToDO(PersonVo personVo) {
        PersonDO personDO = new PersonDO();
        BeanUtils.copyProperties(personVo, personDO);
        return personDO;
    }

    public static PersonVo convertFromDO(PersonDO personDO) {
        PersonVo personVo = new PersonVo();
        BeanUtils.copyProperties(personVo, personDO);
        return personVo;
    }
}
