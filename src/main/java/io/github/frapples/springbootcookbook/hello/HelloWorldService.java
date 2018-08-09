package io.github.frapples.springbootcookbook.hello;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.github.frapples.springbootcookbook.hello.dao.PersonDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    @Autowired
    private HelloWorldMapper helloWorldMapper;

    public List<PersonDO> hello() {
        return this.helloWorldMapper.selectList(new EntityWrapper<PersonDO>().
            eq("id", 1));
    }
}
