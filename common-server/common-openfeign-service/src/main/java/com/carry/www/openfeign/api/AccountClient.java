package com.carry.www.openfeign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 15:00
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@FeignClient(name = "api-account")
public interface AccountClient {

    /**
     * @方法描述: 扣除账户金额
     * @Param: [accountId 账户编号, money 金额]
     * @return: void
     * @Author: carry
     */
    @PostMapping("/reduceAccount")
    void reduceAccount(@RequestParam String accountId, @RequestParam BigDecimal money);
}
