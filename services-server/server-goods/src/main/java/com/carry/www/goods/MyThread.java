package com.carry.www.goods;

import com.carry.www.lock.AquiredLockWorker;
import com.carry.www.lock.DistributedLocker;
import lombok.extern.slf4j.Slf4j;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月25日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
//@Component
@Slf4j
public class MyThread extends Thread {

    // 模拟这个是商品库存
    public static volatile Integer TOTAL = 10;

    private static DistributedLocker distributedLocker;

    public MyThread(DistributedLocker distributedLocker) {
        this.distributedLocker = distributedLocker;
    }

    public void run() {
        try {
            distributedLocker.lock("test", new AquiredLockWorker<Object>() {
                @Override
                public Object invokeAfterLockAquire() {
                    System.out.println("=================线程:" + Thread.currentThread().getName() + "================== 抢到锁!=================");

                    try {
                        if (TOTAL > 0) {
                            TOTAL--;
                        }

                        Thread.sleep(1000);
                        log.info("#################### 减完库存后,当前库存 ####################：" + TOTAL);
                        System.out.println("=================线程:" + Thread.currentThread().getName() + "================== 释放锁!=================");
                    } catch (Exception e) {
                        log.info("[ExecutorRedisson]获取锁失败");
                        e.printStackTrace();
                    }
                    return null;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
