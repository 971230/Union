package com.ztesoft.net.aspect;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.model.FunDealLog;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.eop.sdk.database.BaseSupport;


@Component
@Aspect
public class EccCheckApiAspect extends BaseSupport{
	
	
	// 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.ztesoft.check.fun.WorkFlowMatchCheck.validByCode(..))")
	public void aspect() {}
	
	
	
	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	@AfterReturning(pointcut="aspect()", returning="retVal")
	public void afterReturn(JoinPoint joinPoint, IdentifyResp retVal) {
		Object[] args = joinPoint.getArgs();		
		if(args[0] instanceof IdentifyReq){
			IdentifyReq req = (IdentifyReq) args[0];
			String log_id = req.getInf_log_id();
			this.updateInfLog(log_id, retVal);
		}
	}

	// 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		//日志id
		String log_id = "";//new SeqUtil().getNextSequence("ES_ECC_FUN_DEAL_LOG", "LOG_ID");
		if(args[0] instanceof IdentifyReq){
			IdentifyReq req = (IdentifyReq) args[0];
			req.setInf_log_id(log_id);
			this.insertInfLog(req);
		}
	}
	
	// 配置后置通知,使用在方法aspect()上注册的切入点
	@After("aspect()")
	public void after(JoinPoint joinPoint) {
		
	}
	
	// 配置抛出异常后通知,使用在方法aspect()上注册的切入点
	@AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint,Throwable ex) throws IllegalAccessException, InvocationTargetException {
		Object[] args = joinPoint.getArgs();		
		if(args[0] instanceof IdentifyReq){
			IdentifyReq req = (IdentifyReq) args[0];
			String log_id = req.getInf_log_id();
			IdentifyResp retVal = new IdentifyResp();
			retVal.setCode(EccConsts.IDENTI_9999);
			retVal.setMsg(ex.getMessage());
			this.updateInfLog(log_id, retVal);
		}
	}	
	
	private void insertInfLog(IdentifyReq req){
		try{
			FunDealLog log = new FunDealLog();
			log.setLog_id(req.getInf_log_id());
			log.setBiz_id(req.getBiz_id());
			log.setFun_id(req.getFun_id());
			log.setClazz(req.getClazz());
			log.setExe_time(req.getExe_time());
			log.setImpl(req.getImpl());
			log.setOrder_id(req.getOrder_id());
			log.setTache_code(req.getTache_code());
			log.setReq_time(new Timestamp(System.currentTimeMillis()));
			baseDaoSupport.insert("ES_ECC_FUN_DEAL_LOG", log);			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void updateInfLog(String log_id, IdentifyResp resp){
		try{
			String sql = " update ES_ECC_FUN_DEAL_LOG set resp_time=:resp_time , result_code=:result_code,result_msg=:result_msg where log_id=:log_id" ;
			Map map = new HashMap();
			map.put("log_id", log_id);
			map.put("resp_time", new Timestamp(System.currentTimeMillis()));
			map.put("result_code", resp.getCode());
			map.put("result_msg", resp.getMsg());
			baseDaoSupport.update(sql, map);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
