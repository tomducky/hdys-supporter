package com.carry.www.security.jwt;

import com.carry.www.common.utils.JWTTokenUtil;
import com.carry.www.security.entity.SelfUserDetails;
import com.carry.www.utils.constant.Constants;
import com.carry.www.utils.redis.RedisUtils;
import com.carry.www.utils.spring.SpringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JWT接口请求校验拦截器 请求接口时会进入这里验证Token是否合法和过期
 * 流程
 * 用户认证 → 服务器构造JWT → JWT返回客户端，客户端存储（本地缓存或其他） → 客户端访问服务器，header中带上JWT → 服务器端判断JWT是否正确并且没有超时，不同处理
 * @author carry
 * @version 1.0 CreateDate: 2020年2月24日
 * 
 *          修订历史： 日期 修订者 修订描述
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {

	private RedisUtils redisUtils;

	public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 获取请求头中JWT的Token,请求头部：Authorization Bearer XXXXXXXXX
		String tokenHeader = request.getHeader(Constants.TOKEN_HEADER);
		if (null != tokenHeader && tokenHeader.startsWith(Constants.TOKEN_PREFIX)) {
			try {

				if (redisUtils == null) {
					redisUtils = SpringUtils.getBean(RedisUtils.class);
				}

				// 去掉request中 token的前缀 Bearer ，获取前端传过来的真实的token，这里前端的token其实是真正token的key值
				String reqToken = tokenHeader.replace(Constants.TOKEN_PREFIX, "");
				// 根据key从redis 中获取真实的 token
				String token = (String)redisUtils.get(reqToken);
				
				//判断token 是否 已经过期
				Boolean flag = JWTTokenUtil.isExpiration(token);
				if(!flag){
					//在有效期内
					// 解析JWT 获取载荷
					Claims claims = Jwts.parser().setSigningKey(Constants.TOKEN_SECRET).parseClaimsJws(token)
							.getBody();

					// 获取用户信息
					String username = claims.getSubject();
					String userId = claims.getId();

					if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userId)) {
						// 获取角色
						List<GrantedAuthority> authorities = new ArrayList<>();
						String authority = claims.get("authorities").toString();

						if (!StringUtils.isEmpty(authority)) {
							List<Map<String, String>> authorityMap = Collections.singletonList(JSONObject.fromObject(authority));
							for (Map<String, String> role : authorityMap) {
								if (!StringUtils.isEmpty(role)) {
									authorities.add(new SimpleGrantedAuthority(role.get("authority")));
								}
							}
						}

						// 组装参数,此处也可以直接取数据库查询用户信息 userDetailsService.loadUserByUsername(username);
						SelfUserDetails selfUserDetails = new SelfUserDetails();
						selfUserDetails.setLoginName(claims.getSubject());
						selfUserDetails.setId(claims.getId());
						selfUserDetails.setAuthorities(authorities);

						//赋值当前的SecurityContext 后续使用SecurityContext中的Authentication对象进行相关的权限鉴定
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								selfUserDetails, null, authorities);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			} catch (ExpiredJwtException e) {
				log.info("Token过期");
			} catch (Exception e) {
				log.info("Token无效");
			}
		}
		filterChain.doFilter(request, response);
		return;
	}
}