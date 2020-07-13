package com.carry.www.exception.handle;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 类描述：
 * 重写DefaultErrorWebExceptionHandler返回JSON异常信息
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

	public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
			ErrorProperties errorProperties, ApplicationContext applicationContext) {
		super(errorAttributes, resourceProperties, errorProperties, applicationContext);
	}

	/** 
	* @方法描述: 获取异常属性
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	* @Author: carry
	*/
	@Override
	protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
		int code = 500;
		Throwable error = super.getError(request);
		if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
			code = 404;
		}
		return response(code, this.buildMessage(request, error));
	}

	/** 
	* @方法描述: 指定响应处理方法为JSON处理的方法
	* @return: org.springframework.web.reactive.function.server.RouterFunction<org.springframework.web.reactive.function.server.ServerResponse>
	* @Author: carry
	*/
	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	/** 
	* @方法描述: 源码是获取status 重写getHttpStatus根据code获取对应的HttpStatus
	* @return: org.springframework.http.HttpStatus
	* @Author: carry
	*/
	@Override
	protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
		int statusCode = (int) errorAttributes.get("code");
		return HttpStatus.valueOf(statusCode);
	}

	/** 
	* @方法描述: 构建异常信息
	* @return: java.lang.String
	* @Author: carry
	*/
	private String buildMessage(ServerRequest request, Throwable ex) {
		StringBuilder message = new StringBuilder("Failed to handle request [");
		message.append(request.methodName());
		message.append(" ");
		message.append(request.uri());
		message.append("]");
		if (ex != null) {
			message.append(": ");
			message.append(ex.getMessage());
		}
		return message.toString();
	}

	/** 
	* @方法描述:  构建返回的JSON数据格式
	* @return: java.util.Map<java.lang.String,java.lang.Object>
	* @Author: carry
	*/
	public static Map<String, Object> response(int status, String errorMessage) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", status);
		map.put("msg", errorMessage);
		return map;
	}

}

