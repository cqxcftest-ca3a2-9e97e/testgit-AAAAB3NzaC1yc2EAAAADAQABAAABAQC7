package com.forte.runtime.dal;

import java.util.List;
import java.util.Map;

import com.forte.runtime.pagination.Page;
import com.forte.runtime.pagination.PaginationBean;
/**
 * 
 * <p>myibatis公用DAO</p>
 * Created by liaosifa on 2015年11月23日
 */
public abstract interface BaseDao<T>
{
  public abstract int insertData(T paramT);

  public abstract int deleteDataByPK(Object paramObject);

  public abstract int deleteData(T paramT);

  public abstract int updateData(T paramT);

  public abstract int updateDataByPK(T paramT);

  public abstract T queryObjectByPK(Object paramObject);

  public abstract int queryForInt(T paramT);

  public abstract List<T> queryForListAll();

  public abstract List<T> queryForList(T paramT, int paramInt1, int paramInt2);

  public abstract PaginationBean<T> queryForListByPagination(T paramT, Page paramPage);

  public abstract T queryObject(T paramT);

  public abstract List<T> queryForList(T paramT);

  public abstract int batchInsertData(List<T> paramList);

  public abstract int batchUpdateData(List<T> paramList);

  public abstract void flushSession();

  public abstract PaginationBean<T> queryForListByPagination(Map<String, Object> paramMap, Page paramPage);
}