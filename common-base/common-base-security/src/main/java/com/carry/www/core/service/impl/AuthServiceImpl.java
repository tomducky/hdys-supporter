/**
 * Copyright (C), 2019, 山东锋士信息技术有限公司
 * All right reserved.
 */
package com.carry.www.core.service.impl;

import com.carry.www.core.entity.SysMenuEntity;
import com.carry.www.core.entity.SysRoleEntity;
import com.carry.www.core.mapper.AuthMapper;
import com.carry.www.core.service.AuthService;
import com.carry.www.security.entity.SelfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author carry
 * @version 1.0        CreateDate: 2020年2月24日
 * <p>
 * 修订历史：
 * 日期			修订者		修订描述
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public SelfUserDetails selectUserByName(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        return authMapper.selectUserByName(map);
    }

    @Override
    public List<SysMenuEntity> selectSysMenuByUserId(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return authMapper.selectSysMenuByUserId(map);
    }

    @Override
    public List<SysRoleEntity> selectSysRoleByUserId(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return authMapper.selectSysRoleByUserId(map);
    }

    @Override
    public void addLoginLog(Map<String, Object> params) {
        authMapper.addLoginLog(params);
    }


}
