package com.ztesoft.rule.core.exe;

import com.ztesoft.rule.core.module.fact.ProcessFacts;


/**
 * 方案执行结果处理
 * @author easonwu 
 * @creation Dec 19, 2013
 * 
 */
public interface IRuleResultHandler {
	public void doExec(ProcessFacts pf) ;
}
