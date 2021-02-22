package com.ztesoft.net.auto.rule.i;

import java.util.List;

import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

public interface IAutoFact {

	/**
	 * 规则执行方法
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param params 执行规则成功得到的常量结果集
	 */
	public void exeAction(AutoFact fact,List<RuleConsts> consts)throws RuntimeException;
	
}
