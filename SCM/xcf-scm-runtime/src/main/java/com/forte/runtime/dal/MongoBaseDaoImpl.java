package com.forte.runtime.dal;

import com.forte.runtime.util.MongoQueryUtil;
import com.forte.runtime.util.ReflectUtil;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by WangBin on 2016/4/28.
 */
public abstract class MongoBaseDaoImpl<K,T> implements MongoBaseDao<K,T> {
    protected String entityClassName;
    private Class<T> entityClass;

    @Autowired
    protected MongoTemplate mongoTemplate;
    @Autowired
    protected MongoClient mongoClient;

    public void initialize() throws Exception{
        initSqlName();
    }

    private void initSqlName() {
        this.entityClass = ReflectUtil.getSuperClassGenericType(getClass());
        this.entityClassName = this.entityClass.getSimpleName();
    }

    public void deleteDataByPK(K id) {
        Criteria criteria = Criteria.where("id").in(id);
        if(criteria == null){
            Query query = new Query(criteria);
            Object obj = mongoTemplate.findOne(query, this.entityClass);
            if(query != null &&obj!= null)
                mongoTemplate.remove(obj);
        }
    }

    public T queryObjectByPK(K id){
        Criteria criteria = Criteria.where("id").in(id);
        if(criteria == null){
            Query query = new Query(criteria);
            return mongoTemplate.findOne(query, this.entityClass);
        }
        return null;
    }

    public List<T> queryAll(){
        return mongoTemplate.find(new Query(), this.entityClass);
    }

    @Override
    public void insertData(T o) {
        mongoTemplate.save(o);
    }

    @Override
    public void deleteData(T o) {
        mongoTemplate.remove(o);
    }

    @Override
    public void updateData(T o) {
        mongoTemplate.save(o);
    }

    @Override
    public PageImpl<T> queryForListByPagination(T o, PageRequest pageRequest) {
        Query query = MongoQueryUtil.setObjectQuery(o);
        long count = mongoTemplate.count(query,this.entityClass);
        List<T> list = mongoTemplate.find(query.with(pageRequest), this.entityClass);
        PageImpl<T> result = new PageImpl<T>(list, pageRequest, count);
        return result;
    }

    @Override
    public T queryObject(T o) {
        Query query = MongoQueryUtil.setObjectQuery(o);
        return mongoTemplate.findOne(query, this.entityClass);
    }

    @Override
    public List<T> queryForList(T o) {
        Query query = MongoQueryUtil.setObjectQuery(o);
        return mongoTemplate.find(query, this.entityClass);
    }
}
