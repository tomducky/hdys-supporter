package com.carry.www.filter;

import com.carry.www.utils.constant.Constants;
import com.carry.www.utils.spring.SpringUtils;
import com.carry.www.utils.jwt.JwtTokenUtil;
import com.carry.www.utils.redis.RedisUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 类描述：
 * 全局权限路由过滤器 进行鉴权判断token,无token获取过期则不予许访问其他服务
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    // url匹配器
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return -500;
    }

    /**
     * @方法描述:  全局权限认证过滤器
     * @Param: [exchange, chain]
     * @return: reactor.core.publisher.Mono<java.lang.Void>
     * @Author: carry
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String accessToken = this.getHeaderToken(exchange.getRequest());

        RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);

        // 不过滤登录认证服务
        if (pathMatcher.match("/api-auth/**", exchange.getRequest().getPath().value())) {
            return chain.filter(exchange);
        }

        // 不过滤登录认证服务
        if (pathMatcher.match("/api-secutiry/**", exchange.getRequest().getPath().value())) {
            return chain.filter(exchange);
        }

        // 其他服务判断token，进行拦截
        if (accessToken == null) {
            // 没有token ，则 报错 401，身份不合法。
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return responseJson(exchange.getResponse());
        } else {
            try {
                //redis中获取token
                String token = (String) redisUtils.get(accessToken);

                if (StringUtils.isBlank(token)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return responseJson(exchange.getResponse());
                }

                JwtTokenUtil jwtTokenUtil = SpringUtils.getBean(JwtTokenUtil.class);
                // 判断token是否过期
                Boolean flag = jwtTokenUtil.isExpiration(token);
                if (flag) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return responseJson(exchange.getResponse());
                }

            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return responseJson(exchange.getResponse());
            }
        }

        return chain.filter(exchange);
    }

    /**
    * @方法描述: 返回前台信息
    * @return: reactor.core.publisher.Mono<java.lang.Void>
    * @Author: carry
    */
    public Mono<Void> responseJson(ServerHttpResponse serverHttpResponse) {
        JSONObject message = new JSONObject();
        message.put("code", 401);
        message.put("msg", "用户身份不合法,无权限访问！");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(bits);
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 指定编码，否则在浏览器中会中文乱码
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

   /**
   * @方法描述: 判断请求中 是否 有token
   * @return: java.lang.String
   * @Author: carry
   */
    protected String getHeaderToken(ServerHttpRequest request) {
        List<String> strings = request.getHeaders().get(Constants.TOKEN_HEADER);
        String authToken = null;
        if (strings != null) {
            authToken = strings.get(0).substring("Bearer".length()).trim();
        }
        return authToken;
    }

}
