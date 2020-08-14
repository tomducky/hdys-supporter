package com.carry.www.account.service.impl;


import com.carry.www.account.mapper.AccountMapper;
import com.carry.www.openfeign.domin.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 16:19
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Service
public class AccountServiceImpl {

    @Autowired
    AccountMapper accountMapper;

    /**
     * @方法描述: 扣除账户金额
     * @Param: [accountId 账户id, money 减少的金额]
     * @return: void
     * @Author: carry
     */
    @Transactional(rollbackFor = Exception.class)
    public void reduceAccount(String accountId, BigDecimal money) {
        try {
            //获取账户id
            Account account = accountMapper.getAccountInfoById(accountId);

            if (StringUtils.isEmpty(account)) {
                throw new RuntimeException("账户：" + accountId + "不存在！");
            }

            if (account.getMoney().compareTo(money) == -1) {
                throw new RuntimeException("账户：" + accountId + "余额不足！");
            }

            account.setMoney(account.getMoney().subtract(money));

            accountMapper.reduceAccount(account);
        } catch (Exception e) {
            throw new RuntimeException("扣除账户金额失败:" + e.getMessage());
        }
    }


}
