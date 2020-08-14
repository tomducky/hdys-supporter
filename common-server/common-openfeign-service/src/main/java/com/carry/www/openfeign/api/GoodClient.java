package com.carry.www.openfeign.api;

import com.carry.www.openfeign.domin.Good;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 15:13
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@FeignClient(name = "api-good")
public interface GoodClient {

    /**
     * @方法描述: 查询商品基本信息
     * @Param: [goodId]
     * @return: Good
     * @Author: carry
     */
    @GetMapping("/getGoodById")
    Good getGoodById(@RequestParam String goodId);

    /**
     * @方法描述: 减少商品的库存
     * @Param: [goodId, stock]
     * @return: void
     * @Author: carry
     */
    @PostMapping("/reduceStock")
    void reduceStock(@RequestParam String goodId, @RequestParam int stock);

}
