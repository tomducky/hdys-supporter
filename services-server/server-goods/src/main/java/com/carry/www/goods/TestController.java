package com.carry.www.goods;

import com.carry.www.lock.api.RedisLocker;
import com.carry.www.lock.exception.UnableToAquireLockException;
import lombok.extern.slf4j.Slf4j;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/8/11 11:04
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    RedisLocker redisLocker;

    @Autowired
    Redisson redisson;

    static String goodskey = "test-lock";
    static String lockkey = "LockKey";

    @GetMapping("/testLock")
    public String testLock() {
        int TOTAL = Integer.parseInt(redisTemplate.opsForValue().get(goodskey));
        boolean lockSuccess = false;
        //RLock rLock = redisson.getLock(lockkey);

        try {

            // lockSuccess = rLock.tryLock();
            lockSuccess = redisLocker.tryLock(lockkey);

            if (lockSuccess) {
                System.out.println("=================线程:" + Thread.currentThread().getName() + "================== 获得锁!=================");
                if (TOTAL > 0) {
                    TOTAL--;
                }

                redisTemplate.opsForValue().set(goodskey, +TOTAL + "");

                Thread.sleep(1000);
                log.info("#################### 减完库存后,当前库存 ####################：" + TOTAL);
                System.out.println("=================线程:" + Thread.currentThread().getName() + "================== 释放锁!=================");
            } else {
                throw new UnableToAquireLockException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lockSuccess) {
                redisLocker.unlock(lockkey);
                //rLock.unlock();
            }
        }

        return "success";
    }
}
