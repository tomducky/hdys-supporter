package com.carry.www.lock.config;

/**
 * 类描述：
 * 获取Redisson连接
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月25日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

@Component
public class RedissonConnector {

    Redisson redisson;


    public Redisson getClient() {
        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
//        redisson = Redisson.create(config);

        return (Redisson)Redisson.create();
    }

}
