package com.xcf.scm.dal.test;
import com.xcf.scm.dal.dao.AcctAccountDAO;
import com.xcf.scm.dal.model.AcctAccountDO;
import com.forte.runtime.dal.RedisBaseDaoImpl;
import com.forte.runtime.pagination.Page;
import com.forte.runtime.pagination.PaginationBean;

import java.util.List;
import java.util.Map;

/**
 * Created by WangBin on 2016/4/7.
 */
public class RedisDemoTest extends RedisBaseDaoImpl<String,AcctAccountDO> implements AcctAccountDAO {
    @Override
    public int insertData(AcctAccountDO acctAccountDO) {
        return 0;
    }

    @Override
    public int deleteDataByPK(Object o) {
        return 0;
    }

    @Override
    public int deleteData(AcctAccountDO acctAccountDO) {
        return 0;
    }

    @Override
    public int updateData(AcctAccountDO acctAccountDO) {
        return 0;
    }

    @Override
    public int updateDataByPK(AcctAccountDO acctAccountDO) {
        return 0;
    }

    @Override
    public AcctAccountDO queryObjectByPK(Object o) {
        return null;
    }

    @Override
    public int queryForInt(AcctAccountDO acctAccountDO) {
        return 0;
    }

    @Override
    public List<AcctAccountDO> queryForListAll() {
        return null;
    }

    @Override
    public List<AcctAccountDO> queryForList(AcctAccountDO acctAccountDO, int i, int i1) {
        return null;
    }

    @Override
    public PaginationBean<AcctAccountDO> queryForListByPagination(AcctAccountDO acctAccountDO, Page page) {
        return null;
    }

    @Override
    public AcctAccountDO queryObject(AcctAccountDO acctAccountDO) {
        return null;
    }

    @Override
    public List<AcctAccountDO> queryForList(AcctAccountDO acctAccountDO) {
        return null;
    }

    @Override
    public int batchInsertData(List<AcctAccountDO> list) {
        return 0;
    }

    @Override
    public int batchUpdateData(List<AcctAccountDO> list) {
        return 0;
    }

    @Override
    public void flushSession() {

    }

    @Override
    public PaginationBean<AcctAccountDO> queryForListByPagination(Map<String, Object> map, Page page) {
        return null;
    }
/*@Override
    public int deleteByPrimaryKey(String host, String user) {
        redisTemplate.delete(host+user);
        return 0;
    }

    @Override
    public int insert(AcctAccountDO record) {
        hashOperations.put("customer","id:"+generateId("customer","",new Date().getTime()),record);
        return 1;
    }

    @Override
    public CustomerDO selectByPrimaryKey(String host, String user) {
        return hashOperations.get("customer","keyId");
    }

    @Override
    public List<CustomerDO> selectAll() {
        return hashOperations.multiGet("customer",redisTemplate.keys("customer*"));
    }

    @Override
    public int updateByPrimaryKey(CustomerDO record) {
        hashOperations.put("customer","",record);
        return 1;
    }*/
}
