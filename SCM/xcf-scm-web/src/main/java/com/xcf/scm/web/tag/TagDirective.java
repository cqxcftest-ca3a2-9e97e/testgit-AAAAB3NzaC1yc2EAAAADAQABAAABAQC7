/** 
 * Project Name:forte-portal-web-web 
 * File Name:TagDirective.java 
 * Package Name:com.forte.portalweb.web.tag 
 * Date:2016年3月10日下午1:07:18 
 * Autho:zhangxuesheng
 * Copyright (c) 2016, www.forte.com.cn All Rights Reserved. 
 * 
 */

package com.xcf.scm.web.tag;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * ClassName: TagDirective <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年3月10日 下午1:07:18 <br/>
 * 
 * @author zhangxuesheng
 * @version
 * @since JDK 1.7
 */
public class TagDirective implements TemplateDirectiveModel {

	public void execute(Environment env, Map params, TemplateModel[] model, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		TemplateModel templateModel = (TemplateModel) params.get("tagCode");
		String tagCode = templateModel.toString();

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		HttpSession session = request.getSession();
		/*ApUser user = (ApUser)session.getAttribute("user");

		Writer out = env.getOut();
		if (user != null) {
			if(user.isAdmin()){
				body.render(out);
				return;
			}
			List<CommunityMenu> jurisdictionList = user.getUrls();
			if(jurisdictionList!=null&&jurisdictionList.size()>0){
				for(CommunityMenu menu :jurisdictionList){
					if(tagCode.equals(menu.getId())){
						body.render(out);
						break;
					}
				}			
			}			
		}*/
	}

}
