package com.ztesoft.net.rule.order;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.rule.core.ext.DefFactLoader;
import com.ztesoft.rule.core.module.cfg.BizPlan;

/**
 * 中间数据加载类
 * @作者 MoChunrun
 * @创建日期 2013-12-19 
 * @版本 V 1.0
 */
public class OrderTimmerFactLoader extends DefFactLoader<OrderFact> {

	@Override
	public List<OrderFact> loadFacts(Class clazz,
			Map<String, String> partner, BizPlan plan) {
		//String sql = "select o.userid staff_no,o.lan_id lan_code,oi.goods_id,sum(oi.ship_num*oi.coupon_price) total_money,sum(oi.ship_num) total_num from es_order o,es_order_items oi "+
		//		" where o.order_id=oi.order_id and o.create_time>=trunc(sysdate-20,'MM') and o.create_time<trunc(sysdate,'MM') and o.pay_status in(1,3,4,5,6,7)"+
		//		" group by o.userid,o.lan_id,oi.goods_id";
		//String sql = "select g.goods_type,g.name as goods_name,g.staff_no staff_no,o.lan_id lan_code,oi.goods_id,sum(oi.ship_num*oi.coupon_price) goods_money,sum(oi.ship_num) goods_num from es_order o,es_order_items oi,es_goods g "+
		//		" where oi.goods_id=g.goods_id and o.order_id=oi.order_id and oi.ship_num>0 and o.pay_status in(1,3,4,5,6,7) and o.create_time>="+DBTUtil.truncBeforeDayMonthFormat(200)+" and o.create_time<"+DBTUtil.month1stDate()+" and o.source_from=? group by g.goods_type,g.staff_no,o.lan_id,oi.goods_id,g.name";
		//and o.create_time>=trunc(sysdate-20,'MM') and o.create_time<trunc(sysdate,'MM')
		
		String sql = "select g.goods_type,g.name as goods_name,g.staff_no staff_no,o.lan_id lan_code,oi.goods_id,sum(oi.num*oi.coupon_price) goods_money,sum(oi.num) goods_num from es_order o,es_order_items oi,es_goods g "+
				" where oi.goods_id=g.goods_id and o.order_id=oi.order_id and rownum<2 and o.source_from=? group by g.goods_type,g.staff_no,o.lan_id,oi.goods_id,g.name";
		
		
		return this.baseDaoSupport.queryForList(sql, OrderFact.class,ManagerUtils.getSourceFrom());
	}

}
