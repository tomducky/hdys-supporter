package com.carry.www.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * SpringSecurity用户的实体
 * 注意:这里必须要实现UserDetails接口
 *
 * @author carry
 * @version 1.0        CreateDate: 2020年2月24日
 * <p>
 * 修订历史：
 * 日期			修订者		修订描述
 */
@Data
public class SelfUserDetails implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;
    // tokenKey
    private String tokenId;

    // 用户ID
    private String id;

    // 登录名称
    private String loginName;

    // 用户昵称
    private String nickname;

    // 用户姓名
    private String realName;

    // 用户头像
    private String avatar;

    //
    private String eMail;

    // 0可用 1不可用
    private String status;

    // 电话
    private String phonenumber;

    // 部门ID
    private String deptId;
    // 部门名称
    private String deptName;

    // 密码
    private String password;

    // 角色数组
    private String[] roleIds;

    // 角色集合
    private Collection<GrantedAuthority> authorities;

    // 账户是否过期
    private boolean isAccountNonExpired = false;

    // 账户是否被锁定
    private boolean isAccountNonLocked = false;

    // 证书是否过期
    private boolean isCredentialsNonExpired = false;

    // 账户是否有效
    private boolean isEnabled = true;


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.realName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}