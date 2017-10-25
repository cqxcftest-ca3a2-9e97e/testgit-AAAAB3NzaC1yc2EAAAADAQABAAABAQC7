package com.xcf.scm.dal.test;

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Created by WangBin on 2016/1/19.
 */
public class DemoDaoTest extends DaoTestBase {


    @Test
    public void testQuery(){

    }

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testRedisOper(){
       /* redisTemplate.opsForValue().set("object1","1");
        redisTemplate.opsForValue().set("object2","2");
        redisTemplate.opsForValue().set("object3","3");

        Set<String> keys = redisTemplate.keys("object*");
        System.out.println(redisTemplate.opsForValue().get("object1"));
        System.out.println(redisTemplate.opsForValue().get(keys));
        */
        redisTemplate.opsForList().leftPush("list1","aaaaaaaaaa");
        redisTemplate.opsForList().leftPush("list1","aaaaaaaaaa2");
        redisTemplate.opsForList().leftPush("list1","aaaaaaaaaa3");

        System.out.println("range="+redisTemplate.opsForList().range("list1", 0, 5));
        //System.out.println(redisTemplate.opsForList());

        redisTemplate.opsForValue().set("test","123232");
        redisTemplate.opsForValue().set("test1",123232);

        redisTemplate.opsForHash().put("keytest","test","123232");
    }
}
