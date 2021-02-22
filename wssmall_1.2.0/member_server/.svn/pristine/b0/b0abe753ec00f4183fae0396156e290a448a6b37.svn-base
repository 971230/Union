package com.ztesoft.net.mall.core.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.MemberBuyRule;
import com.ztesoft.net.mall.core.service.IMemberBuyRuleMamager;
import com.ztesoft.net.sqls.SF;

public class MemberBuyRuleMamager extends BaseSupport implements
		IMemberBuyRuleMamager {

	@Override
	public List<MemberBuyRule> qruRuleByGoodsLvId(String goods_id, String lv_id) {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_RULE");
		return this.baseDaoSupport.queryForList(sql, MemberBuyRule.class, goods_id,lv_id);
	}

	@Override
	public int getMemberBuyCountByGoodsLvId(String member_id,String goods_id, String lv_id,
			int kind) {
		String sql = SF.memberSql("SERVICE_GET_MEMBER_ORDER_SUM");
		
		if(kind==Const.MEMBER_RULE_KIND_0){
			sql += " and trunc(o.create_time,'MM')=trunc(sysdate,'MM')";
		}else if(kind==Const.MEMBER_RULE_KIND_2){
			sql += " and trunc(o.create_time,'DD')=trunc(sysdate,'DD')";
		}else if(kind==Const.MEMBER_RULE_KIND_1){
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.add(Calendar.DAY_OF_WEEK, 2-calendar.get(Calendar.DAY_OF_WEEK));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date d1 = calendar.getTime();
			calendar.add(Calendar.DAY_OF_WEEK, 7);
			Date d2 = calendar.getTime();
			sql += " and o.create_time>? and o.create_time<?";
			return this.baseDaoSupport.queryForInt(sql, goods_id,lv_id,member_id,d1,d2);
		}
		return this.baseDaoSupport.queryForInt(sql, goods_id,lv_id,member_id);
	}

}
