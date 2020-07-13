package com.carry.www.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：权限实体
 * @author carry
 * @version 1.0		CreateDate: 2020年2月24日
 * 
 * 修订历史：
 * 日期			修订者		修订描述
 */
@Data
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String permission;

}
