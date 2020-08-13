package com.carry.www.lock.api;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 类描述：
 * 分布式锁核心类，封装redission的方法
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月25日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Slf4j
public class RedisLocker {

    private Redisson redisson;

    public RedisLocker(Redisson redisson) {
        this.redisson = redisson;
    }

    /**
     * @方法描述: lock锁操作
     * @Param: [lockName 锁key,默认超时30秒]
     * @return: void
     * @Author: carry
     */
    public void lock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock();
    }


    /**
     * @方法描述: lock锁操作
     * @Param: [lockName 锁key, leaseTime 超时时间]
     * @return: void
     * @Author: carry
     */
    public void lock(String lockName, long leaseTime) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock(leaseTime, TimeUnit.SECONDS);
    }

    /**
     * @方法描述: tryLock锁）
     * @Param: [lockName 锁的key, 默认10秒超时]
     * @return: boolean
     * @Author: carry
     */
    public boolean tryLock(String lockName) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLockSuccess = false;

        try {
            getLockSuccess = rLock.tryLock();
        } catch (Exception e) {
            log.error("获取Redisson分布式锁失败，lockName=" + lockName);
            e.printStackTrace();
        }

        return getLockSuccess;
    }

    /**
     * @方法描述: tryLock锁
     * @Param: [lockName 锁的key, waitTime 等待时间]
     * @return: boolean
     * @Author: carry
     */
    public boolean tryLock(String lockName, long waitTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;

        try {
            getLock = rLock.tryLock(waitTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁失败，lockName=" + lockName);
            e.printStackTrace();
        }

        return getLock;
    }

    /**
     * @方法描述: tryLock锁
     * @Param: [lockName 锁的key, waitTime 等待时间,leaseTime 超时时间]
     * @return: boolean
     * @Author: carry
     */
    public boolean tryLock(String lockName, long waitTime, long leaseTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;

        try {
            getLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁失败，lockName=" + lockName);
            e.printStackTrace();
        }

        return getLock;
    }

    /**
     * @方法描述: 解锁
     * @Param: [lockName 锁key]
     * @return: void
     * @Author: carry
     */
    public void unlock(String lockName) {
        redisson.getLock(lockName).unlock();
    }

}
