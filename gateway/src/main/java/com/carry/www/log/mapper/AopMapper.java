package com.carry.www.log.mapper;


import com.carry.www.log.domain.Aop;

import java.util.List;
import java.util.Map;

public interface AopMapper {

    /**
     * @方法描述: 获取AOP日志列表
     * @return: java.util.List<com.fencer.rcdd.aspect.log.domain.Aop>
     * @Author: carry
     */
    List<Aop> getAopLogList(Map<String, String> params);

    /**
     * @方法描述: 新增AOP日志信息
     * @return: void
     * @Author: carry
     */
    void addAopLog(Aop aop);

}
