package com.xcf.scm.web.filter;

import com.forte.runtime.spring.AppContextConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by WangBin on 2016/5/27.
 */
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String loginUrl = AppContextConfig.get("loginURL", "/login");
        HttpServletRequest r = (HttpServletRequest)request;
        HttpServletResponse rsp = (HttpServletResponse)response;
        /*if(r.getServletPath().startsWith(loginUrl) ||
                r.getSession().getAttribute("user")!=null){
            //modelAndView.setViewName("index");
            filterChain.doFilter(request, response);
        }else {
            rsp.sendRedirect(r.getContextPath() + loginUrl);
        }*/
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
