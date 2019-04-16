package io.github.frapples.javademoandcookbook.springboot.business.hello.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.frapples.javademoandcookbook.springboot.common.base.AbstractBaseService;
import io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dto.PersonVo;
import io.github.frapples.javademoandcookbook.springboot.common.utils.CollectionUtils;
import io.github.frapples.javademoandcookbook.springboot.business.hello.persistence.HelloWorldMapper;
import io.github.frapples.javademoandcookbook.springboot.business.hello.entity.dao.PersonDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService extends AbstractBaseService<HelloWorldMapper, PersonDO> {

    private final HelloWorldMapper helloWorldMapper;

    @Autowired
    public HelloWorldService(HelloWorldMapper helloWorldMapper) {
        this.helloWorldMapper = helloWorldMapper;
    }

    public List<PersonVo> hello() {
        List<PersonDO> persons = this.helloWorldMapper.selectList(new LambdaQueryWrapper<PersonDO>().
            eq(PersonDO::getId, 1));
        System.out.println(this.helloWorldMapper.selectAbcByName());


        return CollectionUtils.map(persons, PersonVo::convertFromDO);
    }

}
