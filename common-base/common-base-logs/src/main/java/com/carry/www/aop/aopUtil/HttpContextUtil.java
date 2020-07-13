package com.carry.www.aop.aopUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 类描述：
 * 获取ServerHttpRequest
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月12日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class HttpContextUtil {

    @Autowired
    public ServerWebExchange exchange;

    public HttpContextUtil() {
    }

    public static HttpServletRequest getHttpServletRequest() {

        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public ServerHttpRequest getServerHttpRequest() {
        ServerHttpRequest request = exchange.getRequest();

        return request;
    }
}
