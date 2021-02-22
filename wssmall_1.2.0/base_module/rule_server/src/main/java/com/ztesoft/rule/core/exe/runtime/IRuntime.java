package com.ztesoft.rule.core.exe.runtime;

import com.ztesoft.rule.core.exe.IRuleConfigs;
import com.ztesoft.rule.core.exe.IRuleExecutorBuilder;


/**
 * 执行类型接口：
 * 1.定时执行,实现类为TimingRunner,执行
 * @author easonwu 
 * @creation Dec 16, 2013
 * 
 */
public interface IRuntime {
	public void run() ;
	public IRuleConfigs getRuleConfigs() ;
	public IRuleExecutorBuilder getRuleExecutorBuilder() ;
	public void setJitParam(JitParam jitParam);
}
