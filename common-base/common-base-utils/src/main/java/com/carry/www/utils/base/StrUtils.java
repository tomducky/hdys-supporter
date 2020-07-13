package com.carry.www.utils.base;

import java.util.UUID;

/**
 * 类描述：字符串UTILS类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月08日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class StrUtils {

    /** 
    * @方法描述: 删除字符串后面的0
    * @return: java.lang.String
    * @Author: carry
    */
    public static String deleteZero(String str){

        if(str.endsWith("0")){
            str =str.substring(0,str.length()-1);
            return deleteZero(str);
        }

        return str;
    }

}
