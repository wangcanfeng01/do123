package com.wcf.funny.config.listener;

import com.wcf.funny.core.event.CoreEvent;
import com.wcf.funny.core.exception.CommonException;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * @author wangcanfeng
 * @time 2019/3/11
 * @function 方法委托监听器
 **/
@Component("funnyMethodDelegateListener")
@Log4j2
public class FunnyMethodDelegateListener<T extends CoreEvent> implements ApplicationListener<T> {

    /**
     * 注入spring的上下文
     */
    @Autowired
    private ApplicationContext context;

    /**
     * 功能描述:
     *
     * @param event
     * @return:void
     * @since: v1.0
     * @Author:wangcanfeng
     * @Date: 2019/3/11 14:26
     */
    public void onApplicationEvent(CoreEvent event) {
        Object target = getTargetOrBean(event);
        // 没有获取到bean对象直接抛出异常
        if (ObjectUtils.isEmpty(target)) {
           log.error("the target or bean are both not find");
        }
        // 获取对象的方法数组
        Method[] methods = target.getClass().getMethods();
        Method targetMethod = null;
        // 遍历查询名称符合的方法
        for (Method method : methods) {
            if (method.getName().equals(event.getMethodName())) {
                targetMethod = method;
                break;
            }
        }
        if (ObjectUtils.isEmpty(targetMethod)) {
            log.error("can not find the method: " + event.getMethodName());
        }
        try {
            // 调用方法
            targetMethod.invoke(target, event.getArgs());
        } catch (Exception e) {
            log.error("invoke method failed",e);
        }
    }

    private Object getTargetOrBean(CoreEvent event) {
        Object target = null;
        //bean的名称和目前类的类型都为空，则直接返回空
        if (ObjectUtils.isEmpty(event.getBeanName()) && ObjectUtils.isEmpty(event.getTargetClass())) {
            return target;
        }
        //获取bean 或者实例化类对象
        if (!ObjectUtils.isEmpty(event.getTargetClass())) {
            try {
                target = Class.forName(event.getBeanName()).newInstance();
            } catch (Exception e) {
                throw new CommonException(CommonCode.CAN_NOT_FIND_CLASS,e);
            }
        } else {
            target = context.getBean(event.getBeanName());
            if(ObjectUtils.isEmpty(target)){
                throw new CommonException(CommonCode.CAN_NOT_FIND_BEAN);
            }
        }
        return target;
    }

}
