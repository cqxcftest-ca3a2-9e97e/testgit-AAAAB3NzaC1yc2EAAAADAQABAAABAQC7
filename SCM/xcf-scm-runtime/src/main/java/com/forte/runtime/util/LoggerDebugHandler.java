package com.forte.runtime.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.forte.runtime.util.XmlUtil;

public class LoggerDebugHandler {
	private static Logger logger = LoggerFactory.getLogger(LoggerDebugHandler.class);

	 /**
     * 在方法之前执行，输入参数
     * @param joinPoint 连接点信息
	 * @throws Throwable 
     */
    public Object around(ProceedingJoinPoint  joinPoint) throws Throwable{
           Object[] objs=joinPoint.getArgs();
           if(objs!=null && objs.length>0){
        	   for(Object obj:objs){
        		   logger.debug("请求参数！{}",XmlUtil.toXml(obj));
        	   }
           }
           return joinPoint.proceed();
    };
   
    /**
     * 对于要增加的方法，方法返回结果后， 输出参数
     * 方法
     * @param obj target执行后返回的结果
     */
    public Object afterReturning(Object obj){
    	 if(obj!=null){
    		 logger.debug("返回参数！{}",XmlUtil.toXml(obj));
    	 }
    	 return obj;
    };
    
	
}
