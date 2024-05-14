package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共填充
 */

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){

    }

    /**
     * 前置通知
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段填充");
        //获取到当前被拦截方法上的数据类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        OperationType operationType = autoFill.value();
        //获得被拦截方法的参数
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object entity = args[0];
        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long userId = BaseContext.getCurrentId();
        //根据当前不同数据的类型，通过反射进行属性赋值
        switch (operationType){
            case INSERT:
                try{
                    entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class).invoke(entity, now);
                    entity.getClass().getMethod(AutoFillConstant.SET_CREATE_USER, Long.class).invoke(entity, userId);
                    entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class).invoke(entity, now);
                    entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class).invoke(entity, userId);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case UPDATE:
                try{
                    entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class).invoke(entity, now);
                    entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class).invoke(entity, userId);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }




    }
}
