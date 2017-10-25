/** 
 * Project Name:forte-portal-web-util 
 * File Name:DateConverter.java 
 * Package Name:com.forte.portalweb.util.converter 
 * Date:2016年3月10日上午10:20:32 
 * Autho:zhangxuesheng
 * Copyright (c) 2016, www.forte.com.cn All Rights Reserved. 
 * 
 */  
  
package com.xcf.scm.web.converter;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/** 
 * ClassName: DateConverter <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2016年3月10日 上午10:20:32 <br/> 
 * 
 * @author zhangxuesheng 
 * @version  
 * @since JDK 1.7 
 */
public class DateConverter implements WebBindingInitializer {

	/** 
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see WebBindingInitializer#initBinder(WebDataBinder, WebRequest)
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// TODO Auto-generated method stub
		binder.registerCustomEditor(Date.class, new DateEditor(DateEditor.TIMEFORMAT,true));
	}

	
}
