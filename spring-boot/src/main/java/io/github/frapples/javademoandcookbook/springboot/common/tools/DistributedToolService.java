package io.github.frapples.javademoandcookbook.springboot.common.tools;

import java.util.concurrent.locks.Lock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2019/3/7
 */

@Component
public class DistributedToolService {

    private final PlatformTransactionManager platformTransactionManager;

    private final RedisConnectionFactory redisConnectionFactory;

    private final RedissonClient redissonClient;

    private static boolean USE_TEST_LOCK_VERSION = true;


    @Autowired
    public DistributedToolService(PlatformTransactionManager platformTransactionManager,
        RedisConnectionFactory redisConnectionFactory, RedissonClient redissonClient) {
        this.platformTransactionManager = platformTransactionManager;
        this.redisConnectionFactory = redisConnectionFactory;
        this.redissonClient = redissonClient;
    }

    public void withTransaction(TransactionDefinition transactionDefinition, Runnable runnable) {
        TransactionStatus transStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            runnable.run();
            platformTransactionManager.commit(transStatus);
        } catch (Exception e) {
            platformTransactionManager.rollback(transStatus);
            throw e;
        }
    }

    public void withTransaction(Propagation propagation, Isolation isolation, Runnable runnable) {
        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        transDefinition.setPropagationBehavior(propagation.value());
        transDefinition.setIsolationLevel(isolation.value());
        withTransaction(transDefinition, runnable);
    }

    public void withTransaction(Propagation propagation, Runnable runnable) {
        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        transDefinition.setPropagationBehavior(propagation.value());
        withTransaction(transDefinition, runnable);
    }

    public void withTransaction(Runnable runnable) {
        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
        withTransaction(transDefinition, runnable);
    }

    public Lock newDistributedLock(String name) {
        if (USE_TEST_LOCK_VERSION) {
            return new RedisSpinLock(redisConnectionFactory, name);
        } else {
            return redissonClient.getLock(name);
        }
    }

}
