package com.carry.www.seata.config;


import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
/**
 * 类描述：
 *  Seata所需数据库代理配置类
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 15:53
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Configuration
public class DataSourceProxyAutoConfiguration {

    //数据源属性配置
    private DataSourceProperties dataSourceProperties;

    public DataSourceProxyAutoConfiguration(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    /**
     * @方法描述:    配置数据源代理，用于事务回滚
     * @Param: []
     * @return: javax.activation.DataSource
     * @Author: carry
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        return new DataSourceProxy(dataSource);
    }
}
