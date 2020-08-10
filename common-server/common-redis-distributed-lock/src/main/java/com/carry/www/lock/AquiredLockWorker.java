package com.carry.www.lock;


/**
 * 类描述：
 * 获取锁后需要处理的逻辑
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月25日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
