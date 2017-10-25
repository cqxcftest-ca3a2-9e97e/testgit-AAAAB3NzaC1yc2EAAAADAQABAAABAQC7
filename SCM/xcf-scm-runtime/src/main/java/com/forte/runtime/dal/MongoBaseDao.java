package com.forte.runtime.dal;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Mongodb基础服务封装,
 * 暴露MongoTemplate,MongoClient接口
 *
 * Created by WangBin on 2016/4/28.
 */
public interface MongoBaseDao<K,T> {

    public void insertData(T o);

    public void deleteDataByPK(K id);

    public void deleteData(T o);

    public void updateData(T o);

    public T queryObjectByPK(K id);

    public List<T> queryAll();

    public PageImpl<T> queryForListByPagination(T o, PageRequest pageRequest);

    public T queryObject(T o);

    public List<T> queryForList(T o);
}
