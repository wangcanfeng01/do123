package com.wcf.funny.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wangcanfeng
 * @time 2019/3/11
 * @function 基类的事件信息
 **/
public abstract class CoreEvent extends ApplicationEvent {
    /**
     * 功能描述: 创建事件对象的构造函数
     *
     * @param info 方法参数
     * @return:
     * @since: v1.0
     * @Author:
     * @Date:
     */
    public CoreEvent(EventSource info) {
        super(info);
    }

    /**
     * 功能描述：获取实体类的全路径名
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/11 22:30
     * @since v1.0
     **/
    public String getTargetClass() {

        return ((EventSource) this.getSource()).getTargetClass();
    }

    /**
     * 功能描述：获取方法名称
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/11 22:30
     * @since v1.0
     **/
    public String getMethodName() {
        return ((EventSource) this.getSource()).getMethodName();
    }

    /**
     * 功能描述：获取参数信息
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/11 22:30
     * @since v1.0
     **/
    public Object[] getArgs() {
        return ((EventSource) this.getSource()).getArgs();
    }

    /**
     * 功能描述：获取bean的名称
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/11 22:30
     * @since v1.0
     **/
    public String getBeanName() {
        return ((EventSource) this.getSource()).getBeanName();
    }
}
