package com.carry.www.security.handle;


import com.carry.www.common.utils.ResultUtil;
import com.carry.www.core.service.AuthService;
import com.carry.www.security.entity.SelfUserDetails;
import com.carry.www.utils.base.IpUtils;
import com.carry.www.utils.base.UUIDUtil;
import com.carry.www.utils.constant.Constants;
import com.carry.www.utils.redis.RedisUtils;
import com.carry.www.utils.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登出成功处理类
 * 
 * @author carry
 * @version 1.0 CreateDate: 2020年2月24日
 * 
 *          修订历史： 日期 修订者 修订描述
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private AuthService authService;

	private Map<String, Object> logParam = new HashMap<String, Object>();

	/**
	 * 用户登出返回结果 这里应该让前端清除掉Token
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

		RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
		String tokenHeader = request.getHeader(Constants.TOKEN_HEADER);
		// 清除redis中存储的返回用户的实际 uuid
		redisUtils.del(tokenHeader);
		// 获取用户身份信息
		SelfUserDetails selfUserDetails = (SelfUserDetails) authentication.getPrincipal();
		String username = "";
		if (selfUserDetails != null) {
			username = selfUserDetails.getUsername();
			// 清除redis中存储的token信息 和用户身份信息
			redisUtils.del(Constants.APP_NAME + "_" + username + "_token");
			redisUtils.del(tokenHeader + "_"  + Constants.APP_NAME + "_user");
		}

		// 记录退出日志
		logParam.put("log_id", UUIDUtil.createUUID());
		logParam.put("login_type", "2");
		logParam.put("login_user", username);
		logParam.put("login_ip", IpUtils.getIpAddr(request));
		logParam.put("remark", "用户退出成功");
		ServletContext sc = request.getServletContext();
		WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(sc);
		if (cxt != null && cxt.getBean(AuthService.class) != null && authService == null) {
			authService = cxt.getBean(AuthService.class);
		}
		authService.addLoginLog(logParam);
		// 记录退出日志
		
		Map<String, Object> resultData = new HashMap<>();
		resultData.put("code", 0);
		resultData.put("msg", "登出成功");
		SecurityContextHolder.clearContext();
		ResultUtil.responseJson(response, ResultUtil.resultSuccess(resultData));
	}
}