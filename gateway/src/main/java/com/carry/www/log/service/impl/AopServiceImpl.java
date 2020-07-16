package com.carry.www.log.service.impl;


import com.carry.www.log.domain.Aop;
import com.carry.www.log.mapper.AopMapper;
import com.carry.www.log.service.AopService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AopServiceImpl implements AopService {

    @Autowired(required = false)
    public AopMapper aopMapper;

    /**
     * @方法描述: 获取AOP日志列表
     * @return: java.util.List<com.fencer.rcdd.aspect.log.domain.Aop>
     * @Author: carry
     */
    @Override
    public List<Aop> getAopLogList(Map<String, String> params) {
        return aopMapper.getAopLogList(params);
    }

    /**
     * @方法描述: 新增AOP日志信息
     * @return: void
     * @Author: carry
     */
    @Override
    public void addAopLog(Aop aop) {
        aopMapper.addAopLog(aop);
    }

}
