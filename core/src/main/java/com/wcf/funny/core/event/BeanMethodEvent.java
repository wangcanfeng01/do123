package com.wcf.funny.core.event;

import com.wcf.funny.core.exception.CommonException;
import com.wcf.funny.core.exception.errorcode.CommonCode;
import org.springframework.util.ObjectUtils;

/**
 * @author wangcanfeng
 * @time 2019/3/11
 * @function 继承了基类的核心事件功能，然后利用静态方法构造对象
 **/
public class BeanMethodEvent extends CoreEvent {
    /**
     * 功能描述: 创建事件对象的构造函数
     *
     * @param args   方法参数
     * @param method 方法名称
     * @return:
     * @since: v1.0
     * @Author:
     * @Date:
     */
    public static BeanMethodEvent create(Object[] args, String beanName, String method) {
        EventSource info = new EventSource();
        // 因为这个是通过调用springboot中注入的bean的方法，所以bean的名称不能为空
        if(ObjectUtils.isEmpty(beanName)){
            throw new CommonException(CommonCode.BEAN_NAME_NULL);
        }
        info.setArgs(args);
        info.setBeanName(beanName);
        info.setMethodName(method);
        return new BeanMethodEvent(info);
    }

    /**
     * 功能描述: 继承了父类的构造函数，修饰为私有的构造函数，不想让上层业务直接通过构造函数传入参数
     *
     * @param info
     * @return:
     * @since: v1.0
     * @Author:
     * @Date:
     */
    private BeanMethodEvent(EventSource info) {
        super(info);
    }
}
