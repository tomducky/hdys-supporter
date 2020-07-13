package com.carry.www.common.utils;


import com.carry.www.security.entity.SelfUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *  Security工具类
 * @author carry
 * @version 1.0		CreateDate: 2020年2月24日
 * 
 * 修订历史：
 * 日期			修订者		修订描述
 */
public class SecurityUtil {

    /**
     * 私有化构造器
     */
    private SecurityUtil(){}

    /**
     * 获取当前用户信息
     */
    public static SelfUserDetails getUserInfo(){
        SelfUserDetails userDetails = (SelfUserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        return userDetails;
    }
    /**
     * 获取当前用户ID
     */
    public static String getUserId(){
        return getUserInfo().getId();
    }
    /**
     * 获取当前用户账号
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }
}