package com.ztesoft.net.rule.order;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.rule.core.ext.DefFactLoader;
import com.ztesoft.rule.core.module.cfg.BizPlan;

public class OrderRealTimeFactLoader extends DefFactLoader<OrderFact> {

	@Override
	public List<OrderFact> loadFacts(Class clazz,
			Map<String, String> partner, BizPlan plan) {
		String order_id = partner.get("order_id");
		String sql = "select g.goods_type,g.name as goods_name,o.order_id,g.staff_no staff_no,o.lan_id lan_code,oi.goods_id,sum(oi.ship_num*oi.coupon_price) goods_money,sum(oi.ship_num) goods_num from es_order o,es_order_items oi,es_goods g "+
				" where oi.goods_id=g.goods_id and o.order_id=oi.order_id and o.pay_status in(1,3,4,5,6,7) and o.order_id=? and o.source_from=? group by g.goods_type,o.order_id,g.staff_no,o.lan_id,oi.goods_id,g.name";
		return this.baseDaoSupport.queryForList(sql, OrderFact.class, order_id,ManagerUtils.getSourceFrom());
	}
	
}
