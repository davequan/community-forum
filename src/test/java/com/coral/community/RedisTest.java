package com.coral.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testStrings(){
        String redisKey ="test:count";
        redisTemplate.opsForValue().set(redisKey,1);
        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }
    @Test
    public void testHashes(){
        String redisKey = " test:user";
        redisTemplate.opsForHash().put(redisKey,"id",1);
        redisTemplate.opsForHash().put(redisKey,"username","zhangsan");
        System.out.println(redisTemplate.opsForHash().get(redisKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey,"username"));
    }
    @Test
    public void testLists(){
         String redisKey = "test:ids";
         redisTemplate.opsForList().leftPush(redisKey,101);
        redisTemplate.opsForList().leftPush(redisKey,102);
        redisTemplate.opsForList().leftPush(redisKey,103);
        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey,0));
        System.out.println(redisTemplate.opsForList().range(redisKey,0,2));

        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
    }

    @Test
    public void testSets(){
        String redisKey = "test:teacher";
        redisTemplate.opsForSet().add(redisKey,"zhang","liu","guan");
        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }
    @Test
    public void testSortedSets(){
        String redisKey = "test:students";
        redisTemplate.opsForZSet().add(redisKey,"tang",80);
        redisTemplate.opsForZSet().add(redisKey,"wu",90);
        redisTemplate.opsForZSet().add(redisKey,"zhu",50);
        redisTemplate.opsForZSet().add(redisKey,"sha",70);
        redisTemplate.opsForZSet().add(redisKey,"ma",60);
        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().score(redisKey,"zhu"));
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey,"zhu"));
        System.out.println(redisTemplate.opsForZSet().range(redisKey,0,2));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey,0,2));
    }
    @Test
    public void testKeys(){
        redisTemplate.delete("test:user");
        System.out.println(redisTemplate.hasKey("test:user"));
        redisTemplate.expire("test:students",10, TimeUnit.SECONDS);
    }

    // visit the same key multiple times
    @Test
    public  void testBoundOperations(){
        String redisKey="test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.append("11");
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    //编程式事务
    @Test
    public void testTransactional(){
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String rediKey = "test:tx";
                redisOperations.multi();  // start transaction

                redisOperations.opsForSet().add(rediKey,"zhangsan");
                redisOperations.opsForSet().add(rediKey,"lisi");
                redisOperations.opsForSet().add(rediKey,"wangwu");
                System.out.println(redisOperations.opsForSet().members(rediKey));
                return redisOperations.exec();
            }
        });
        System.out.println(obj);
    }


}
