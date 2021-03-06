package com.fih.ishareing.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(filterName = "XssBodyFilter", urlPatterns = "/api/*")
public class XssBodyFilter implements Filter{

    FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        XssHttpServletRequestBodyWrapper xssRequest = new XssHttpServletRequestBodyWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

}
