package io.github.frapples.javademoandcookbook.springboot.business.hello.persistence;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dao.PersonDO;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface HelloWorldMapper extends BaseMapper<PersonDO> {

    default List<PersonDO> selectAbcByName() {
        return this.selectList(new LambdaQueryWrapper<PersonDO>().
            eq(PersonDO::getId, 1));
    }
}
