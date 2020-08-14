package com.carry.www.account.controller;

import com.carry.www.account.service.AccountService;
import com.carry.www.openfeign.api.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 16:19
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@RestController
@RequestMapping("/account")
public class AccountController implements AccountClient {

    @Autowired
    private AccountService accountService;

    /**
     * @方法描述: 扣除账户金额
     * @Param: [accountId 账户id, money 减少的金额]
     * @return: void
     * @Author: carry
     */
    @Override
    @PostMapping("/reduceAccount")
    public void reduceAccount(@RequestParam String accountId, @RequestParam BigDecimal money) {
        accountService.reduceAccount(accountId, money);
    }
}