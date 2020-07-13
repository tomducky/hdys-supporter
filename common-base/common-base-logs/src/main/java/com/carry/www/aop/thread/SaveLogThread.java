package com.carry.www.aop.thread;

import com.carry.www.aop.log.domain.Aop;
import com.carry.www.aop.log.service.AopService;

/**
 * 类描述：
 * 异步保存AOp日志
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月13日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class SaveLogThread implements Runnable {

    private Aop aop;
    private AopService aopService;

    public SaveLogThread(Aop aop, AopService aopService) {
        this.aop = aop;
        this.aopService = aopService;
    }

    @Override
    public void run() {
        aopService.addAopLog(aop);
    }

}
