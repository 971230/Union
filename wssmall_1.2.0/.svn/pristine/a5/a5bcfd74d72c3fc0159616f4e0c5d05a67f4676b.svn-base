package zte.net.ecsord.rule.ordersy;

import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

@DroolsFact(name="订单分流匹配",code="orderSyFact")
public class OrderSyFact extends AutoFact {
	
	private String order_id;

	@DroolsFactField(name="地市", ele_type="checkbox", stype_code="DC_MODE_REGION")
	private String region_id;
	
	@DroolsFactField(name="订单来源", ele_type="checkbox", stype_code="DC_MODE_SHOP_CODE")
	private String order_from;
	
	@DroolsFactField(name="商品id", ele_type="input", stype_code="")
	private String goods_id;
	
	@DroolsFactField(name="配送方式", ele_type="checkbox", stype_code="DC_MODE_SHIP_TYPE")
	private String sending_type;

	@DroolsFactField(name="备注", ele_type="input", stype_code="")
	private String comments;
	
	@DroolsFactField(name="收获人姓名", ele_type="input", stype_code="")
	private String ship_name;
	

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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public String getTrace_flow_id() {
		return "";
	}

	public String getSending_type() {
		return sending_type;
	}

	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

}
