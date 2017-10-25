/** 
 * Project Name:forte-portal-web-web 
 * File Name:EnterInterceptor.java 
 * Package Name:com.forte.portalweb.web.interceptor 
 * Date:2016年3月16日下午1:21:39 
 * Autho:zhangxuesheng
 * Copyright (c) 2016, www.forte.com.cn All Rights Reserved. 
 * 
 */

package com.xcf.scm.web.interceptor;

import com.forte.runtime.spring.AppContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: EnterInterceptor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年3月16日 下午1:21:39 <br/>
 * 
 * @author zhangxuesheng
 * @version
 * @since JDK 1.7
 */
public class EnterInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(EnterInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ServletContext servletContext = request.getSession().getServletContext();
		servletContext.setAttribute("staticFileRoot", request.getContextPath());
		servletContext.setAttribute("staticFileVer",AppContextConfig.get("staticFileVer","?version=1.0"));
		servletContext.setAttribute("ctx", request.getContextPath());
		servletContext.setAttribute("requestPath", request.getRequestURI());
		String img = AppContextConfig.get("imgURL","/wyweb/img/download,/wyweb/index,/wyweb/logout");
		//servletContext.setAttribute("imgVerifyCodeUrl", imgVerifyCodeUrl);
		String upload = request.getContextPath()+"/ueditor/jsp/upload/image";
		if(request.getRequestURI().startsWith(upload)){
			return true;
		}
		String loginUrl = AppContextConfig.get("loginURL","/login");
		if(loginUrl.indexOf(request.getServletPath())>=0) {
			return true;
		}
		if(img.indexOf(request.getRequestURI())>=0){
			return true;
		}
		/*if(request.getSession().getAttribute("user")!=null){
			//modelAndView.setViewName("index");
			ApUser user = (ApUser)request.getSession().getAttribute("user");
			if(user.isAdmin()){
				return true;
			}
			List<CommunityMenu> menuList = user.getUrls();
			String ctx = request.getContextPath();
			for(CommunityMenu item : menuList){
				if(request.getRequestURI().equals(ctx + item.getUrl())){
					return true;
				}
			}
			logger.error("invalid-access,url="+request.getRequestURI());
			throw new AccessException("无操作权限");
			//return false;
		}
		response.sendRedirect(request.getContextPath()+loginUrl);
		return false;*/
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
