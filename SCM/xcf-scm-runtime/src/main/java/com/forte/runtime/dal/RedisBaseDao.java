package com.forte.runtime.dal;

/**
 * Created by WangBin on 2016/4/5.
 */
public  abstract interface RedisBaseDao<K,V> {
    String generateId(String prefix, String method, Object... objects);

    boolean exists(K key);

    void set(String key, V value, long expire);

    V get(String key);

    int save(String key,V object);

    int delete(K key);

    /*List<V> queryForListPage(K key,Map map,Page page);

    List<V> queryForList(K key,Map map);*/
}
