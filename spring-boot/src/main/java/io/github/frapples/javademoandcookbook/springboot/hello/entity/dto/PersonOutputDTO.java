package io.github.frapples.javademoandcookbook.springboot.hello.entity.dto;

import io.github.frapples.javademoandcookbook.springboot.hello.entity.dao.PersonDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/30
 */
@Data
public class PersonOutputDTO {

    private Integer id;
    private String name;
    private Integer age;

    public static PersonDO convertToDO(PersonOutputDTO personOutputDTO) {
        PersonDO personDO = new PersonDO();
        BeanUtils.copyProperties(personOutputDTO, personDO);
        return personDO;
    }

    public static PersonOutputDTO convertFromDO(PersonDO personDO) {
        PersonOutputDTO personOutputDTO = new PersonOutputDTO();
        BeanUtils.copyProperties(personOutputDTO, personDO);
        return personOutputDTO;
    }
}
