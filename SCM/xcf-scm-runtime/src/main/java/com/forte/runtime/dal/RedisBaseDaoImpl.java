package com.forte.runtime.dal;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

/**
 * Created by WangBin on 2016/4/1.
 */
public abstract class RedisBaseDaoImpl<K,V> implements RedisBaseDao<K,V>,InitializingBean {

    @Autowired
    protected RedisTemplate redisTemplate;

    protected ValueOperations<String, V> valueOperations;
    protected HashOperations<String, K, V> hashOperations;
    protected ListOperations<String, V> listOperations;
    protected SetOperations<String, V> setOperations;

    private Class<K> hkClass;
    private Class<V> hvClass;

    /**
     *
     * @return String.class
     */
    private Class<K> getKClass(){
        if (hkClass == null) {
            hkClass = (Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return hkClass;
    }

    private Class<V> getVClass(){
        if (hvClass == null) {
            hvClass = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return hvClass;
    }

    @Override
    public String generateId(String prefix, String method, Object... objects) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);//o.getClass().getName()
        sb.append(method);
        for (Object obj : objects) {
            sb.append(obj.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean exists(K key) {
        return redisTemplate.hasKey(key.toString());
    }
    public void set(String key, V value, long expireSeconds) {
        valueOperations.set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    public V get(String key) {
        return valueOperations.get(key);
    }
    /*@Override
    public int save(String key,V object) {
        if(key == null || key.isEmpty()){
            key = this.generateId(this.getClass().getSimpleName(),
                    "-"+(int)(1000*Math.random())+"-",new Date().getTime());
        }
        redisTemplate.opsForList().leftPush(key,object);
        return 1;
    }*/

    @Override
    public int delete(K key) {
        redisTemplate.delete(key.toString());
        return 1;
    }

    @Override
    public int save(String key, V object) {
        valueOperations.set(key,object);
        return 1;
    }
/*@Override
    public List<V> queryForListPage(K key, Page page) {
        return redisTemplate.opsForList().range(key.toString(),
                page.getPageSize()*(page.getCurrPage()-1),
                page.getPageSize()*(page.getCurrPage()-1)+page.getPageSize()
                );
    }

    @Override
    public List<V> queryForList(K key) {
        long size = redisTemplate.opsForList().size(key.toString());
        if(size>1000){
            size = 1000;
        }
        return redisTemplate.opsForList().range(key.toString(),0,size-1);
    }*/

    @Override
    public void afterPropertiesSet() throws Exception {
        if(redisTemplate == null){
            return;
        }
        if(getKClass() == null || getVClass() == null){
            throw new IllegalArgumentException("获取泛型class失败");
        }
        //
        valueOperations = redisTemplate.opsForValue();
        hashOperations = redisTemplate.opsForHash();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        //
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<V>(getVClass()));
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<K>(getKClass()));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<V>(getVClass()));
    }
}
