package com.carry.www.utils.base;

import java.util.UUID;

/**
 * 类描述：UUIDUtil类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月08日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class UUIDUtil {

    /**
     * @方法描述:  生成唯 UUID
     * @Param: []
     * @return: java.lang.String
     * @Author: carry
     */
    public static String createUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        return uuid;
    }

}
