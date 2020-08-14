package com.carry.www.order.controller;

import com.carry.www.openfeign.api.AccountClient;
import com.carry.www.openfeign.api.GoodClient;
import com.carry.www.openfeign.domin.Good;
import com.carry.www.openfeign.domin.Order;
import com.carry.www.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
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
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private GoodClient goodClient;

    @Autowired
    private OrderService orderService;

   /**
    * @方法描述: 下单服务
    * @Param: [goodId 商品id, accountId 账户id, buyCount 商品数量]
    * @return: java.lang.String
    * @Author: carry
    */
    @PostMapping("/submitOrder")
    @GlobalTransactional
    public String submitOrder(
            @RequestParam("goodId") String goodId,
            @RequestParam("accountId") String accountId,
            @RequestParam("buyCount") int buyCount) {

        Good good = goodClient.getGoodById(goodId);
        BigDecimal orderPrice = good.getPrice().multiply(new BigDecimal(buyCount));
        // 减少商品库存
        goodClient.reduceStock(goodId, buyCount);
        // 减少账户金额
        accountClient.reduceAccount(accountId, orderPrice);

        Order order = toOrder(goodId, accountId, orderPrice);
        orderService.addOrder(order);

        return "下单成功.";
    }

    /**
     * @方法描述:  封装订单信息
     * @Param: [goodId 商品id, accountId 账户id, orderPrice 订单金额]
     * @return: com.carry.www.openfeign.domin.Order
     * @Author: carry
     */
    private Order toOrder(String goodId, String accountId, BigDecimal orderPrice) {
        Order order = new Order();
        order.setGoodId(goodId);
        order.setAccountId(accountId);
        order.setPrice(orderPrice);

        return order;
    }

}
