package net.sunnycore.intercontent.util;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ServletContextFilter implements Filter,Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -6785569668582818537L;

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ThreadLocalStorage.store(IocUtil.SERVLET_CONTEXT_KEY, httpServletRequest.getSession(true).getServletContext());
        ThreadLocalStorage.store(SecurityUtil.RESPONSE_KEY, response);
        ThreadLocalStorage.store(SecurityUtil.REQUEST_KEY, request);
        chain.doFilter(httpServletRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
}
