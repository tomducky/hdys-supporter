package com.carry.www.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
public interface AccountService {
    
    /**
     * @方法描述: 扣除账户金额
     * @Param: [accountId 账户id, money 减少的金额]
     * @return: void
     * @Author: carry
     */
     void reduceAccount(String accountId, BigDecimal money);
}
