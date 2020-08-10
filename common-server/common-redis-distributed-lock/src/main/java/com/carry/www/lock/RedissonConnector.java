package com.carry.www.lock;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月25日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 获取RedissonClient连接类
 */
@Component
public class RedissonConnector {

    RedissonClient redisson;
    // 覆盖默认的redis地址
//    @PostConstruct
//    public void init(){
//        Config config=new Config();
//
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        redisson = Redisson.create(config);
//    }

//    public RedissonClient getClient(){
//        return  redisson;
//    }

    public RedissonClient getClient(){
        return  Redisson.create();
    }

}
