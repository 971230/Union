package com.ztesoft.rule.core.exe;

import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.module.cfg.BizPlan;


/**
 * 规则参与人
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public interface IRulePartners {
	public int partnersSize(BizPlan plan);
	public int partnersSize(BizPlan plan , int mold) ;
	
	public List<Map> loadPartners(BizPlan plan);
	
	public List<Map> loadPartners(BizPlan plan ,int pi , int ps );
	public List<Map> loadPartners(BizPlan plan, int mold ,int pi , int ps );
	
	public int getPlanEntityCount(String plan_id);
}
