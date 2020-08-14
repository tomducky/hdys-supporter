package com.carry.www.goods.controller;

import com.carry.www.goods.service.GoodService;
import com.carry.www.openfeign.api.GoodClient;
import com.carry.www.openfeign.domin.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@RestController
public class GoodController implements GoodClient {

    @Autowired
    private GoodService goodService;

    /**
     * @方法描述: 根据商品id获取商品信息
     * @Param: [goodId 商品id]
     * @return: Good
     * @Author: carry
     */
    @Override
    @GetMapping("/getGoodById")
    public Good getGoodById(String goodId) {
        return goodService.getGoodById(goodId);
    }

    /**
     * @方法描述: 更新商品库存
     * @Param: [good 商品信息]
     * @return: void
     * @Author: carry
     */
    @Override
    @PostMapping("/reduceStock")
    public void reduceStock(String goodId, int stock) {
        goodService.reduceGoodStock(goodId, stock);
    }
}
