package com.forte.runtime.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by WangBin on 2016/4/5.
 */
@Component
@Configuration
public class RedisConfiguration {

    public boolean isRedisEnabled() {
        return "true".equals(AppContextConfig.get("common.redis.enabled"));
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        if (!isRedisEnabled()) return null;

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setPoolConfig(jedisPoolConfig());
        /*JedisShardInfo info = new JedisShardInfo(
                AppContextConfig.get("redis.host"),
                Integer.valueOf(AppContextConfig.get("redis.port")),
                Integer.valueOf(AppContextConfig.get("redis.timeout",3000)),
                AppContextConfig.get("redis.name","redis-server")
        );
        factory.setShardInfo(info);*/
        factory.setHostName(AppContextConfig.get("redis.host"));
        factory.setPort(Integer.valueOf(AppContextConfig.get("redis.port")));
        factory.setUsePool(Boolean.valueOf(AppContextConfig.get("redis.usePool", true)));
        factory.setTimeout(Integer.valueOf(AppContextConfig.get("redis.timeout", 3000)));
        if (AppContextConfig.get("redis.need.password", "false").toLowerCase().equals("true")) {
            factory.setPassword(AppContextConfig.get("redis.password"));
        }
        return factory;
    }

    /*@Bean
    RedisSentinelConfiguration redisSentinelConfiguration(){
        RedisSentinelConfiguration conf = new RedisSentinelConfiguration();
        conf.setMaster(AppContextConfig.get("redis.master","redis-master"));
        conf.setSentinels(AppContextConfig.get("redis.Sentinels").split(";"));
        return conf;
    }*/

    @Bean
    JedisPoolConfig jedisPoolConfig() {
        if (!isRedisEnabled()) return null;

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.valueOf(AppContextConfig.get("redis.maxIdle", 10)));
        config.setMaxTotal(Integer.valueOf(AppContextConfig.get("redis.maxTotal", 10)));
        config.setMaxWaitMillis(Integer.valueOf(AppContextConfig.get("redis.maxWait", 3000)));
        config.setTestOnBorrow(Boolean.valueOf(AppContextConfig.get("redis.testOnBorrow", "true")));
        return config;
    }

    @Bean
    RedisTemplate redisTemplate() {
        if (!isRedisEnabled()) return null;

        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    StringRedisTemplate stringRedisTemplate() {
        if (!isRedisEnabled()) return null;

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
