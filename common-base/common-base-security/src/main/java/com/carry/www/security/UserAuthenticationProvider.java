package com.carry.www.security;

import com.carry.www.common.config.SelfSsoAuthenticationFilter;
import com.carry.www.core.entity.SysRoleEntity;
import com.carry.www.core.service.AuthService;
import com.carry.www.security.entity.SelfUserDetails;
import com.carry.www.security.service.SelfUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类描述：自定义登录验证
 *  调用自己重写SelfUserDetailsService 实现loadUserByUsername
 * @author carry
 * @version 1.0		CreateDate: 2020年2月24日
 * 
 * 修订历史：
 * 日期			修订者		修订描述
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
	
    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    
    @Autowired
    private AuthService authService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //是否是第三方单点过来的
        boolean isSsoLogin= SelfSsoAuthenticationFilter.isSSoLogin;
    	// 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询数据库用户是否存在
        SelfUserDetails userInfo = selfUserDetailsService.loadUserByUsername(userName);

        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        // 判断密码是否正确，security 是使用BCryptPasswordEncoder进行加密的
        if(!isSsoLogin){
            if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
                throw new BadCredentialsException("密码不正确！");
            }
        }

        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<SysRoleEntity> sysRoleEntityList = authService.selectSysRoleByUserId(userInfo.getId());
        for (SysRoleEntity sysRoleEntity: sysRoleEntityList){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getCode()));
        }

        userInfo.setAuthorities(authorities);

        // 登录成功 把用户的相关信息封装到Authentication
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}