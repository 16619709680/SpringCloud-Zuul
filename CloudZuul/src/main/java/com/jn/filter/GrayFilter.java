package com.jn.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 使用过滤器实现灰度发布规则设定
 */
@Component
public class GrayFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
         RequestContext currentContext = RequestContext.getCurrentContext();
         HttpServletRequest request = currentContext.getRequest();
         String authorization = request.getHeader("Authorization");

         if(StringUtils.isNotBlank(authorization)){
             //伪代码
             if("000".equals(authorization)){
                 RibbonFilterContextHolder.getCurrentContext().add("version","v1");
             }else{
                 RibbonFilterContextHolder.getCurrentContext().add("version","v2");
             }
         }



        return null;
    }
}
