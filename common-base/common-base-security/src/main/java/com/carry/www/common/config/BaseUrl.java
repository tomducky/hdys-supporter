package com.carry.www.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月28日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
@Data
public class BaseUrl {

    @Value("${httpUrl.url15}")
    private String httpUlr;

}
