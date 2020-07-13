package com.carry.www.utils.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：字典基础类
 * 
 * @author carry
 * @version 1.0 CreateDate: 2019年4月28日
 * 
 *          修订历史： 日期 修订者 修订描述
 */
@Data
public class Dict implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private String id;

	// 类型
	private String type;

	// key
	private String key;

	// value
	private String value;
	
	//  order
	private String orderBy;


	
}
