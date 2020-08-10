package com.carry.www.lock;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2019年07月25日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class UnableToAquireLockException  extends RuntimeException {

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }

}
