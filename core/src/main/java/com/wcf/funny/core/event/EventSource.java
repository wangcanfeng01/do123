package com.wcf.funny.core.event;

import lombok.Data;

/**
 * @author wangcanfeng
 * @time 2019/3/11
 * @function 事件源信息
 **/
@Data
public class EventSource {
    /**
     * 容器中的bean的名称
     */
    private String beanName;

    /**
     * 目标类的全路径名
     */
    private String targetClass;

    /**
     * 参数信息
     */
    private Object[] args;

    /**
     * 方法名称
     */
    private String methodName;
}
