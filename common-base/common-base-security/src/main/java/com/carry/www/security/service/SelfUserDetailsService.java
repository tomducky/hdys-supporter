package com.carry.www.security.service;

import com.carry.www.core.service.AuthService;
import com.carry.www.security.entity.SelfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * SpringSecurity用户的业务实现
 *
 * @author carry
 * @version 1.0        CreateDate: 2020年2月24日
 * <p>
 * 修订历史：
 * 日期			修订者		修订描述
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthService authService;

    /**
     * @方法描述:查询用户信息 封装UserDetails
     * @Param: [username]
     * @return: com.carry.www.security.entity.SelfUserDetails
     * @Author: carry
     */
    @Override
    public SelfUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        SelfUserDetails selfUserDetails = authService.selectUserByName(username);

        return selfUserDetails;

    }
}