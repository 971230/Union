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
public class SpreadOrderTimmerFactLoader extends DefFactLoader<SpreadOrderFact> {

	@Override
	public List<SpreadOrderFact> loadFacts(Class clazz,
			Map<String, String> partner, BizPlan plan) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select b.goods_id  goods_id,");
		sql.append(" b.price     goods_price,");
		sql.append(" b.store     goods_num,");
		sql.append(" a.order_id  order_id,");
		sql.append(" a.spread_id spread_id");
		sql.append(" from es_order a, es_goods b, es_order_items c");
		sql.append(" where a.order_id = c.order_id");
		sql.append(" and a.goods_id = b.goods_id");
		sql.append(" and a.source_from = '"+ManagerUtils.getSourceFrom()+"'");
		sql.append(" and b.source_from = '"+ManagerUtils.getSourceFrom()+"'");
		sql.append(" and c.source_from = '"+ManagerUtils.getSourceFrom()+"'");
		sql.append(" and a.spread_id is not null");
		sql.append(" and a.order_id not in (select rel_order_id from es_commission_detail)");
		
		return this.baseDaoSupport.queryForList(sql.toString(), SpreadOrderFact.class);
	}

}
