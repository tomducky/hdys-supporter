/**
 * Copyright (C), 2018, 山东锋士信息技术有限公司
 * All right reserved.
 */
package com.carry.www.core.mapper;


import com.carry.www.core.entity.SysMenuEntity;
import com.carry.www.core.entity.SysRoleEntity;
import com.carry.www.security.entity.SelfUserDetails;

import java.util.List;
import java.util.Map;

/**
 * @author carry
 * @version 1.0        CreateDate: 2019年5月20日 下午2:01:51
 * @description
 */
public interface AuthMapper {

    /**
     * @方法描述:   通过用户名查询用户
     * @Param: [map]
     * @return: com.carry.www.core.entity.SysUserEntity
     * @Author: carry
     */
    SelfUserDetails selectUserByName(Map<String, Object> map);

    /**
     * @方法描述:  查询菜单集合
     * @Param: [map]
     * @return: java.util.List<com.carry.www.core.entity.SysMenuEntity>
     * @Author: carry
     */
    List<SysMenuEntity> selectSysMenuByUserId(Map<String, Object> map);

    /**
     * @方法描述:
     * @Param: [map]
     * @return: java.util.List<com.carry.www.core.entity.SysRoleEntity>
     * @Author: carry
     */
    List<SysRoleEntity> selectSysRoleByUserId(Map<String, Object> map);


    /**
     * @方法描述:  记录登录日志
     * @Param: [params]
     * @return: void
     * @Author: carry
     */
    void addLoginLog(Map<String, Object> params);

}
