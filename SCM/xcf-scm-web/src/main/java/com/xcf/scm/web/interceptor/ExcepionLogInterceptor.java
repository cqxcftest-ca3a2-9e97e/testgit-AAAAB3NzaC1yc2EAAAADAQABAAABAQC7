/** 
 * Project Name:forte-portal-web-web 
 * File Name:ExcepionLogInterceptor.java 
 * Package Name:com.forte.portalweb.web.interceptor 
 * Date:2016年3月10日下午1:16:03 
 * Autho:zhangxuesheng
 * Copyright (c) 2016, www.forte.com.cn All Rights Reserved. 
 * 
 */

package com.xcf.scm.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: ExcepionLogInterceptor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年3月10日 下午1:16:03 <br/>
 * 
 * @author zhangxuesheng
 * @version
 * @since JDK 1.7
 */
public class ExcepionLogInterceptor extends SimpleMappingExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		this.logger.error(ex.getMessage(), ex);
		return super.resolveException(request, response, handler, ex);
	}
}
