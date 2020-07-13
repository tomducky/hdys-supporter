package com.carry.www.utils.base;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 客户端工具类
 * 

 */
public class ServletUtils {

	/**
	 * @方法描述: 获取String参数
	 * @Param: [name]
	 * @return: java.lang.String
	 * @Author: carry
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * @方法描述:  获取request
	 * @Param: []
	 * @return: javax.servlet.http.HttpServletRequest
	 * @Author: carry
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	/**
	 * @方法描述: 获取response
	 * @Param: []
	 * @return: javax.servlet.http.HttpServletResponse
	 * @Author: carry
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}


	/**
	 * @方法描述: 获取ServletRequestAttributes
	 * @Param: []
	 * @return: org.springframework.web.context.request.ServletRequestAttributes
	 * @Author: carry
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

	/**
	 * @方法描述:  获取头部相关信息
	 * @Param: [request, key]
	 * @return: java.lang.String
	 * @Author: carry
	 */
	public static String getHeader(ServletRequest request, String key) {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsRequest = (HttpServletRequest) request;
			return hsRequest.getHeader(key);
		}
		return null;
	}


}
