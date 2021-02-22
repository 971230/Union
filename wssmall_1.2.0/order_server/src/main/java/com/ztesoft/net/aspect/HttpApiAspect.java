package com.ztesoft.net.aspect;

import java.lang.reflect.InvocationTargetException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ztesoft.net.eop.sdk.database.BaseSupport;

/**
 * 
 * @author wui 订单注解
 * 要求：做到一个模块一个注解
 * 
 * 
 */
@Component
@Aspect
public class HttpApiAspect extends BaseSupport{

	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* zte.service.OrderServices.addOrder(..))")
	public void aspect() {}

	/*
	 * // * 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
//		IDataLogManager dataLogManager = SpringContextHolder.getBean("datasLogManager");
//		dataLogManager.insertDataBeforeLog(null);
	}

	// 配置后置通知,使用在方法aspect()上注册的切入点
	@After("aspect()")
	public void after(JoinPoint joinPoint) {
//		IDataLogManager dataLogManager = SpringContextHolder.getBean("datasLogManager");
//		dataLogManager.insertDataAfterLog(null);
	}

	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint) {
		//logger.info("@AfterReturning");
		
	}

	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint,Throwable ex) throws IllegalAccessException, InvocationTargetException {
		
	}

}
