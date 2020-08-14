package com.carry.www.goods.mapper;

import com.carry.www.openfeign.domin.Good;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public interface GoodMapper {

    /**
     * @方法描述: 根据商品id获取商品信息
     * @Param: [goodId 商品id]
     * @return: Good
     * @Author: carry
     */
    Good getGoodById(String goodId);

    /**
     * @方法描述:  更新商品库存
     * @Param: [good 商品信息]
     * @return: void
     * @Author: carry
     */
    void reduceGoodStock(Good good);
}
