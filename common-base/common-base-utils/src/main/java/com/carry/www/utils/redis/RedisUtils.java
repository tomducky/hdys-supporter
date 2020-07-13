package com.carry.www.utils.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 类描述：Redis工具类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================

    /**
     * @方法描述: 指定缓存失效时间
     * @Param: [key, time]
     * @return: boolean
     * @Author: carry
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * @方法描述:   根据key 获取过期时间
     * @Param: [key]
     * @return: long
     * @Author: carry
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * @方法描述: 判断key是否存在
     * @Param: [key]
     * @return: boolean
     * @Author: carry
     */
    public boolean hasKey(String key) {
        try {

            return redisTemplate.hasKey(key);
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * @方法描述:  删除缓存 ,多个kei值
     * @Param: [key]
     * @return: void
     * @Author: carry
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ============================String=============================

   /**
    * @方法描述:    普通缓存获取
    * @Param: [key]
    * @return: java.lang.Object
    * @Author: carry
    */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * @方法描述: 普通缓存放入
     * @Param: [key, value]
     * @return: boolean
     * @Author: carry
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @方法描述:  普通缓存放入并设置时间
     * @Param: [key, value, time]
     * @return: boolean
     * @Author: carry
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }


    // ================================Map=================================

   /**
    * @方法描述:  HashGet 获取对象
    * @Param: [key, item]
    * @return: java.lang.Object
    * @Author: carry
    */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }


    /**
     * @方法描述: 获取hashKey对应的所有键值
     * @Param: [key]
     * @return: java.util.Map<java.lang.Object,java.lang.Object>
     * @Author: carry
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

   /**
    * @方法描述:HashSet
    * @Param: [key, map]
    * @return: boolean
    * @Author: carry
    */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

   /**
    * @方法描述:     HashSet 并设置时间
    * @Param: [key, map, time]
    * @return: boolean
    * @Author: carry
    */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  /**
   * @方法描述:  向一张hash表中放入数据,如果不存在将创建
   * @Param: [key, item, value]
   * @return: boolean
   * @Author: carry
   */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

   /**
    * @方法描述:向一张hash表中放入数据,如果不存在将创建 并加入时间
    * @Param: [key, item, value, time]
    * @return: boolean
    * @Author: carry
    */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   /**
    * @方法描述:  删除hash表中的值
    * @Param: [key, item]
    * @return: void
    * @Author: carry
    */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

   /**
    * @方法描述:  判断hash表中是否有该项的值
    * @Param: [key, item]
    * @return: boolean
    * @Author: carry
    */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }


    // ============================set=============================

   /**
    * @方法描述:  根据key获取Set中的所有值
    * @Param: [key]
    * @return: java.util.Set<java.lang.Object>
    * @Author: carry
    */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   /**
    * @方法描述:   根据value从一个set中查询,是否存在
    * @Param: [key, value]
    * @return: boolean
    * @Author: carry
    */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @方法描述:  将数据放入set缓存
     * @Param: [key, values]
     * @return: long
     * @Author: carry
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @方法描述:   将set数据放入缓存
     * @Param: [key, time, values]
     * @return: long
     * @Author: carry
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @方法描述:获取set缓存的长度
     * @Param: [key]
     * @return: long
     * @Author: carry
     */

    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @方法描述:移除值为value的
     * @Param: [key, values]
     * @return: long
     * @Author: carry
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

   /**
    * @方法描述:   获取list缓存的内容
    * @Param: [key, start, end]
    * @return: java.util.List<java.lang.Object>
    * @Author: carry
    */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @方法描述:获取list缓存的长度
     * @Param: [key]
     * @return: long
     * @Author: carry
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

   /**
    * @方法描述: 通过索引 获取list中的值
    * @Param: [key, index]
    * @return: java.lang.Object
    * @Author: carry
    */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @方法描述: 将list放入缓存
     * @Param: [key, value]
     * @return: boolean
     * @Author: carry
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

   /**
    * @方法描述:   将list放入缓存
    * @Param: [key, value, time]
    * @return: boolean
    * @Author: carry
    */

    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   /**
    * @方法描述: 将list放入缓存
    * @Param: [key, value]
    * @return: boolean
    * @Author: carry
    */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   /**
    * @方法描述: 将list放入缓存
    * @Param: [key, value, time]
    * @return: boolean
    * @Author: carry
    */

    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  /**
   * @方法描述:   根据索引修改list中的某条数据
   * @Param: [key, index, value]
   * @return: boolean
   * @Author: carry
   */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   /**
    * @方法描述:   移除N个值为value
    * @Param: [key, count, value]
    * @return: long
    * @Author: carry
    */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

   /**
    * @方法描述:  对象转byte数组
    * @Param: [obj]
    * @return: byte[]
    * @Author: carry
    */
    public static byte[] serializableUtil(Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bais = new ByteArrayOutputStream();
            ObjectOutputStream ois = new ObjectOutputStream(bais);
            ois.writeObject(obj);
            bytes = bais.toByteArray();
            ois.close();
            bais.close();
            return bytes;
        } catch (IOException e) {
            System.out.print("序列化失败");

        }
        return bytes;
    }

    /**
     * @方法描述:   /byte数组转对象
     * @Param: [bytes]
     * @return: java.lang.Object
     * @Author: carry
     */
    public static Object unSerialzableUtil(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = (Object) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
