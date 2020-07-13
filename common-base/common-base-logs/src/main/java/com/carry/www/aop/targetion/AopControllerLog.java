package com.carry.www.aop.targetion;

import java.lang.annotation.*;

/**
 * 类描述：
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月13日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopControllerLog {

    /**
     * @方法描述: 描述业务
     * @Param: []
     * @return: java.lang.String
     * @Author: carry
     */
    String description() default "";

}
