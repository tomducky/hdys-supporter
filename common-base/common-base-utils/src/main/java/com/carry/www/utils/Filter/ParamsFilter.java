package com.carry.www.utils.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：
 * 参数拦截器 拦截所有get请求的空格
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年06月21日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@WebFilter(filterName = "paramsFilter", urlPatterns = {"/*"})
public class ParamsFilter implements Filter {
    private Map<String, String[]> params = new HashMap<String, String[]>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Boolean flag =httpServletRequest.getRequestURI().indexOf("get") != -1||
                      httpServletRequest.getRequestURI().indexOf("select") != -1;
        if (flag) {
            this.params.putAll(request.getParameterMap());
            this.trimParameterValues();

            for (Iterator iterator = params.keySet().iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                String value = (String) params.get(key)[0];
                request.setAttribute(key, value);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    public void trimParameterValues() {
        Set<Map.Entry<String, String[]>> entrys = params.entrySet();
        for (Map.Entry<String, String[]> entry : entrys) {
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
            this.params.put(entry.getKey(), values);
        }
    }

}
