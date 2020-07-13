/**
 * Copyright (C), 2018, 山东锋士信息技术有限公司
 * All right reserved.
 */
package com.carry.www.core.service;

import com.carry.www.core.entity.SysMenuEntity;
import com.carry.www.core.entity.SysRoleEntity;
import com.carry.www.security.entity.SelfUserDetails;

import java.util.List;
import java.util.Map;

/**
 * @author carry
 * @description
 * @version 1.0		CreateDate: 2019年5月20日 下午2:01:51 
 */
public interface AuthService {
	
	/**
	 * @方法描述:  通过用户名查询用户
	 * @Param: [username]
	 * @return: com.carry.www.core.entity.SysUserEntity
	 * @Author: carry
	 */
    SelfUserDetails selectUserByName(String username);
    
   /**
    * @方法描述:   查询菜单集合
    * @Param: [userId]
    * @return: java.util.List<com.carry.www.core.entity.SysMenuEntity>
    * @Author: carry
    */
    List<SysMenuEntity> selectSysMenuByUserId(String userId);
    
   /**
    * @方法描述:   查询用户角色
    * @Param: [userId]
    * @return: java.util.List<com.carry.www.core.entity.SysRoleEntity>
    * @Author: carry
    */
    List<SysRoleEntity> selectSysRoleByUserId(String userId);
    
    
    
    /**
     * @方法描述:   记录登录日志
     * @Param: [params]
     * @return: void
     * @Author: carry
     */
    void addLoginLog(Map<String, Object> params);


}
