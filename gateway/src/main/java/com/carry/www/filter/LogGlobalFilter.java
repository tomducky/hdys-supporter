package com.carry.www.filter;

import com.alibaba.fastjson.JSON;
import com.carry.www.log.domain.Aop;
import com.carry.www.log.service.AopService;
import com.carry.www.thread.SaveLogThread;
import com.carry.www.utils.base.AuthUser;
import com.carry.www.utils.base.BaseService;
import com.carry.www.utils.base.DateUtils;
import com.carry.www.utils.base.UUIDUtil;
import com.carry.www.utils.constant.Constants;
import com.carry.www.utils.jwt.JwtTokenUtil;
import com.carry.www.utils.redis.RedisUtils;
import com.carry.www.utils.spring.SpringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 类描述：
 * 全局日志过滤器
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
public class LogGlobalFilter implements GlobalFilter, Ordered {

    //自定义线程池
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // url匹配器
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired(required = false)
    private AopService aopService;

    @Override
    public int getOrder() {
        return -300;
    }

    /**
     * @方法描述: 全局日志过滤器
     * @Param: [exchange, chain]
     * @return: reactor.core.publisher.Mono<java.lang.Void>
     * @Author: carry
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 不过滤登录认证服务
        if (pathMatcher.match("/api-secutiry/**", exchange.getRequest().getPath().value())) {
            return chain.filter(exchange);
        }

        try {
            AuthUser sysUser = getLoginUser(request);

            Aop aop = new Aop();
            aop.setId(UUIDUtil.createUUID());
            aop.setUserId(sysUser.getUserId());
            aop.setUserName(sysUser.getRealName());
            aop.setOperatTime(DateUtils.getNowDateTimeFmt("yyyy-Mm-dd hh24:mi:ss"));
            aop.setIp(request.getRemoteAddress().getHostName());
            aop.setPath(request.getURI().getPath());
            aop.setMethodName(request.getMethod().name());
            aop.setParams(request.getQueryParams().toString());


            exchange.getAttributes().put("startTime", System.currentTimeMillis());

            //获取request body
            Flux<DataBuffer> cachedBody = exchange.getAttribute(Constants.CACHE_REQUEST_BODY_OBJECT_KEY);
            if (cachedBody != null) {
                String raw = toRaw(cachedBody);
                aop.setParams(raw);
            }

            //通过线程池来执行日志保存
            threadPoolExecutor.execute(new SaveLogThread(aop, aopService));

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.METHOD_FAILURE);
        }

        return chain.filter(exchange);
    }

    /**
     * @方法描述:
     * @Param: 获取用户信息
     * @return: com.carry.www.utils.base.AuthUser
     * @Author: carry
     */
    public AuthUser getLoginUser(ServerHttpRequest request) {
        String sysUserJsonStr = null;
        AuthUser sysUser = null;

        try {
            List<String> strings = request.getHeaders().get("Authorization");

            if (strings.size() > 0) {
                String reqToken = null;
                if (strings != null) {
                    reqToken = strings.get(0).substring("Bearer".length()).trim();
                }

                RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
                sysUserJsonStr = String.valueOf(redisUtils.get(reqToken + "_" + Constants.APP_NAME + "_user"));
            }

            if (StringUtils.isNotBlank(sysUserJsonStr)) {
                sysUser = (AuthUser) JSON.toJavaObject((JSON) JSON.parse(sysUserJsonStr), AuthUser.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sysUser = null;
        }

        return sysUser;
    }

    /**
     * @方法描述: 用于获取请求参数
     * @Param: [body]
     * @return: java.lang.String
     * @Author: carry
     */
    private static String toRaw(Flux<DataBuffer> body) {
        AtomicReference<String> rawRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            rawRef.set(Strings.fromUTF8ByteArray(bytes));
        });
        return rawRef.get();
    }

}
