package com.xcf.scm.web.filter;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by WangBin on 2016/6/23.
 */
public class FreemarkerFilter extends SiteMeshFilter {

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain)
            throws IOException, ServletException {
        try {
            super.doFilter(rq, rs, chain);
        }catch (ServletException ex){
            ex.printStackTrace(System.err);
            ((HttpServletResponse)rs).sendRedirect(((HttpServletRequest) rq).getContextPath() + "/login");
            //throw new FreemarkerResolveException("freemarker-exception",ex);
        }
    }
}
