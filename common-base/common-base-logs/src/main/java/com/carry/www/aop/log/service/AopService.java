package com.carry.www.aop.log.service;


import com.carry.www.aop.log.domain.Aop;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月12日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Service
public interface AopService {

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
