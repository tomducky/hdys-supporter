package com.carry.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类描述：
 * 服务不通的熔断回调类
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@RestController
public class HystrixController {

    /** 
    * @方法描述: 默认熔断回调
    * @return: java.util.Map<java.lang.String,java.lang.String>
    * @Author: carry
    */
    @RequestMapping("/defaultfallback")
    public Map<String,String> defaultfallback(){
        Map<String,String> map = new LinkedHashMap<>();
        map.put("code", HttpStatus.INTERNAL_SERVER_ERROR+"");
        map.put("msg","网络连接超时，请稍后再试。");
        return map;
    }

}
