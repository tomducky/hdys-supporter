package com.carry.www.order.mapper;

import com.carry.www.openfeign.domin.Order;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public interface OrderMapper {

    /**
     * @方法描述: 新增订单
     * @Param: [order]
     * @return: void
     * @Author: carry
     */
    void addOrder(Order order);

    /**
     * @方法描述: 删除订单
     * @Param: [order]
     * @return: void
     * @Author: carry
     */
    void deleteOrder(Order order);

    /**
     * @方法描述: 根据订单id获取订单
     * @Param: [OrderId 订单id]
     * @return: com.carry.www.openfeign.domin.Order
     * @Author: carry
     */
    Order getOrderById(String OrderId);
}
