package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.MemberBuyRule;

/**
 * 会员购买规则
 * @作者 MoChunrun
 * @创建日期 2013-10-15 
 * @版本 V 1.0
 */
public interface IMemberBuyRuleMamager {

	/**
	 * 查询商品购买规则
	 * @作者 MoChunrun
	 * @创建日期 2013-10-15 
	 * @param goods_id
	 * @param lv_id
	 * @return
	 */
	public List<MemberBuyRule> qruRuleByGoodsLvId(String goods_id,String lv_id);
	
	/**
	 * 查询会员规则已购买商品数量
	 * @作者 MoChunrun
	 * @创建日期 2013-10-15 
	 * @param goods_id
	 * @param lv_id
	 * @return
	 */
	public int getMemberBuyCountByGoodsLvId(String member_id,String goods_id,String lv_id,int kind);
	
}
