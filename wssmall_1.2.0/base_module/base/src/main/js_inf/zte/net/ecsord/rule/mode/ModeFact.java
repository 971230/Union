package zte.net.ecsord.rule.mode;

import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

@DroolsFact(name="生产模式匹配",code="modeFact")
public class ModeFact extends AutoFact {

	private OrderTreeBusiRequest orderTreeBusiRequest;
	private String order_id;

	@DroolsFactField(name="地市", ele_type="checkbox", stype_code="DC_MODE_REGION")
	private String region;
	
	@DroolsFactField(name="商品类型", ele_type="checkbox", stype_code="DC_MODE_GOODS_TYPE")
	private String goods_type;
	
	@DroolsFactField(name="订单来源", ele_type="checkbox", stype_code="DC_MODE_SHOP_CODE")
	private String shop_code;
	
	@DroolsFactField(name="配送方式", ele_type="checkbox", stype_code="DC_MODE_SHIP_TYPE")
	private String shipping_type;
	
	@DroolsFactField(name="号卡类型", ele_type="checkbox", stype_code="DC_MODE_CARD_TYPE")
	private String card_type;
	
	@DroolsFactField(name="网别", ele_type="checkbox", stype_code="DC_MODE_NET_TYPE")
	private String net_type;
	
	@DroolsFactField(name="订单类型", ele_type="checkbox", stype_code="DC_ORDER_NEW_TYPE")
	private String order_type;
	
	@DroolsFactField(name="生产模式", ele_type="radio", stype_code="DC_MODE_OPER_MODE")
	private String oper_mode;
	
	@DroolsFactField(name="特殊业务类型", ele_type="radio", stype_code="DC_BUSINESS_TYPE")
	private String special_buss_type;
	
	@DroolsFactField(name="是否总部合约", ele_type="radio", stype_code="DIC_IS_GROUP_CONTRACT")
	private String is_group_contract;
	
	@DroolsFactField(name="是否老用户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_old;

	@DroolsFactField(name="是否社会机", ele_type="radio", stype_code="DIC_CUSTOMIZATION")
	private String is_society;
	
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return this.order_id;
	}

	@Override
	public String getTrace_flow_id() {
		trace_flow_id = getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		return trace_flow_id;
	}

	public String getRegion() {
		this.region = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.ORDER_CITY_CODE);
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getGoods_type() {
		this.goods_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),this.order_id, AttrConsts.GOODS_TYPE);
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getShop_code() {
		this.shop_code = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),this.order_id, AttrConsts.ORDER_FROM);
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}

	public String getShipping_type() {
		this.shipping_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),this.order_id, AttrConsts.SENDING_TYPE);
		return shipping_type;
	}

	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
	}

	public String getCard_type() {
		this.card_type = CommonDataFactory.getInstance().getAttrFieldValue(this.order_id, AttrConsts.SIM_TYPE);
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getNet_type() {
		this.net_type = CommonDataFactory.getInstance()
				 .getProductSpec(this.order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		return net_type;
	}

	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}

	public String getOper_mode() {
		return oper_mode;
	}

	public void setOper_mode(String oper_mode) {
		this.oper_mode = oper_mode;
	}

	public String getOrder_id() {
		return order_id;
	}

	public String getSpecial_buss_type() {
		special_buss_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SPECIAL_BUSI_TYPE);
		return special_buss_type;
	}

	public void setSpecial_buss_type(String special_buss_type) {
		this.special_buss_type = special_buss_type;
	}

	public String getIs_group_contract() {
		is_group_contract = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_GROUP_CONTRACT);
		return is_group_contract;
	}

	public void setIs_group_contract(String is_group_contract) {
		this.is_group_contract = is_group_contract;
	}

	public String getIs_old() {
		is_old = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_OLD);
		return is_old;
	}

	public void setIs_old(String is_old) {
		this.is_old = is_old;
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

	public void setOrder_id(String order_id) {
		orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		this.order_id = order_id;
	}

	public String getOrder_type() {
		this.order_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(this.order_id, AttrConsts.ORDER_TYPE);
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	
	public OrderTreeBusiRequest getOrderTree(){
		if(orderTreeBusiRequest ==null)
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		return orderTreeBusiRequest;
	}
	public void toStringN(){
		logger.info("================================================================>");
		logger.info("zte.net.ecsord.rule.tache.ModeFact日志信息打印:"+"订单编号："+this.getOrder_id()+"地市："+this.getRegion()+"订单来源:"+this.getShop_code()+"配送方式:"+this.getShipping_type()
				+"号卡类型:"+this.getCard_type()+"网别:"+this.getNet_type()+"订单类型:"+this.getOrder_type()+"生产模式:"+this.getOper_mode());
		logger.info("================================================================>");
	}
}
