package com.ztesoft.net.aspect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.DataLog;
import com.ztesoft.net.mall.core.service.IDataLogManager;
import commons.CommonTools;

/**
 * 
 * @author wui 订单注解
 * 要求：做到一个模块一个注解
 * 
 * 
 */
@Component
@Aspect
public class OrderAspect extends BaseSupport{

	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* zte.service.OrderServices.addOrder(..))")
	public void aspect() {}

	/*
	 * // * 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
		
		//logger.info("11111");
//		String httpReqJson = CommonTools.beanToJson(httpReqMap);
//		IDataLogManager dataLogManager = SpringContextHolder.getBean("datasLogManager");
//		dataLogManager.insertDataBeforeLog(null);
	}

	// 配置后置通知,使用在方法aspect()上注册的切入点
	@After("aspect()")
	public void after(JoinPoint joinPoint) {
//		IDataLogManager dataLogManager = SpringContextHolder.getBean("datasLogManager");
//		dataLogManager.insertDataAfterLog(null);
		
	}

	/**
	 * java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表；
	 Signature getSignature() ：获取连接点的方法签名对象；
	 java.lang.Object getTarget() ：获取连接点所在的目标对象； （通俗一点就是被代理的对象）
	 java.lang.Object getThis() ：获取代理对象本身；
	 * @param joinPoint
	 */
	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@SuppressWarnings("unchecked")
	@AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint) {
		
	}

	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint,Throwable ex) throws IllegalAccessException, InvocationTargetException {
		
		//后续改成异步执行，需要关注
		DataLog dataLog = new DataLog();
		dataLog.setCreate_time(Consts.SYSDATE);
		dataLog.setData_state("1");
		dataLog.setData_object_table_name("ES_ORDER");
		dataLog.setData_object_name("订单日志");
		dataLog.setData_log_id(System.currentTimeMillis() + "");
		dataLog.setData_in_params(CommonTools.beanToJson(joinPoint.getArgs()[0]));
		dataLog.setData_in_params_type(joinPoint.getArgs()[0].getClass().getName());
		dataLog.setData_out_params_type(joinPoint.getSignature().toString());
		// error切分
		String errorStr =ex.getMessage();
		List<String> errors = parseStr(errorStr);
		for (int i=0;i<errors.size() && i<5;i++){
			BeanUtils.setProperty(dataLog, "data_text"+(i+1), errors.get(i));
		}
		
		TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(dataLog){
			@Override
			public ZteResponse execute(ZteRequest zteRequest) {
				try{
					DataLog dataLog = (DataLog)zteRequest;
					IDataLogManager dataLogManager = SpringContextHolder.getBean("datasLogManager");
					dataLogManager.insertDataLog(dataLog);
				}catch(Exception e){
					e.printStackTrace();
				}
				return new ZteResponse();
			}
		});
		ThreadPoolFactory.singleExecute(taskThreadPool); //异步单线程执行
	}


	@SuppressWarnings("rawtypes")
	private List parseStr(String errorStr) {
		List<String> errorsList = new ArrayList<String>();
		parseCicleStr(errorsList, errorStr);
		return errorsList;
	}
	
	private void parseCicleStr(List<String> errorsList, String errorStr) {
		int splitPoint = 3500;
		while (!StringUtil.isEmpty(errorStr) && (errorStr.length() >splitPoint)) {
			String error_info = errorStr.substring(0, splitPoint).toString();
			errorsList.add(error_info);
			errorStr = errorStr.substring(splitPoint);
			parseCicleStr(errorsList, errorStr);
		}
		errorsList.add(errorStr);
	}
}
