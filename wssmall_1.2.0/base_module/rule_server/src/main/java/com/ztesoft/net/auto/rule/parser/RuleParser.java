package com.ztesoft.net.auto.rule.parser;

import java.util.List;

import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.mall.core.model.RuleCond;

public class RuleParser {
	
	//>、>=、<、<=、==、!=、contains、not contains、memberof、not memberof、matches、not matches、in
	//EQ NE GE GT LT LE 等于 不等 大于等于 大于 小于 小于等于
	public static final String EQ = "==";
	public static final String NE = "!=";
	public static final String GE = ">=";
	public static final String GT = ">";
	public static final String LT = "<";
	public static final String LE = "<=";
	public static final String CONTAINS = "contains";
	public static final String NOT_CONTAINS = "not contains";
	public static final String MEMBEROF = "memberof";
	public static final String NOT_MEMBEROF = "not memberof";
	public static final String MATCHES = "matches";
	public static final String NOT_MATCHES = "not matches";
	public static final String IN = "in";
	
	/**
	 * 比较两个值
	 * @作者 MoChunrun
	 * @创建日期 2014-12-5 
	 * @param rond
	 * @param srcValue
	 * @param targetValue
	 * @return
	 */
	public static boolean matchValue(String rond,String srcValue,String targetValue){
		
		return true;
	}

	/**
	 * 匹配规则条件
	 * select rc.*,ro.obj_code,ro.clazz,roa.attr_code from es_rule_cond rc,es_rule_obj ro,es_rule_obj_attr roa 
		where rc.obj_id=ro.obj_id and rc.attr_id=roa.attr_id and rc.source_from=ro.source_from and ro.source_from=roa.source_from
		and rc.rule_id='201410143984000004' order by rc.attr_index asc
	 * @作者 MoChunrun
	 * @创建日期 2014-12-5 
	 * @param conds
	 * @param fact
	 * @return
	 */
	public boolean matchCond(List<RuleCond> conds,AutoFact fact){
		
		return false;
	}
	
}
