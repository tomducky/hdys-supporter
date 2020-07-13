package com.carry.www.aop.aopUtil;


import com.carry.www.utils.spring.SpringUtils;
import com.carry.www.utils.base.AuthUser;
import com.carry.www.utils.constant.Constants;
import com.carry.www.utils.redis.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：
 *   获取用户信息
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月12日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class UserUtil {

    public static AuthUser getUserInfo(){
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        String tokenHeader = request.getHeader(Constants.TOKEN_HEADER);

        String sysUserJsonStr = null;
        AuthUser sysUser = null;
        try {
            if(StringUtils.isNotBlank(tokenHeader) && tokenHeader.startsWith(Constants.TOKEN_PREFIX)){
                String reqToken = tokenHeader.replace(Constants.TOKEN_PREFIX, "").replaceAll(" ", "");
                RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
                sysUserJsonStr = String.valueOf(redisUtils.get(reqToken + "_" + Constants.APP_NAME + "_user"));
            }

            if(StringUtils.isNotBlank(sysUserJsonStr)){
                sysUser = (AuthUser) JSON.toJavaObject((JSON)JSON.parse(sysUserJsonStr), AuthUser.class);
            }
        } catch (Exception e) {
            sysUser=null;
        }

        return sysUser;
    }
}
