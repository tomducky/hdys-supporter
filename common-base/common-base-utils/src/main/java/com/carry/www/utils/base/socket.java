package com.carry.www.utils.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：socket工具类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年09月26日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Data
public class socket implements Serializable {
    //id
    private String id;

    //业务表id
    private String business_id;

    //通知题目
    private String title;

    //消息类型
    private String type;

    //通知人员
    private String user;

    //客户端是否已接接收消息
    private String is_reciive;

    private String create_date;
    private String create_user;
    private String update_date;
    private String update_user;
}
