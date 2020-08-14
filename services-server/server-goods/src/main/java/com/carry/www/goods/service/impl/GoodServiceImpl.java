package com.carry.www.goods.service.impl;

import com.carry.www.goods.mapper.GoodMapper;
import com.carry.www.openfeign.domin.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 14:56
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Service
public class GoodServiceImpl {

    @Autowired
    private GoodMapper goodMapper;

    /**
     * @方法描述: 根据商品id获取商品信息
     * @Param: [goodId 商品id]
     * @return: Good
     * @Author: carry
     */
    public Good getGoodById(String goodId) {
        return goodMapper.getGoodById(goodId);
    }

    /**
     * @方法描述: 更新商品库存
     * @Param: [good 商品信息]
     * @return: void
     * @Author: carry
     */
    @Transactional(rollbackFor = Exception.class)
    public void reduceStock(String goodId, int stock) {
        try {
            Good good = goodMapper.getGoodById(goodId);

            if (StringUtils.isEmpty(good)) {
                throw new RuntimeException("商品：" + goodId + "不存在！");
            }

            int num = good.getStock() - stock;
            if (num < 0) {
                throw new RuntimeException("商品：" + goodId + "库存不足！");
            }

            good.setStock(num);

            goodMapper.reduceGoodStock(good);
        } catch (Exception e) {
            throw new RuntimeException("更新商品库存失败:" + e.getMessage());
        }


    }
}
