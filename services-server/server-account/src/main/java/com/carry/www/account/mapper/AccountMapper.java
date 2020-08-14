package com.carry.www.account.mapper;

import com.carry.www.openfeign.domin.Account;

import java.math.BigDecimal;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 16:28
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public interface AccountMapper {

    /**
     * @方法描述: 根据账户id获取账户信息
     * @Param: [accountId 账户]
     * @return: com.carry.www.openfeign.domin.Account
     * @Author: carry
     */
    Account getAccountInfoById(String accountId);

    /**
     * @方法描述: 扣除账户金额
     * @Param: [account账户信息]
     * @return: void
     * @Author: carry
     */
    void reduceAccount(Account account);

}
