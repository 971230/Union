package com.ztesoft.rule.core.exe.def;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.exe.IRuleConfigs;
import com.ztesoft.rule.core.exe.IRuleContext;
import com.ztesoft.rule.core.exe.IRuleExecutor;
import com.ztesoft.rule.core.exe.IRuleExecutorBuilder;
import com.ztesoft.rule.core.exe.IRuleFactDatas;
import com.ztesoft.rule.core.exe.IRuleFacts;
import com.ztesoft.rule.core.exe.IRulePartners;
import com.ztesoft.rule.core.exe.IRuleResultHandler;
import com.ztesoft.rule.core.exe.thread.IPartnerExecutorService;
import com.ztesoft.rule.core.module.cfg.ConfigData;

public class DefRuleExecutorBuilder implements IRuleExecutorBuilder{
	private static Logger logger = Logger.getLogger(DefRuleExecutorBuilder.class);
	private IRuleDBAccess ruleDBAccess ;
	
	private IRuleExecutor executor ;

	private IRuleFacts ruleFacts ;
	private IRuleContext ruleContext ;
	private IRulePartners rulePartners ;
	private IRuleFactDatas ruleFactDatas ;
	private IRuleConfigs ruleConfigs ;
	private IRuleResultHandler ruleResultHandler ;
	private IPartnerExecutorService partnerExecutorService ;
	
	private ConfigData configData ;
	
	
	public DefRuleExecutorBuilder(){
	}
	

	@Override
	public IRuleExecutor build() {
//		executor = new DefDroolsRuleExecutor() ;
		//默认实现,【扩展点】
		executor = SpringContextHolder.getBean("defDroolsRuleExecutor") ;
		
//		makeRuleConfigs( ) ;
		makeRulePartners( );
		makeRuleFacts() ;
		makeRuleFactDatas() ;
		makeRuleContext() ;
		makeRuleResultHandler() ;
		makePartnerExecutorService() ;
		
		executor.setRuleConfigs(ruleConfigs) ;
		executor.setRulePartners(rulePartners) ;
		executor.setRuleFacts(ruleFacts) ;
		executor.setRuleFactDatas(ruleFactDatas) ;
		executor.setRuleContext(ruleContext) ;
		executor.setRuleResultHandler(ruleResultHandler) ;
		executor.setPartnerExecutorService(partnerExecutorService) ;
		
		return executor ;
	}

	

	@Override
	public void makeRuleResultHandler() {
		//默认实现,【扩展点】
		ruleResultHandler = SpringContextHolder.getBean("defRuleResultHandler") ;
	}
	
	
	@Override
	public void makeRuleContext() {
		//默认实现,【扩展点】
		ruleContext = SpringContextHolder.getBean("defDroolsRuleContext") ;
	}

	@Override
	public void makeRulePartners() {
		//默认实现,【扩展点】
		rulePartners = SpringContextHolder.getBean("defRulePartners") ;
	}

	@Override
	public void setRuleConfigs(IRuleConfigs ruleConfigs) {
//		ruleConfigs = new DefRuleConfigs() ;
		logger.info(new Date()+"-2-"+this+"--"+configData) ;

		this.ruleConfigs = ruleConfigs ;
		
		//添加当前配置数据
		if(configData != null)
			this.ruleConfigs.setConfigData(configData) ;
	}
	

	@Override
	public void makeRuleFacts() {
		//默认实现,【扩展点】
		ruleFacts = SpringContextHolder.getBean("defRuleFacts") ;
	}

	@Override
	public void makeRuleFactDatas() {
		//默认实现,【扩展点】
		ruleFactDatas = SpringContextHolder.getBean("defRuleFactDatas") ;
	}



	@Override
	public void makePartnerExecutorService() {
		//默认实现,【扩展点】
		partnerExecutorService = SpringContextHolder.getBean("defPartnerExecutorService") ;
	}


	@Override
	public void setConfigData(ConfigData configData){
		logger.info(new Date()+"-1-"+this+"--"+configData) ;
		this.configData = configData ;
		//添加当前配置数据
		if(ruleConfigs != null)
			this.ruleConfigs.setConfigData(configData) ;
	}
	
	
	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}

	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}

	public IRuleContext getRuleContext() {
		return ruleContext;
	}

	public void setRuleContext(IRuleContext ruleContext) {
		this.ruleContext = ruleContext;
	}


	public IRuleResultHandler getRuleResultHandler() {
		return ruleResultHandler;
	}


	public void setRuleResultHandler(IRuleResultHandler ruleResultHandler) {
		this.ruleResultHandler = ruleResultHandler;
	}



}
