package com.carry.www.order.service;

import com.carry.www.openfeign.domin.Order;
import org.springframework.stereotype.Service;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Service
public interface OrderService {

    /**
     * @方法描述: 新增订单
     * @Param: [order]
     * @return: void
     * @Author: carry
     */
    void addOrder(Order order);
}
