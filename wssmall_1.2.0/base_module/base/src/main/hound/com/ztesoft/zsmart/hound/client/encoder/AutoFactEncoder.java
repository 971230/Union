package com.ztesoft.zsmart.hound.client.encoder;

import com.ztesoft.net.rule.fact.OrderPageFact;

import zte.net.ecsord.rule.comm.ExceptionFact;
import zte.net.ecsord.rule.mode.ModeFact;
import zte.net.ecsord.rule.orderexp.OrderExpFilterFact;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.rule.warehouse.WareHouseFact;
import zte.net.ecsord.rule.workflow.WorkFlowFact;

public class AutoFactEncoder extends AbstractHoundEncoder {

	// CommonFact
	// ExceptionFact
	// InfTplFact
	// LogisticsFact
	// ModeFact
	// OrderExpFilterFact
	// OrderExpSolutionFact
	// OrderPageFact
	// OrderSyFact
	// TacheFact
	// WareHouseFact
	// WorkFlowFact

	@Override
	public String encode(Object arg0) {
		// TODO Auto-generated method stub
		String objStr = "";

		// long begin = System.currentTimeMillis();
		if (arg0 instanceof ExceptionFact) {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule", "trace_flow_id");
		} else if (arg0 instanceof ModeFact) {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule", "trace_flow_id", "region", "goods_type",
					"shop_code", "shipping_type", "card_type", "net_type",
					"special_buss_type", "is_group_contract", "is_old",
					"is_society", "order_id", "order_type", "prod_cat_id",
					"is_physics", "orderTreeBusiRequest");
		} else if (arg0 instanceof OrderExpFilterFact) {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule", "orderTreeBusiRequest", "tache_code", "order_from",
					"goods_type", "product_type", "is_send_zb", "is_send_wms",
					"is_new_user", "is_physics", "is_open_account",
					"is_write_card", "is_easy_account", "is_shipping_quick",
					"net_type", "order_model", "order_type", "plat_type",
					"sending_type", "abnormal_type", "card_type", "pay_method",
					"is_society", "wm_isreservation_from", "prod_cat_id",
					"is_heyue", "supply_id", "syn_ord_zb", "need_shipping",
					"comments", "ship_name", "is_new_sys", "old_order_model",
					"is_zb_refund", "busi_type", "is_aop", "occupiedFlag",
					"customer_type", "goods_cat_id", "region",
					"operation_status", "bssOccupiedFlag", "write_card_status",
					"buyer_message");
		} else if (arg0 instanceof OrderPageFact) {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule", "trace_flow_id", "order_from", "goods_type",
					"tache_id", "order_model", "order_type");
		} else if (arg0 instanceof TacheFact) {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule", "trace_flow_id", "order_from", "goods_type",
					"tache_id", "order_model", "order_type", "is_send_zb",
					"is_send_wms", "product_type", "is_new_user", "is_physics",
					"is_open_account", "is_write_card", "is_easy_account",
					"is_shipping_quick", "net_type", "order_model",
					"order_type", "plat_type", "sending_type", "abnormal_type",
					"card_type", "pay_method", "is_society",
					"wm_isreservation_from", "prod_cat_id",
					"orderTreeBusiRequest", "is_heyue", "supply_id",
					"syn_ord_zb", "need_shipping", "comments", "ship_name",
					"is_new_sys", "old_order_model", "old_order_model",
					"busi_type", "is_aop", "occupiedFlag", "customer_type",
					"goods_cat_id", "region", "operation_status",
					"bssOccupiedFlag", "write_card_status", "buyer_message",
					"shipping_company", "stock_state", "produce_center");
		} else if (arg0 instanceof WareHouseFact) {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule", "is_match_warehouse", "is_matched");
		} else if (arg0 instanceof WorkFlowFact) {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule", "trace_flow_id", "need_open_act", "user_flag",
					"busi_type", "goods_type", "net_type",
					"wm_isreservation_from");
		} else {

			objStr = this.toJSONString(arg0, "hasExeRuleList",
					"hasExeRuleLogs", "responses", "execute", "rule_script",
					"rule");
		}

		// long begin1 = System.currentTimeMillis();
		//
		// logger.info("=======AutoFactEncoder=========begin1-begin:"
		// + (begin1 - begin));
		return objStr;
	}
}
