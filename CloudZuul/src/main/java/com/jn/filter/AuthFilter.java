package com.jn.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 鉴权过滤器
 */
@Component
public class AuthFilter extends ZuulFilter {
    /**
     * 过滤器类别
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器级别 越小优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 过滤器是否生效
     */
    @Override
    public boolean shouldFilter() {
        /**
         * 业务逻辑处理
         * 设置请求是否拦截
         * 根据请求Url
         */
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        //请求uri带有auth,执行此过滤器
        if (requestURI.contains("auth") || requestURI.contains("getport")) {
            System.out.println("请求URI:"+requestURI+"被拦截");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 鉴权逻辑
     */
    @Override
    public Object run() throws ZuulException {

        System.out.println("过滤器拦截业务逻辑处理：......");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        System.out.println("request:----->"+ request);
        String token = request.getHeader("Authorization");
        System.out.println("token:--->"+token);
        if(StringUtils.isNotBlank(token)){

        }else{
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            currentContext.setResponseBody("Auth fail");
        }
        return null;
    }
}
