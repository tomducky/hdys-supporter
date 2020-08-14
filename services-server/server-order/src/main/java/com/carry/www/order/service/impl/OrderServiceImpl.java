package com.carry.www.order.service.impl;

import com.carry.www.openfeign.domin.Order;
import com.carry.www.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Service
public class OrderServiceImpl {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * @方法描述: 新增订单
     * @Param: [order]
     * @return: void
     * @Author: carry
     */
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(Order order) {
        try {
            orderMapper.addOrder(order);
        } catch (Exception e) {
            throw new RuntimeException("新增订单失败：" + e.getMessage());
        }
    }
}
