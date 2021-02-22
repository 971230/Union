package com.ztesoft.rule.core.ext;

import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.module.cfg.BizPlan;

/**
 * 参与规则计算的事实(Fact)[或称作业务数据]加载器
 * @author easonwu 2013
 *
 * @param <T>
 */
public interface IFactLoader<T> {
	List<T> loadFacts(String sql ,Class clazz , Map<String, String> partner, BizPlan plan) ;
	
	List<T> loadFacts(Class clazz ,Map<String,String> partner , BizPlan plan);
}
