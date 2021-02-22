package com.ztesoft.net.mall.core.service.impl;


import java.util.List;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.RuleTypeRela;
import com.ztesoft.net.mall.core.service.IRuleTypeRelaManager;

/***
 * 业务类型规则关联
 * @author huang.xiaoming
 *
 */
public class RuleTypeRelaManager extends BaseSupport<RuleTypeRela> implements IRuleTypeRelaManager {
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	public static int NAMESPACE = Const.CACHE_SPACE_ORDERSTD;
	static int time = Const.CACHE_TIME_ORDERSTD;//缺省缓存5天,memcache最大有效期是30天
	
	@Override
	public RuleTypeRela getRuleTypeRela(String busiType) {
		String preKey = busiType + "_ES_BUSINESS_TYPE_RULE_RELA";
		String sql = "SELECT busi_type_rule_rela_id,busi_type_id,mode_match_rule_id,is_pass_aop_rule,workflow_match_rule_id,order_audit_rule_id"
				+ " FROM es_business_type_rule_rela where busi_type_id=?";
		RuleTypeRela ruleTypeRela = (RuleTypeRela)cache.get(NAMESPACE,preKey);
		if(null != ruleTypeRela) {
			return ruleTypeRela;
		} else {
			List<RuleTypeRela> list = baseDaoSupport.queryForList(sql, RuleTypeRela.class, busiType);
			if(null != list && list.size()>0) {
				ruleTypeRela = list.get(0);
				cache.set(NAMESPACE, preKey, list.get(0), time);
			}
			
		}
		return ruleTypeRela;
	}

	@Override
	public void refreshCache() {
		String sql = "SELECT busi_type_rule_rela_id,busi_type_id,mode_match_rule_id,is_pass_aop_rule,workflow_match_rule_id,order_audit_rule_id"
				+ " FROM es_business_type_rule_rela ";
		List<RuleTypeRela> list = baseDaoSupport.queryForList(sql, RuleTypeRela.class);
		for(RuleTypeRela ruleTypeRela : list) {
			String preKey = ruleTypeRela.getBusi_type_id() + "_ES_BUSINESS_TYPE_RULE_RELA";
			cache.set(NAMESPACE, preKey, ruleTypeRela, time);
		}
		
	}
	
}
