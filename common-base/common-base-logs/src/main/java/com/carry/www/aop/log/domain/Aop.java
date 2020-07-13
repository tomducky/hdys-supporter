package com.carry.www.aop.log.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：aop实体类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月12日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Data
public class Aop implements Serializable {
    //日志id
    private String id;

    //操作用户
    private String userName;

    //操作
    private String operation;

    //类名
    private String clazz;

    //方法名
    private String methodName;

    //参数
    private String params;

    //操作时间
    private String operatTime;

    //操作日志内容
    private String content;

    //通知类型
    private String addviceType;

    //日志类型
    private String logType;

    //操作人id
    private String userId;

    //异常信息
    private String errorMsg;

}
