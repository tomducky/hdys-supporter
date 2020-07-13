package com.carry.www.common.config;


import com.carry.www.security.UserAuthenticationProvider;
import com.carry.www.security.UserPermissionEvaluator;
import com.carry.www.security.handle.*;
import com.carry.www.security.jwt.JWTAuthenticationTokenFilter;
import com.carry.www.utils.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 类描述： SpringSecurity核心配置类
 *
 * @author carry
 * @version 1.0        CreateDate: 2020年2月24日
 * <p>
 * 修订历史：
 * 日期			修订者		修订描述
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的 使用表达式方法级别的安全性
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;

    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private UserLoginFailureHandler userLoginFailureHandler;

    /**
     * 自定义注销成功处理器
     */
    @Autowired
    private UserLogoutSuccessHandler userLogoutSuccessHandler;

    /**
     * 自定义暂无权限处理器
     */
    @Autowired
    private UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;

    /**
     * 自定义未登录的处理器
     */
    @Autowired
    private UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;

    /**
     * 自定义登录逻辑验证器
     */
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    /**
     * 集成第三方单点登录
     */
    @Autowired
    BaseUrl baseUrl;

    /**
     * @方法描述:  加密方式
     * @Param: []
     * @return: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
     * @Author: carry
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @方法描述:  自定义权限验证
     * @Param: []
     * @return: org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
     * @Author: carry
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    /**
     * @方法描述:   自定义登录验证逻辑
     * @Param: [auth]
     * @return: void
     * @Author: carry
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }

    /**
     * @方法描述: security主配置
     * @Param: [http]
     * @return: void
     * @Author: carry
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //####################登录、退出等验证 START###################################################
        http.authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取,比如主页、登录、spring boot admin监控页面)
                .antMatchers(Constants.antMatchers.split(",")).permitAll()
                // 其他的需要身份验证
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                // 配置登录地址,前端需要form表单的形式提交
                .formLogin()
                .loginProcessingUrl("/login/userLogin")
                // 配置登录成功自定义处理类
                .successHandler(userLoginSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(userLoginFailureHandler)
                .and()
                // 配置登出地址
                .logout()
                .logoutUrl("/login/userLogout")
                // 配置用户登出自定义处理类
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();
        //####################登录、退出等验证 END###################################################

        //####################jwt 拦截 START###################################################
        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 基于Token禁用缓存
        http.headers().cacheControl();
        // 添加JWT过滤器
        http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
        //####################jwt 拦截 START###################################################

        //####################ssoLogin拦截 登录前拦截 START###################################################
        http.addFilterBefore(new SelfSsoAuthenticationFilter("/login/userLogin", baseUrl.getHttpUlr()), UsernamePasswordAuthenticationFilter.class);
        //####################ssoLogin拦截 登录前拦截 END###################################################

    }
}