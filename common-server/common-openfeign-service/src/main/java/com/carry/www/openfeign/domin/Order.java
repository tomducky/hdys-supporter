package com.carry.www.openfeign.domin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/14 15:16
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Data
public class Order implements Serializable {

    private String id;

    private String goodId;

    private String accountId;

    private BigDecimal price;
}
