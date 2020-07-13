package com.carry.www.security.handle;

import org.springframework.security.core.AuthenticationException;

/**
 * 类描述：
 * 自定义登录异常类
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月26日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class SsoLoginException extends AuthenticationException {

    public SsoLoginException(String msg)
    {
        super(msg);
    }

}
