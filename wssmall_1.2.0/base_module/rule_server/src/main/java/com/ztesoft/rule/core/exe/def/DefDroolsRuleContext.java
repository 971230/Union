package com.ztesoft.rule.core.exe.def;

import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.rule.core.exe.IRuleContext;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.module.cfg.RuleConfig;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.io.ResourceFactory;

import java.util.List;

public class DefDroolsRuleContext implements IRuleContext {
	
	private ConfigData configData ;
	private String planCode ;
	
	public DefDroolsRuleContext(){
		
	}

	@Override
	public Object getContext(ConfigData configData) {
		this.configData = configData ;
		this.planCode = configData.getBizPlan().getPlan_code() ;
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		if(StringUtils.isEmpty(planCode)){
			throw new IllegalArgumentException("ruleSet is empty.");
		}
		
		//决策表配置
		DecisionTableConfiguration dtableconfiguration = null;
		
		List<RuleConfig> ruleConfigs = configData.getRuleConfigs() ;
		
		for(RuleConfig rule : ruleConfigs){
			
			if(ResourceType.DTABLE.equals(rule.getResourceType())){
				if(dtableconfiguration == null ){
					dtableconfiguration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
		        	dtableconfiguration.setInputType( DecisionTableInputType.XLS );
				}	
				kbuilder.add( ResourceFactory.newByteArrayResource(rule.getRule_script().getBytes()),
						ResourceType.DTABLE,dtableconfiguration );
			}else{
				kbuilder.add(ResourceFactory.newByteArrayResource(rule.getRule_script().getBytes()), 
						rule.getResourceType());
			}
		}
		
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
	
	

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public ConfigData getConfigData() {
		return configData;
	}

	public void setConfigData(ConfigData configData) {
		this.configData = configData;
	}

}
