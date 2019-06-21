package io.github.frapples.javademoandcookbook.demoandcase.cookbook.commonpool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/5/27
 */
public class DemoPoolFactory extends BaseKeyedPooledObjectFactory<String, DemoEntity> {

    @Override
    public DemoEntity create(String s) throws Exception {
        return new DemoEntity(s);
    }

    @Override
    public PooledObject<DemoEntity> wrap(DemoEntity demoEntity) {
        return new DefaultPooledObject<>(demoEntity);
    }


    public static void main(String[] args) throws Exception {
        GenericKeyedObjectPoolConfig<DemoEntity> config = new GenericKeyedObjectPoolConfig<>();
        {
            config.setMaxIdlePerKey(8);
            config.setMinIdlePerKey(3);
            config.setMaxTotal(18);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
        }
        KeyedObjectPool<String, DemoEntity> objectPool = new GenericKeyedObjectPool<>(new DemoPoolFactory(), config);
        //添加对象到池，重复的不会重复入池
        objectPool.addObject("1");
        objectPool.addObject("2");
        objectPool.addObject("3");

        // 获得对应key的对象
        DemoEntity demoEntity = objectPool.borrowObject("1");

        // 释放对象
        objectPool.returnObject("1", demoEntity);

        //清除所有的对象
        objectPool.clear();
    }
}
