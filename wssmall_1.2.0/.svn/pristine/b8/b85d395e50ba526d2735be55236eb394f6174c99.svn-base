package com.ztesoft.rule.core.module.fact;

import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.module.cfg.MidDataConfig;


/**
 * 待处理Fact结果集
 * @author easonwu 
 * @creation Dec 23, 2013
 * 
 */
public class ProcessFact {
	private List<DefFact> facts ;//待处理结果集
	private Map partner ;//参与者
	private MidDataConfig midDataConfig ;
	
	public ProcessFact(MidDataConfig midDataConfig ,List<DefFact> facts  , Map partner ){
		this.midDataConfig = midDataConfig ;
		this.partner = partner ;
		this.facts = facts ;
	}
	
	public ProcessFact(){
		
	}

	public MidDataConfig getMidDataConfig() {
		return midDataConfig;
	}

	public void setMidDataConfig(MidDataConfig midDataConfig) {
		this.midDataConfig = midDataConfig;
	}

	public Map getPartner() {
		return partner;
	}
	public void setPartner(Map partner) {
		this.partner = partner;
	}
	public List<DefFact> getFacts() {
		return facts;
	}
	public void setFacts(List<DefFact> facts) {
		this.facts = facts;
	}
	
	
}
