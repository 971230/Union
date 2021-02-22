package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 充值卡调拨实体
 * 
 * @author wui
 */
public class Card implements java.io.Serializable {
	private String card_id;
	private String card_type;
	private String card_price;
	private String card_disc; //discount
	private String card_code;
	private String card_password;
	private String state;
	private String create_date;
	private String state_date;
	private String in_month;
	private String order_id;
	private String phone_number;
	private String exp_date;
	private String eff_date;
	private String to_userid;
	private String from_userid;
	private String sec_order_id; //淘宝的下单生成的订单id
	private String deal_time;
	private String ship_state;
	private String type_code; //类型编码 云卡CLOUD、合约机CONTRACT、流量卡RECHARGE_CARD、充值卡CARD
	
	private String goods_id; //面额的充值卡对象的商品
	private String state_name;
	
	private String batch_id;	//导入批次号
	private String import_state;	//导入状态
	private String import_userid;	//导入人
	private String comments;		//描述
	
	private String first_userid; //冗余字段、统计使用，记录一级分销商卡销售记录
	private String first_orderid; //冗余字段、统计使用，记录一级分销商订单编号
	
	private String p_apply_show_parent_stock; //申请看父级库存
	
	private String item_type;
	
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCard_price() {
		return card_price;
	}
	public void setCard_price(String card_price) {
		this.card_price = card_price;
	}
	public String getCard_disc() {
		return card_disc;
	}
	public void setCard_disc(String card_disc) {
		this.card_disc = card_disc;
	}
	public String getCard_code() {
		return card_code;
	}
	public void setCard_code(String card_code) {
		this.card_code = card_code;
	}
	public String getCard_password() {
		return card_password;
	}
	public void setCard_password(String card_password) {
		this.card_password = card_password;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getState_date() {
		return state_date;
	}
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getIn_month() {
		return in_month;
	}
	public void setIn_month(String in_month) {
		this.in_month = in_month;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getTo_userid() {
		return to_userid;
	}
	public void setTo_userid(String to_userid) {
		this.to_userid = to_userid;
	}
	public String getFrom_userid() {
		return from_userid;
	}
	public void setFrom_userid(String from_userid) {
		this.from_userid = from_userid;
	}
	public String getSec_order_id() {
		return sec_order_id;
	}
	public void setSec_order_id(String sec_order_id) {
		this.sec_order_id = sec_order_id;
	}
	@NotDbField
	public String getState_name() {
		String stateName = "";
		if(state.equals("0")){
			stateName = "可用";
		}else if(state.equals("1")){
			stateName = "预占";
		}else if(state.equals("2")){
			stateName = "已用";
		}else if(state.equals("3")){
			stateName = "失效";
		}
		return stateName;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	@Override
	public String toString() {
		return this.card_code;
	}
	public String getShip_state() {
		return ship_state;
	}
	public void setShip_state(String ship_state) {
		this.ship_state = ship_state;
	}
	
	public String getGoods_id() {
		return goods_id;
	}
	
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getFirst_userid() {
		return first_userid;
	}
	public void setFirst_userid(String first_userid) {
		this.first_userid = first_userid;
	}
	@NotDbField
	public String getP_apply_show_parent_stock() {
		return p_apply_show_parent_stock;
	}
	@NotDbField
	public void setP_apply_show_parent_stock(String p_apply_show_parent_stock) {
		this.p_apply_show_parent_stock = p_apply_show_parent_stock;
	}
	public String getFirst_orderid() {
		return first_orderid;
	}
	public void setFirst_orderid(String first_orderid) {
		this.first_orderid = first_orderid;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getImport_state() {
		return import_state;
	}
	public void setImport_state(String import_state) {
		this.import_state = import_state;
	}
	public String getImport_userid() {
		return import_userid;
	}
	public void setImport_userid(String import_userid) {
		this.import_userid = import_userid;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}