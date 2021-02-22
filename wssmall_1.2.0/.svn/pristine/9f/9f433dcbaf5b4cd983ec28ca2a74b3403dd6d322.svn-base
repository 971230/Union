package com.ztesoft.rule.core.ext;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.rule.core.module.cfg.BizPlan;


/**
 * 方案之再次过滤器
 * 
 * SQL    直接SQL模式加载
	JAVA   通过javabean模式加载
	取值对应于【rule_sql】和【rule_java】
	【注】：
	1.其中javabean需实现IPlanCtrl接口

	2.sql写法要求：select count(1) from tab_xx where f1=:v1 and f2=:v2 ...
	其中f1/f2等字段为目标表字段，目标表可以有多个；
	v1/v2的取值，可以是常量，也可以是变量，变量约定几个，
	当前账期等
	
 * @author easonwu 
 * @creation Dec 14, 2013
 * 
 */
public class DefPlanCtrl   extends BaseSupport   implements IPlanCtrl{
	
	@Override
	public boolean valid(BizPlan plan ) {
		return true ;
	}

	@Override
	public boolean valid(String sql, BizPlan plan) {
		if(StringUtils.isEmpty(sql))
			return true ;
		Map param = plan.toMap() ;
		int cnt = baseDaoSupport.queryForIntByMap(sql, param);
		return cnt > 0 ;
	}
	
}
