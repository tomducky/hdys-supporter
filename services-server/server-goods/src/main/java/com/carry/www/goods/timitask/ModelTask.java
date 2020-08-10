package com.carry.www.goods.timitask;

import com.carry.www.goods.MyThread;
import com.carry.www.lock.RedisLocker;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;


// @SpringBootTest
// @RunWith(SpringRunner.class)
@Slf4j
@Component
public class ModelTask {

    @Autowired
    RedisLocker distributedLocker;

    @Async
    @Scheduled(cron = "0 5 * ? * *")
    public void testRedisLock() {
        for (int i = 0; i < 15; i++) {
            new MyThread(distributedLocker).start();

        }
    }

}












