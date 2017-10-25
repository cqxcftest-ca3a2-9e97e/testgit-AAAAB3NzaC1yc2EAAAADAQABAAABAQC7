package com.forte.runtime.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Field;

/**
 * 
 * <p>反射类</p>
 * Created by liaosifa on 2015年11月3日
 */
public class MongoQueryUtil
{
  private static final Logger logger = LoggerFactory.getLogger("MongoQueryUtil");

  public static Query setObjectQuery(Object target)
  {
	Query query = new Query();
    if(target == null)
    {
    	return query;
    }
    try {
    	Class clazz = target.getClass();
    	Field[] fields = clazz.getDeclaredFields();
    	if(fields!=null&&fields.length>0){
    		 for(int i = 0 ; i < fields.length; i++){  
    	           Field field = fields[i];  
    	           field.setAccessible(true); //设置些属性是可以访问的  
    	           Object val = field.get(target);//得到此属性的值     
    	           String fileName = field.getName();           	            
    	           String type = field.getType().toString();//得到此属性的类型  
    	           if(val!=null){
    	        	   query.addCriteria(Criteria.where(fileName).is(val));
//    	        	   if (type.endsWith("String") && val!=null) {  
//        	        	   query.addCriteria(Criteria.where(fileName).is(val));
//        	           }else if((type.endsWith("int") || type.endsWith("Integer")) && val!=null){  
//        	        	   query.addCriteria(Criteria.where(fileName).is(val));
//        	           }else{  
//        	              
//        	           }  
    	           }    	            
    	       }  
    	}   	
    }
    catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return query;
  }

  public static void main(String[] args){
	  Query query = new Query();
	  query.addCriteria(Criteria.where("zhan").is("1"));
  }
 
}