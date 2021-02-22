package com.ztesoft.net.mall.core.model;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author kingapex
 *2010-4-6下午04:11:42
 */
public class OrderItem  implements java.io.Serializable {

	@ZteSoftCommentAnnotationParam(name="子订ID",type="String",isNecessary="Y",desc="子订ID")
     private String item_id;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
     private String order_id;
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="商品ID")
     private String goods_id;
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="Y",desc="产品ID")
     private String spec_id;
	@ZteSoftCommentAnnotationParam(name="购买数量",type="String",isNecessary="Y",desc="购买数量")
     private Integer num;
	@ZteSoftCommentAnnotationParam(name="发货数量",type="String",isNecessary="Y",desc="发货数量")
     private Integer ship_num;
	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="商品名称")
     private String name;
     private String sn;
     private Integer store; //对应货品的库存
     private String addon;
     
     private String type_id;
     private String have_stock;
     private String type_code;
     @ZteSoftCommentAnnotationParam(name="产品单价",type="String",isNecessary="Y",desc="产品单价")
     private Double price;
     private int gainedpoint;
     private String  order_outer_id;
     
     private String source_from;
 	
 	//入网信息,合约机、终端等
 	//private String acc_nbr;
 	//private String cust_name;
 	//private String certi_type;
 	//private String certi_number;
 	//private String terminal_code;
 	//private String terminal_name;
 	//private String lan_id;
 	//private String crm_order_id;
 	
 	private String back_num="0";
 	private String order_apply_id="0";
 	private String lv_id;
 	@ZteSoftCommentAnnotationParam(name="优惠后单价",type="String",isNecessary="Y",desc="优惠后单价")
 	private Double coupon_price;
 	
 	private String unit;
 	
 	//根据action_code与status判断是否需要受理、发货、支付
 	private String action_code;
 	private List<GoodsActionRule> actionRuleList;
 	
 	private String status;
 	@ZteSoftCommentAnnotationParam(name="类型:0商品 1配件 2赠品",type="String",isNecessary="Y",desc="类型:0商品 1配件 2赠品")
 	private Integer item_type=0;// 0商品 1配件 2赠品 3补寄礼品
 	private String product_id ="";
 	public Integer getItem_type() {
		return item_type;
	}
	public void setItem_type(Integer item_type) {
		this.item_type = item_type;
	}
	@NotDbField
 	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	@NotDbField
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getCoupon_price() {
		return coupon_price;
	}
	public void setCoupon_price(Double coupon_price) {
		this.coupon_price = coupon_price;
	}
	public String getLv_id() {
		return lv_id;
	}
	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}
	@NotDbField
 	public String getOrder_apply_id() {
		return order_apply_id;
	}
	public void setOrder_apply_id(String order_apply_id) {
		this.order_apply_id = order_apply_id;
	}
	@NotDbField
	public String getBack_num() {
		return back_num;
	}
	public void setBack_num(String back_num) {
		this.back_num = back_num;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Integer getShip_num() {
		return ship_num;
	}
	public void setShip_num(Integer ship_num) {
		this.ship_num = ship_num;
	}

	public String getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getGainedpoint() {
		return gainedpoint;
	}
	public void setGainedpoint(int gainedpoint) {
		this.gainedpoint = gainedpoint;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	@NotDbField
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public String getAddon() {
		return addon;
	}
	public void setAddon(String addon) {
		this.addon = addon;
	}
	@NotDbField
	public String getType_id() {
		return type_id;
	}
	@NotDbField
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	@NotDbField
	public String getHave_stock() {
		return have_stock;
	}
	@NotDbField
	public void setHave_stock(String have_stock) {
		this.have_stock = have_stock;
	}
	@NotDbField
	public String getType_code() {
		return type_code;
	}
	@NotDbField
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	/*public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCerti_type() {
		return certi_type;
	}
	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}
	public String getCerti_number() {
		return certi_number;
	}
	public void setCerti_number(String certi_number) {
		this.certi_number = certi_number;
	}
	public String getTerminal_code() {
		return terminal_code;
	}
	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}
	public String getTerminal_name() {
		return terminal_name;
	}
	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getCrm_order_id() {
		return crm_order_id;
	}
	public void setCrm_order_id(String crm_order_id) {
		this.crm_order_id = crm_order_id;
	}*/
	public String getOrder_outer_id() {
		return order_outer_id;
	}
	public void setOrder_outer_id(String order_outer_id) {
		this.order_outer_id = order_outer_id;
	}
	
	@NotDbField
	public List<GoodsActionRule> getActionRuleList() {
		return actionRuleList;
	}
	public void setActionRuleList(List<GoodsActionRule> actionRuleList) {
		this.actionRuleList = actionRuleList;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
}