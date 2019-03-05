package com.wcf.funny.core.annotation.aspect;

import com.wcf.funny.core.annotation.OperationLog;
import com.wcf.funny.core.constant.LogConstant;
import com.wcf.funny.core.entity.OperationLogInfo;
import com.wcf.funny.core.service.OperationLogService;
import com.wcf.funny.core.utils.FunnyTimeUtils;
import com.wcf.funny.core.utils.RequestUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author wangcanfeng
 * @time 2019/1/13
 * @function 操作日志注解切面
 **/
@Component
@Aspect
@Log4j2
public class OperationLogAspect {
    /**
     * 日志持久化服务
     */
    @Autowired
    private OperationLogService operationLogService;

    @Pointcut("@annotation(com.wcf.funny.core.annotation.OperationLog)")
    public void needLog() {

    }

    /**
     * 功能描述：对注解标注的方法做前后处理
     *
     * @param point
     * @author wangcanfeng
     * @time 2019/1/13 17:23
     * @since v1.0
     **/
    @Around("needLog()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        OperationLogInfo logInfo = null;
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取到注解对应的方法
        Method method = signature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog != null) {
            logInfo = getLog(operationLog);
        }
        //可以在方法执行过程中设置操作日志的相关内容
        Object obj = point.proceed();
        if (!ObjectUtils.isEmpty(logInfo)) {
            operationLogService.insertLog(logInfo);
        }
        return obj;
    }

    /**
     * 功能描述：处理异常抛出后的事情
     *
     * @param jp 切点信息
     * @param e  抛出的异常
     * @author wangcanfeng
     * @time 2019/1/13 17:23
     * @since v1.0
     **/
    @AfterThrowing(pointcut = "needLog()", throwing = "e")
    public void afterThrow(JoinPoint jp, Exception e) {
        OperationLogInfo logInfo = RequestUtils.getLogInfo();
        logInfo.setActionResult(LogConstant.ActionResult.FAILED);
        operationLogService.insertLog(logInfo);
    }

    /**
     * 功能描述：
     *
     * @param operationLog
     * @return com.wcf.funny.core.entity.OperationLogInfo
     * @author wangcanfeng
     * @time 2019/1/13 17:23
     * @since v1.0
     **/
    private OperationLogInfo getLog(OperationLog operationLog) {
        //获取当前请求内容
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        OperationLogInfo logInfo = new OperationLogInfo();
        //将日志信息插入到本地变量中
        RequestUtils.setLogInfo(logInfo);
        String username = request.getRemoteUser();
        logInfo.setAuthorName(username);
        logInfo.setCreateTime(FunnyTimeUtils.nowUnix());
        //设置操作类型
        if (!ObjectUtils.isEmpty(operationLog.action())) {
            logInfo.setActionType(operationLog.action());
        }
        //默认操作结果为成功
        logInfo.setActionResult(LogConstant.ActionResult.SUCCESS);
        logInfo.setActionInfo(operationLog.info());
        //操作日志的详情在注解对应的方法中插入
        logInfo.setIp(request.getRemoteHost());
        logInfo.setActionObject(operationLog.object());
        return logInfo;
    }
}
