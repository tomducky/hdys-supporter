package com.carry.www.utils.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：树类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月08日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Data
public class TreeDict {

    private String value;

    private String label;

    private String isChild;

    private List<?> children = new ArrayList<>();
}