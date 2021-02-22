package com.ztesoft.rule.core.module.cfg;

import java.util.HashMap;
import java.util.Map;


/**
 * 重算信息
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
public class RetryInfo {
//	select a.retry_id,a.rel_type,a.entity_type , a.entity_id,a.plan_id ,a.exec_cycle from es_rule_retry a where a.status_cd='00A'
	
	private String retry_id ;
	private String rel_type ;
	private String entity_type ;
	private String entity_id ;
	private String plan_id ;
	private String exec_cycle ;
	
	public Map toMap(){
		Map map = new HashMap() ;
		map.put("retry_id", retry_id) ;
		map.put("rel_type", rel_type) ;
		map.put("entity_type", entity_type) ;
		map.put("entity_id", entity_id) ;
		map.put("plan_id", plan_id) ;
		map.put("exec_cycle", exec_cycle) ;
		
		return map ;
	}
	
	
	public RetryInfo(){
		
	}
	
	public String getRetry_id() {
		return retry_id;
	}
	public void setRetry_id(String retry_id) {
		this.retry_id = retry_id;
	}
	public String getRel_type() {
		return rel_type;
	}
	public void setRel_type(String rel_type) {
		this.rel_type = rel_type;
	}
	public String getEntity_type() {
		return entity_type;
	}
	public void setEntity_type(String entity_type) {
		this.entity_type = entity_type;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getExec_cycle() {
		return exec_cycle;
	}
	public void setExec_cycle(String exec_cycle) {
		this.exec_cycle = exec_cycle;
	}
	
	
}
