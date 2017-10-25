package com.forte.runtime.test;

import com.forte.runtime.spring.RedisConfiguration;
import com.forte.runtime.startup.PropertyLoaderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * Created by WangBin on 2016/3/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PropertyLoaderConfig.class, RedisConfiguration.class})
public class RedisTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void testSave(){
        redisTemplate.opsForHash().put("test","12323","hello");
        redisTemplate.opsForValue().set("key11","1232323");
    }
    @Test
    public void testDel(){
        Set<String> keys = redisTemplate.keys("taaa*");
        redisTemplate.delete(keys);
    }
    @Test
    public void testQuery(){
        String key = "aaaaaaaaaaaaaaaaaaax";
        System.out.println("query-out:"+redisTemplate.opsForHash().get("test","12323"));
        System.out.println(redisTemplate.opsForValue().get("key11"));
    }
}

