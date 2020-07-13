package com.carry.www.eureka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 * 服务启动后调用的功能，预加载数据，例如：数据字典，配置信息等放到全局变量或者缓存（数据不要是经常变动的，因为项目启动后只加载一次）
 * 如果只是想打出服务启动的提示，建议在启动类里面继承CommandLineRunner，解决服务间依赖的问题
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
public class StartupRunner implements CommandLineRunner {
    /**
     * @方法描述: 继承CommandLineRunner 服务启动后执行run里面的方法 如果需要启动多个在类上加@Order(value=1)注解
     * @return: void
     * @Author: carry
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################### eureka 服务启动完成！######################");
    }
}
