package zte.net.ecsord.rule.tache;

import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

@DroolsFact(name="流程环节",code="tacheFact")
public class TacheFact extends AutoFact {
	
	private OrderTreeBusiRequest orderTreeBusiRequest;
	private String order_id;
	
	@DroolsFactField(name="环节编码", ele_type="checkbox", stype_code="DC_ORDER_TACHE_NODE")
	private String tache_code;
	
	@DroolsFactField(name="订单来源", ele_type="checkbox", stype_code="DC_MODE_SHOP_CODE")
	private String order_from;
	
	@DroolsFactField(name="是否总部交互", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_send_zb;
	
	@DroolsFactField(name="是否WMS交互", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_send_wms;
	
	@DroolsFactField(name="商品类型", ele_type="checkbox", stype_code="DC_MODE_GOODS_TYPE")
	private String goods_type;
	
	@DroolsFactField(name="货品类型", ele_type="checkbox", stype_code="DC_PRODUCT_TYPE")
	private String product_type;
	
	@DroolsFactField(name="是否新用户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_new_user;
	
	@DroolsFactField(name="是否实物单", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_physics;
	
	@DroolsFactField(name="是否需要开户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_open_account;
	
	@DroolsFactField(name="是否需要写卡", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_write_card;
	
	@DroolsFactField(name="是否支持一键开户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_easy_account;
	
	@DroolsFactField(name="是否闪电送", ele_type="radio", stype_code="DIC_SHIPPING_QUICK")
	private String is_shipping_quick;
	
	@DroolsFactField(name="网别", ele_type="checkbox", stype_code="DC_MODE_NET_TYPE")
	private String net_type;
	
	@DroolsFactField(name="订单类型", ele_type="checkbox", stype_code="DC_ORDER_NEW_TYPE")
	private String order_type;
	
	@DroolsFactField(name="生产模式", ele_type="checkbox", stype_code="DC_MODE_OPER_MODE")
	private String order_model;
	
	@DroolsFactField(name="平台类型", ele_type="checkbox", stype_code="")
	private String plat_type;
	
	@DroolsFactField(name="配送方式", ele_type="checkbox", stype_code="DC_MODE_SHIP_TYPE")
	private String sending_type;
	
	@DroolsFactField(name="是否需要发货", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String need_shipping;
	
	@DroolsFactField(name="异常类型", ele_type="checkbox", stype_code="ORDER_ABNORMAL_TYPE")
	private String abnormal_type;
	
	@DroolsFactField(name="号卡类型", ele_type="checkbox", stype_code="DC_MODE_CARD_TYPE")
	private String card_type;
	
	@DroolsFactField(name="支付方式", ele_type="checkbox", stype_code="DIC_PAY_METHOD")
	private String pay_method;
	
	@DroolsFactField(name="社会机定制机", ele_type="radio", stype_code="DIC_CUSTOMIZATION")
	private String is_society;
	
	@DroolsFactField(name="是否预约单", ele_type="radio", stype_code="DIC999_BESPOKE_FLAG")
	private String wm_isreservation_from;
	
	@DroolsFactField(name="参与活动", ele_type="checkbox", stype_code="DC_ACTIVITY_TYPE")
	private String prod_cat_id;
	
	@DroolsFactField(name="是否4G合约机", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_heyue;
	

	@DroolsFactField(name="供应商", ele_type="checkbox", stype_code="DIC_SUPPLIER_ID")
	private String supply_id;
	

	@DroolsFactField(name="订单是否已送总部", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String syn_ord_zb;
	
	@DroolsFactField(name="备注", ele_type="input", stype_code="")
	private String comments;
	
	@DroolsFactField(name="收获人姓名", ele_type="input", stype_code="")
	private String ship_name;
	
	@DroolsFactField(name="是否新系统处理", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_new_sys;
	
	@DroolsFactField(name="老生产模式", ele_type="checkbox", stype_code="DC_MODE_OPER_MODE")
	private String old_order_model;
	

	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return order_id;
	}

	@Override
	public String getTrace_flow_id() {
		OrderTreeBusiRequest orderTree =getOrderTree();
		trace_flow_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		return trace_flow_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		//orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		this.order_id = order_id;
	}

	public String getTache_code() {
		this.tache_code = getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getOrder_from() {
		this.order_from = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.ORDER_FROM);
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getIs_send_zb() {
		is_send_zb = getOrderTree().getOrderExtBusiRequest().getSend_zb();
		return is_send_zb;
	}

	public void setIs_send_zb(String is_send_zb) {
		this.is_send_zb = is_send_zb;
	}

	public String getIs_send_wms() {
		is_send_wms = getOrderTree().getOrderExtBusiRequest().getIs_send_wms();
		return is_send_wms;
	}

	public void setIs_send_wms(String is_send_wms) {
		this.is_send_wms = is_send_wms;
	}

	public String getGoods_type() {
		this.goods_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.GOODS_TYPE);
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getProduct_type() {
		this.product_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PRODUCT_TYPE);
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getIs_new_user() {
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_OLD);
		if(EcsOrderConsts.IS_OLD_1.equals(is_old)){
			is_new_user = EcsOrderConsts.NO_DEFAULT_VALUE;
		}else{
			is_new_user = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_new_user;
	}

	public void setIs_new_user(String is_new_user) {
		this.is_new_user = is_new_user;
	}

	public String getIs_physics() {
		is_physics = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_PHISICS);
		return is_physics;
	}

	public void setIs_physics(String is_physics) {
		this.is_physics = is_physics;
	}

	public String getIs_open_account() {
		is_open_account = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_ACCOUNT);
		return is_open_account;
	}

	public void setIs_open_account(String is_open_account) {
		this.is_open_account = is_open_account;
	}

	public String getIs_write_card() {
		is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_WRITE_CARD);
		return is_write_card;
	}

	public void setIs_write_card(String is_write_card) {
		this.is_write_card = is_write_card;
	}

	public String getIs_easy_account() {
		is_easy_account = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_EASY_ACCOUNT);
		return is_easy_account;
	}

	public void setIs_easy_account(String is_easy_account) {
		this.is_easy_account = is_easy_account;
	}

	public String getIs_shipping_quick() {
		OrderExtBusiRequest orderExtBusiReq = getOrderTree().getOrderExtBusiRequest();
		this.is_shipping_quick = orderExtBusiReq.getShipping_quick();
		return is_shipping_quick;
	}

	public void setIs_shipping_quick(String is_shipping_quick) {
		this.is_shipping_quick = is_shipping_quick;
	}

	public String getNet_type() {
		this.net_type = CommonDataFactory.getInstance().
				getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		return net_type;
	}

	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}

	public String getOrder_model() {
		this.order_model = getOrderTree().getOrderExtBusiRequest().getOrder_model();
		return order_model;
	}

	public void setOrder_model(String order_model) {
		this.order_model = order_model;
	}

	public String getOrder_type() {
		this.order_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.ORDER_TYPE);
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getPlat_type() {
		this.plat_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PLAT_TYPE);
		return plat_type;
	}

	public void setPlat_type(String plat_type) {
		this.plat_type = plat_type;
	}

	public String getSending_type() {
		this.sending_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SENDING_TYPE);
		return sending_type;
	}

	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
	}

	public String getAbnormal_type() {
		this.abnormal_type=getOrderTree().getOrderExtBusiRequest().getAbnormal_type();
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getCard_type() {
		this.card_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SIM_TYPE);
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	public String getPay_method() {
		pay_method = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PAY_METHOD);
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getIs_society() {
		String is_customized = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_CUSTOMIZED);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_customized)){
			is_society = EcsOrderConsts.NO_DEFAULT_VALUE;
		}
		else if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_customized)){
			is_society = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_society;
	}

	public void setIs_society(String is_society) {
		this.is_society = is_society;
	}

	public String getWm_isreservation_from() {
		wm_isreservation_from = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.WM_ISRESERVATION_FROM);
		return wm_isreservation_from;
	}

	public void setWm_isreservation_from(String wm_isreservation_from) {
		this.wm_isreservation_from = wm_isreservation_from;
	}

	public String getProd_cat_id() {
		prod_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PROD_CAT_ID);
		return prod_cat_id;
	}

	public void setProd_cat_id(String prod_cat_id) {
		this.prod_cat_id = prod_cat_id;
	}

	@JSONField(deserialize=true,serialize=false)
	public OrderTreeBusiRequest getOrderTree(){
		if(orderTreeBusiRequest ==null)
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		return orderTreeBusiRequest;
	}
	public void toStringN(){
		logger.info("================================================================>");
		logger.info("zte.net.ecsord.rule.tache.TacheFact日志信息打印："+"订单编号："+this.getOrder_id()+"环节编码:"+this.getTache_code()+":"+"订单来源:"+this.getOrder_from()+"是否总部交互:"+this.getIs_send_zb()
				+"是否发送WMS："+this.getIs_send_wms()+"商品类型："+this.getGoods_type()+"产品类型："+this.getProduct_type()+"是否新用户："+this.getIs_new_user()
				+"是否实物单:"+this.getIs_physics()+"是否需要开户:"+this.getIs_open_account()+"是否需要写卡:"+this.getIs_write_card()+"是否支持一键开户："+this.getIs_easy_account()+"是否闪电送:"+this.getIs_shipping_quick()+"网别:"+this.getNet_type()+"订单类型:"+this.getOrder_type()+":生产模式"+this.getOrder_model()
				+"平台类型:"+this.getPlat_type()+"配送方式:"+this.getSending_type()+"异常类型:"+this.getAbnormal_type()+"号卡类型:"+this.getCard_type());
		logger.info("================================================================>");
	}
	
	public String getIs_heyue() {
		is_heyue = EcsOrderConsts.NO_DEFAULT_VALUE;
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if(SpecConsts.TYPE_ID_20002.equals(goods_type) && EcsOrderConsts.NET_TYPE_4G.equals(this.getNet_type())){
			is_heyue = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_heyue;
	}

	public void setIs_heyue(String is_heyue) {
		this.is_heyue = is_heyue;
	}


	public String getSupply_id() {
		this.supply_id = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SUPPLY_ID);
		return supply_id;
	}

	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}
	

	
	public String getSyn_ord_zb() {		
		syn_ord_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
		return syn_ord_zb;
	}

	public void setSyn_ord_zb(String syn_ord_zb) {
		this.syn_ord_zb = syn_ord_zb;
	}

	public String getNeed_shipping() {
		need_shipping = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.NEED_SHIPPING);
		return need_shipping;
	}

	public void setNeed_shipping(String need_shipping) {
		this.need_shipping = need_shipping;
	}
	
	public String getComments() {
		comments = getOrderTree().getOrderBusiRequest().getRemark();
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getShip_name() {
		ship_name = getOrderTree().getOrderBusiRequest().getShip_name();
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	
	public String getIs_new_sys(){
		String sys_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYS_CODE);
		if(EcsOrderConsts.ORDER_SFLOW_NEW.equals(sys_code) || StringUtil.isEmpty(sys_code)){ //为空，也缺省为新系统处理
			return EcsOrderConsts.IS_DEFAULT_VALUE;
		}else{
			return EcsOrderConsts.NO_DEFAULT_VALUE;
		}
	}
	public void setIs_new_sys(String is_new_sys){
		this.is_new_sys = is_new_sys;
	}
	
	public String getOld_order_model() {
		this.old_order_model = getOrderTree().getOrderExtBusiRequest().getOld_order_model();
		return old_order_model;
	}

	public void setOld_order_model(String old_order_model) {
		this.old_order_model = old_order_model;
	}
}
