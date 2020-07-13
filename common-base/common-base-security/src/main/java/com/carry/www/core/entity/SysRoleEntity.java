package com.carry.www.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：角色实体
 * @author carry
 * @version 1.0		CreateDate: 2020年2月24日
 * 
 * 修订历史：
 * 日期			修订者		修订描述
 */
@Data
public class SysRoleEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private String id;

	/**
	 * 角色编码
	 */
	private String code;
}
