package com.ztesoft.net.rule.fact;

import java.util.List;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

/**
 * 页面URL获取
 * @作者 MoChunrun
 * @创建日期 2014-9-30 
 * @版本 V 1.0
 */
@DroolsFact(name="环节界面匹配",code="orderPageFact")
public class OrderPageFact extends AutoFact {
	private OrderTreeBusiRequest orderTreeBusiRequest;
	//商品类型、订单环节、订单模式、订单来源，订单状态
	private String order_id;
	@DroolsFactField(name="商品类型", ele_type="checkbox", stype_code="DC_MODE_GOODS_TYPE")
	private String goods_type;
	@DroolsFactField(name="订单来源", ele_type="checkbox", stype_code="DC_MODE_SHOP_CODE")
	private String order_from;
	@DroolsFactField(name="订单环节", ele_type="checkbox", stype_code = "DC_ORDER_TACHE_NODE")
	private String tache_id;
	@DroolsFactField(name="订单模式", ele_type="checkbox", stype_code="DC_MODE_OPER_MODE")
	private String order_model;
	@DroolsFactField(name="订单类型", ele_type="checkbox", stype_code="DC_ORDER_NEW_TYPE")
	private String order_type;
	@DroolsFactField(name="订单状态", ele_type="checkbox", stype_code="DIC_ORDER_STATUS")
	private int status;
	@DroolsFactField(name="页面地址", ele_type="input")
	private String actionUrl;
	@DroolsFactField(name="是否走AOP开户", ele_type="checkbox", stype_code="DIC_OPEN_SYSTEM")
	private String is_aop;
	
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		/*if(consts!=null && consts.size()>0){
			for(RuleConsts cs:consts){
				if("actionUrl".equals(cs.getAttr_code())){
					actionUrl = cs.getConst_value();
				}
			}
		}*/
	}

	@Override
	public String getObj_id() {
		return order_id;
	}

	@Override
	public String getTrace_flow_id() {
		trace_flow_id = getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		return trace_flow_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getGoods_type() {
		this.goods_type =  getOrderTree().getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getGoods_type();
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getOrder_from() {
		this.order_from = getOrderTree().getOrderExtBusiRequest().getOrder_from();
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}


	public String getTache_id() {
		tache_id = getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		return tache_id;
	}

	public void setTache_id(String tache_id) {
		this.tache_id = tache_id;
	}

	public String getOrder_model() {
		order_model = getOrderTree().getOrderExtBusiRequest().getOrder_model();
		return order_model;
	}

	public void setOrder_model(String order_model) {
		this.order_model = order_model;
	}

	public String getOrder_type() {
		this.order_type = getOrderTree().getOrderBusiRequest().getOrder_type();
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public int getStatus() {
		status = getOrderTree().getOrderBusiRequest().getStatus();
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIs_aop() {
		is_aop = getOrderTree().getOrderExtBusiRequest().getIs_aop();
		return is_aop;
	}

	public void setIs_aop(String is_aop) {
		this.is_aop = is_aop;
	}
	@JSONField(deserialize=true,serialize=false)
	public OrderTreeBusiRequest getOrderTree(){
		if(orderTreeBusiRequest ==null)
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		return orderTreeBusiRequest;
	}
	public void removeOrderTree(){
		orderTreeBusiRequest=null;
	}

}
