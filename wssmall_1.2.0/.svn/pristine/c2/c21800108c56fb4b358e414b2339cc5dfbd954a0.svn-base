package com.ztesoft.rule.core.util;

import java.util.Collection;
import java.util.Map;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.exe.IRuleConfigs;
import com.ztesoft.rule.core.exe.IRuleContext;
import com.ztesoft.rule.core.exe.IRuleExecutor;
import com.ztesoft.rule.core.exe.IRuleExecutorBuilder;
import com.ztesoft.rule.core.exe.IRuleFactDatas;
import com.ztesoft.rule.core.exe.IRuleFacts;
import com.ztesoft.rule.core.exe.IRulePartners;
import com.ztesoft.rule.core.ext.IFactDataLoader;
import com.ztesoft.rule.core.ext.IFactLoader;
import com.ztesoft.rule.core.ext.IPlanCtrl;
import com.ztesoft.rule.core.ext.IRuleResultProssor;
import com.ztesoft.rule.core.module.filter.IFactFilter;


/**
 * 当前上下文bean创建工厂
 * @author easonwu 
 * @creation Dec 18, 2013
 * 
 */
public class LocalBeanFactory {
	
	public static IRuleConfigs createRuleConfigs(Class<? extends IRuleConfigs> clazz){
		return getBean(clazz) ;
	}
	
	public static IRuleContext createRuleContext(Class<? extends IRuleContext> clazz){
		return getBean(clazz) ;
	}
	
	public static IRuleExecutor createRuleExecutor(Class<? extends IRuleExecutor> clazz){
		return getBean(clazz) ;
	}
	
	public static IRuleExecutorBuilder createRuleExecutorBuilder(Class<? extends IRuleExecutorBuilder> clazz){
		return getBean(clazz) ;
	}
	
	public static IRuleFactDatas createRuleFactDatas(Class<? extends IRuleFactDatas> clazz){
		return getBean(clazz) ;
	}
	
	
	public static IRuleFacts createRuleFacts(Class<? extends IRuleFacts> clazz){
		return getBean(clazz) ;
	}
	
	public static IRulePartners createRulePartners(Class<? extends IRulePartners> clazz){
		return getBean(clazz) ;
	}
	
	
	//======================================完全动态实现=============================================//

	public static IPlanCtrl createPlanCtrl(String clazzName){
		return (IPlanCtrl)getBean(ClazzUtil.getClassByCache(clazzName)) ;
	}
	
	
	public static IPlanCtrl createPlanCtrl(Class<? extends IPlanCtrl> clazz){
		return getBean(clazz) ;
	}
	
	

	public static IFactDataLoader createFactDataLoader(String clazzName){
		return (IFactDataLoader)getBean(ClazzUtil.getClassByCache(clazzName)) ;
	}
	
	public static IFactDataLoader createFactDataLoader(Class<? extends IFactDataLoader> clazz){
		return getBean(clazz) ;
	}


	public static IFactLoader createFactLoader(String className ){
		return (IFactLoader) getBean(ClazzUtil.getClassByCache(className)) ;
	}
	
	public static IFactLoader createFactLoader(Class<? extends IFactLoader> clazz){
		return getBean(clazz) ;
	}
	


	public static IRuleResultProssor createRuleResultProssor(String clazzName){
		return (IRuleResultProssor)getBean(ClazzUtil.getClassByCache(clazzName)) ;
	}
	
	public static IRuleResultProssor createRuleResultProssor(Class<? extends IRuleResultProssor> clazz){
		return getBean(clazz) ;
	}
	
	public static IFactFilter createFactFilter(String className){
		return (IFactFilter)getBean(ClazzUtil.getClassByCache(className)) ;
	}
	
	public static IFactFilter createFactFilter(Class<? extends IFactFilter> clazz){
		return getBean(clazz) ;
	}
	
	
	//=======================公用==================================================//
	
	public static <T> T getBean(Class<T> clazz) {
		Map obj =(Map) SpringContextHolder.getBean(clazz );
		Collection cs = obj.values() ;
		return (T)cs.iterator().next();
	}
}
