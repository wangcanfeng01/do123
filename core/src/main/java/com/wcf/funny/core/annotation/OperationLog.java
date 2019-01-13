package com.wcf.funny.core.annotation;

import com.wcf.funny.core.constant.ActionObject;
import com.wcf.funny.core.constant.ActionType;

import java.lang.annotation.*;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 打印操作日志的注解
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OperationLog {

    /**
     * 操作类型
     * @return
     */
    ActionType action();

    /**
     * 操作对象
     * @return
     */
    ActionObject object();

    /**
     * 操作详情
     * @return
     */
    String details() default "";
}