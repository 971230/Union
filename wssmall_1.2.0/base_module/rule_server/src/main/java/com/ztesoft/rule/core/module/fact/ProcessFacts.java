package com.ztesoft.rule.core.module.fact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.module.cfg.MidDataConfig;


/**
 * 待处理Fact结果集
 * @author easonwu 
 * @creation Dec 23, 2013
 * 
 */
public class ProcessFacts {
	private List<ProcessFact> processFacts ;

	
	public void addProcessFact(ProcessFact fact){
		getProcessFacts().add(fact) ;
	}

	public void addProcessFact(MidDataConfig midDataConfig ,List<DefFact> facts  , Map partner ){
		getProcessFacts().add(new ProcessFact( midDataConfig ,facts  ,  partner )) ;
	}
	
	
	
	public List<ProcessFact> getProcessFacts() {
		if(this.processFacts == null ){
			processFacts = new ArrayList<ProcessFact>() ;
		}	
		return processFacts;
	}

	public void setProcessFacts(List<ProcessFact> processFacts) {
		this.processFacts = processFacts;
	}
	
	
}
