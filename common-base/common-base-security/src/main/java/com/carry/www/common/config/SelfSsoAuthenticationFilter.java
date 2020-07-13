package com.carry.www.common.config;

import com.carry.www.security.handle.SsoLoginException;
import com.carry.www.utils.base.AjaxResult;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类描述：
 * 登陆前置拦截类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月26日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class SelfSsoAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String httpUrl;

    //拦截单点登录url
    private String ssosUrl;

    public static boolean isSSoLogin = false;

    protected SelfSsoAuthenticationFilter(String defaultFilterProcessesUrl,String httpUrl) {
        super(defaultFilterProcessesUrl);
        this.ssosUrl = defaultFilterProcessesUrl;
        this.httpUrl = httpUrl;
    }

    protected SelfSsoAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getServletPath();
        BaseHttp baseHttp=BaseHttp.getInstance();
        if (ssosUrl.equals(url) && "POST".equalsIgnoreCase(req.getMethod())) {
            //解决单例模式下isSSoLogin公用的问题
            isSSoLogin = false;
            String loginCode = (String) req.getParameter("loginCode");
            //如果loginCode不为空 则认为是单点登陆过来的，根据第三方的api来判断
            if (StringUtils.isNotEmpty(loginCode)) {
                //三方接口返回的token
                String token = (String) req.getParameter("token");
                //测试用，正式环境可以去掉
                String selfTestFlag = (String) req.getParameter("selfTestFlag");
                //根据token调用三方验证token的接口
                //如果三方接口正确返回了用户信息，则代表此token是合法的，自动调用咱们自己的登录方法进行逻辑处理
                if (StringUtils.isNotEmpty(token)) {

                    //##############################自测用 START#####################################################
                    if (StringUtils.isNotEmpty(selfTestFlag)) {
                        String urlOf15 = "http://人以自己的接口";
                        String methodType = "GET";
                        try {
                            AjaxResult ajaxResult = baseHttp.http(urlOf15, methodType, null);
                            String code = ajaxResult.get("code").toString();
                            if ("0".equals(code)) {
                                JSONObject jsonObjectOfResult = JSONObject.fromObject(ajaxResult.get("result"));
                                String status = (String) jsonObjectOfResult.get("status");
                                if ("ok".equals(status)) {
                                    isSSoLogin = true;
                                } else {
                                    throw new SsoLoginException("单点登录异常");
                                }
                            }else {
                                throw new SsoLoginException("单点登录异常");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new SsoLoginException("单点登录异常");
                        }
                    //##############################自测用 END#####################################################
                    } else {
                        //三方单点登录验证
                       String urlOf15 = httpUrl + token;
                        String methodType = "GET";
                        try {
                            AjaxResult ajaxResult = baseHttp.http(urlOf15, methodType, null);
                            String code = ajaxResult.get("code").toString();
                            if ("0".equals(code)) {
                                JSONObject jsonObjectOfResult = JSONObject.fromObject(ajaxResult.get("result"));
                                String codeOf15 = (String) jsonObjectOfResult.get("code");
                                // 根据三方接口判断
                                if ("20000".equals(codeOf15)) {
                                    isSSoLogin = true;
                                } else {
                                    throw new SsoLoginException("单点登录异常");
                                }
                            }
                        } catch (Exception e) {
                             e.printStackTrace();
                            throw new SsoLoginException("单点登录异常");
                        }
                    }
                } else {
                    throw new SsoLoginException("单点登录异常");
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
