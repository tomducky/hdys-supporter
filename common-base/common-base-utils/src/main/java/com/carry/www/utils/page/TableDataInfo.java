package com.carry.www.utils.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述 表格分页数据对象
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月08日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Data
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    // 总记录数
    private long totalRows;
    // 列表数据
    private List<?> rows;
    // 消息状态码
    private int code;
    // 消息提示
    private String msg;

    public TableDataInfo()
    {
    }

    /**
     * @方法描述:  分页
     * @Param: [list, total]
     * @return:
     * @Author: carry
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.totalRows = total;
    }
}
