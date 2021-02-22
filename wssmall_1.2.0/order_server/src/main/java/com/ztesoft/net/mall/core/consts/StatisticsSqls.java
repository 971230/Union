package com.ztesoft.net.mall.core.consts;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;

/**
 *统计报表sql语句
 * 
 * @author wui
 */
public class StatisticsSqls {

	 	//查询来源淘宝订单,统计月受理量
		public final static String ACCEPT_MONTH_SQL="select (select * from es_goods_type where type_code = t.type_code) type_name," +
				" sum(1),sum("+DBTUtil.decode("accept_status", "4", "1", "0")+") from es_order t where source_from = ? and userid =? and lan_id = ? and "+DBTUtil.to_char("create_date", 6)+"=? group by type_code ";
		//"+DBTUtil.decode("accept_status", "4", "1", "0")+"  decode(accept_status,'4','1','0')
		
		
		//查询来源淘宝订单 订单统计报表
		public final static String ORDER_ACCEPT_MONTH_SQL="select (select * from es_goods_type where type_code = t.type_code) type_name," +
				//"+DBTUtil.decode("status", OrderStatus.ORDER_CANCELLATION+"", "1", "0")+"  decode(accept_status,'4','1','0')
				"sum("+DBTUtil.decode("status", OrderStatus.ORDER_PAY+"", "1", "0")+") " + //待受理
				"sum("+DBTUtil.decode("status", OrderStatus.ORDER_ACCEPT+"", "1", "0")+") " + //待发货
				"sum("+DBTUtil.decode("status", OrderStatus.ORDER_SHIP+"", "1", "0")+" ) " +//待确认收货
				"sum("+DBTUtil.decode("status", OrderStatus.ORDER_COMPLETE+"", "1", "0")+") " + //已完结
				"sum("+DBTUtil.decode("status", OrderStatus.ORDER_CANCELLATION+"", "1", "0")+") " +//已作废
				"sum(1) " +//订单总数
				"sum((select payed_money from es_payment_ctf ctf where ctf.order_id = t.order_id and ctf.status = '"+OrderStatus.PAY_STATUS_1+"')) " + //订单总金额
				
				"from es_order t where source_from = ? and userid =? and lan_id = ?  and "+DBTUtil.to_char("create_date", 6)+"=? and order_type = ?  group by type_code ";
		
		
		//销售统计报表 ,统计商品销售数量,销售金额
		public final static String GOODS_SALES_MONTH_SQL="select (select * from es_goods_type where type_code = t.type_code) type_name,(select name from es_goods g where g.goods_id = t.goods_id) goods_name," +
			////"+DBTUtil.decode("status", OrderStatus.ORDER_ACCEPT+"", "1", "0")+"
			"sum("+DBTUtil.decode("status", OrderStatus.ORDER_ACCEPT+"", "1", "0")+" accept_counts, " +
			"sum((select payed_money from es_payment_ctf ctf where ctf.order_id = t.order_id and ctf.status = '"+OrderStatus.PAY_STATUS_1+"')) payed_amount " +
			" from es_order t where  source_from = ? and userid =? and lan_id = ?  and "+DBTUtil.to_char("create_date", 6)+"=?  group by t.goods_id,t.type_code";
	
		
		//业务收入 ,统计商品销售数量,销售金额
		public final static String ACCEPT_SALES_MONTH_SQL="select (select * from es_goods_type where type_code = t.type_code) type_name,(select name from es_goods g where g.goods_id = t.goods_id) goods_name," +
				//"+DBTUtil.decode("status", OrderStatus.ORDER_ACCEPT+"", "1", "0")+"
			"sum("+DBTUtil.decode("status", OrderStatus.ORDER_ACCEPT+"", "1", "0")+") accept_counts, " +
			"sum((select payed_money from es_payment_ctf ctf where ctf.order_id = t.order_id and ctf.status = '"+OrderStatus.PAY_STATUS_1+"')) payed_amount " +
			" from es_order t where  source_from = ? and userid =? and lan_id = ?  and "+DBTUtil.to_char("create_date", 6)+"=?  group by t.goods_id,t.type_code";
	
		
		
}
