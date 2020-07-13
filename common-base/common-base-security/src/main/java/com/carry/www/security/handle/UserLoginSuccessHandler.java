package com.carry.www.security.handle;

import com.carry.www.common.utils.JWTTokenUtil;
import com.carry.www.common.utils.ResultUtil;
import com.carry.www.core.service.AuthService;
import com.carry.www.security.entity.SelfUserDetails;
import com.carry.www.utils.base.DateUtils;
import com.carry.www.utils.base.IpUtils;
import com.carry.www.utils.base.UUIDUtil;
import com.carry.www.utils.constant.Constants;
import com.carry.www.utils.redis.RedisUtils;
import com.carry.www.utils.spring.SpringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 登录成功处理类
 * 此处的2个token 1:真正的服务器token  2:返回给前台的虚拟token:uuid，也就是真正token的key
 * @author carry
 * @version 1.0 CreateDate: 2020年2月24日
 * <p>
 * 修订历史： 日期 修订者 修订描述
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AuthService authService;

    private Map<String, Object> logParam = new HashMap<String, Object>();

    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);

        //获取用户身份信息
        SelfUserDetails selfUserDetails = (SelfUserDetails) authentication.getPrincipal();
        String username = "";
        if (selfUserDetails != null) {
            username = selfUserDetails.getUsername();
        }

        // 根据当前登录用户名,判断redis中,是否已经生成过token(多个浏览器同时登陆，只生成一个真实的token)
        String realToken = String.valueOf(redisUtils.get(Constants.APP_NAME + "_" + username + "_token"));
        // JWT判断 token 无效时 重新生成token 放入 redis中，否则使用原有的token
        if (StringUtils.isNotBlank(realToken) && JWTTokenUtil.isExpiration(realToken)) {
            realToken = JWTTokenUtil.createAccessToken(selfUserDetails);
            //和JWT失效时间一致 Constants.expiration
            redisUtils.set(Constants.APP_NAME + "_" + username + "_token", realToken, Constants.EXPIRATION_12);
        }

        // 生成返回给客户端的唯一标识,虚拟token,前端用
        // 不同的浏览器同时登陆同一用户,对应同一个后端token，所以此处使用多个uuid作为key,各自的退出互不影响
        String uuid = UUIDUtil.createUUID();
        // 存tokenId ，虚拟token， 后台获取用
        selfUserDetails.setTokenId(uuid);
        // 将 uuid 唯一标识 与 token 的关联关系 存储在 redis 中
        redisUtils.set(uuid, realToken);

        //###########################其他数据放缓存 START###############################
        //将当前登录的用户信息放入redis
        redisUtils.set(uuid+Constants.APP_NAME + "_" + username + "_token", JSONObject.fromObject(selfUserDetails).toString());

        //###########################其他数据放缓存 END###############################


        //###########################获取用户角色集合 START###############################
        Collection<GrantedAuthority> userRolesCollations = selfUserDetails.getAuthorities();
        List<String> roleList = new LinkedList<String>();
        for (GrantedAuthority grantedAuthority : userRolesCollations) {
            String authority = grantedAuthority.getAuthority();
            roleList.add(authority.replace("ROLE_", ""));
        }

        String[] roleIds = roleList.toArray(new String[0]);
        selfUserDetails.setRoleIds(roleIds);
        //###########################获取用户角色集合 END###############################


        // ####################################记录登录日志 START####################################
        logParam.put("id", uuid);
        logParam.put("user_id",selfUserDetails.getId());
        logParam.put("user_name", username);
        logParam.put("ip", IpUtils.getIpAddr(request));
        logParam.put("mark", "用户认证成功");
        logParam.put("time", DateUtils.getNowDateTimeFmt("yyyy-MM-dd hh24:mi:ss"));
        logParam.put("type", "0");
        logParam.put("mark", "用户认证成功");

        ServletContext sc = request.getServletContext();
        WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(sc);
        if (cxt != null && cxt.getBean(AuthService.class) != null && authService == null) {
            authService = cxt.getBean(AuthService.class);
        }
        try {
            authService.addLoginLog(logParam);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        // ####################################记录登录日志 END####################################

        // ###############################封装返回参数 给前台 START ##################################
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("code", 0);
        resultData.put("type", Constants.TOKEN_PREFIX);
        resultData.put("token", uuid);
        resultData.put("user", selfUserDetails);
        resultData.put("msg", "登录成功");
        // ###############################封装返回参数 给前台 END ##################################

        ResultUtil.responseJson(response, resultData);
    }
}