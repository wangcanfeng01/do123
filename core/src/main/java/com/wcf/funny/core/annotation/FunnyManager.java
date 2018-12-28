package com.wcf.funny.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author WCF
 * @time 2018/12/28
 * @function 管理器component别名，方便阅读
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface FunnyManager {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
