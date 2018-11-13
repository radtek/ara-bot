package com.zhuiyi.aspect;

import com.zhuiyi.common.util.CustomTimeUtil;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author tree
 * @version 1.0
 * date: 2018/7/26 17:51
 * description:
 * own: zhuiyi
 */
@Aspect
@Component
@Slf4j
public class ModelHandleAspect {

    private static final String IDFIELD = "id";
    private static final String GMTCREATE = "gmtCreate";
    private static final String GMTMODIFIED = "gmtModified";

    /**
     * 对象保存时的切入点
     * 匹配com.zhuiyi.service包及其子包下的所有类的save开头的方法
     */
    @Pointcut("execution(* com.zhuiyi.service..*.save*(..))")
    public void saveHandle() {
    }

    /**
     * 对象保存时的切入点
     * 匹配com.zhuiyi.service包及其子包下的所有类的update开头的方法
     */
    @Pointcut("execution(* com.zhuiyi.service..*.update*(..))")
    public void updateHandle() {
    }

    /**
     * save方法调用前被调用
     *
     * @param joinPoint 切点处的数据
     */
    @Before("saveHandle()")
    public void doBeforeSaveAdvice(JoinPoint joinPoint) {
        log.trace("[SYS][SERVICE][SaveBefor][start...]");
        //获取当前时间
        Date nowTime = CustomTimeUtil.getNowTimeForDate();
        //获取当前切点内的参数信息
        Object[] obj = joinPoint.getArgs();
        BeanWrapper beanWrapper = new BeanWrapperImpl(obj[0]);
        //自动设置TYPE为Long类型的ID字段
        if (beanWrapper.isWritableProperty(IDFIELD) && beanWrapper.getPropertyType(IDFIELD) == Long.class && beanWrapper.getPropertyValue(IDFIELD) == null) {
            beanWrapper.setPropertyValue(IDFIELD, new DefaultKeyGenerator().generateKey().longValue());
        }
        // 设置创建时间
        if (beanWrapper.isWritableProperty(GMTCREATE) && beanWrapper.getPropertyValue(GMTCREATE) == null) {
            beanWrapper.setPropertyValue(GMTCREATE, nowTime);
        }
        // 设置修改时间
        if (beanWrapper.isWritableProperty(GMTMODIFIED) && beanWrapper.getPropertyValue(GMTMODIFIED) == null) {
            beanWrapper.setPropertyValue(GMTMODIFIED, nowTime);
        }
        log.trace("[SYS][SERVICE][SaveBefor][end...]");
    }

    /**
     * update方法调用前被调用
     *
     * @param joinPoint 切点处的数据
     */
    @Before("updateHandle()")
    public void doBeforeUpdateAdvice(JoinPoint joinPoint) {
        log.trace("[SYS][SERVICE][UpdateBefor][start...]");
        //获取当前时间
        Date nowTime = CustomTimeUtil.getNowTimeForDate();
        //获取当前切点内的参数信息
        Object[] obj = joinPoint.getArgs();
        BeanWrapper beanWrapper = new BeanWrapperImpl(obj[0]);
        // 设置修改时间
        if (beanWrapper.isWritableProperty(GMTMODIFIED) && beanWrapper.getPropertyValue(GMTMODIFIED) == null) {
            beanWrapper.setPropertyValue(GMTMODIFIED, nowTime);
        }
        log.trace("[SYS][SERVICE][UpdateBefor][end...]");
    }
}
