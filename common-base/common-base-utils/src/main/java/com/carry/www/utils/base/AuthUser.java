package com.carry.www.utils.base;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @方法描述:  用户基础信息类
 * @Param:
 * @return:
 * @Author: carry
 */
@Data
public class AuthUser {

    private static final long serialVersionUID = 1L;

    // tokenKey
    private String tokenId;
    // 用户ID
    private String userId;
    // 登录名称
    private String loginname;
    // 用户昵称
    private String nickname;
    // 用户姓名
    private String realName;
    // 电话
    private String phonenumber;
    // 部门ID
    private String deptId;
    // 部门名称
    private String deptName;
    // 区划编码
    private String addvcd;
    // 区划名称
    private String addvcdName;
    // 密码
    private String password;
    // 角色数组
    private String[] roleIds;
    // 角色集合
    private Collection<GrantedAuthority> authorities;

}
