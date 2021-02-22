package com.ztesoft.net.mall.core.model;

import com.ztesoft.form.util.StringUtil;

/**
 * 商品申请
 * @author wui
 */
public class GoodsApply  implements java.io.Serializable {

	private Integer goods_num;
	private String apply_desc;
	private Double sales_price;
	private String goods_id;
	
	private String ship_day;
	private String ship_name;
	private String ship_addr;
	private String ship_tel;
	private String ship_mobile;
	
	private String zip;
	private String source_from;  //必填
	private String ship_id; //物流id 5
	private Integer payment_id;//支付id 5
	private String userid; //必填
	private String order_type; //订单来源 必填
	
	private String begin_nbr;
	private String end_nbr;
	
	private String apply_from; //申请类型
	
	private CardInfRequest cardInfRequest;
	
	private String bill_flag;
	
	private String type_code;
	private String lan_id;  //云卡、合约机 订单本地网lan_id
	
	
	
	private String returned_ids;//充值卡退货申请时，退货对主键
	
	public Integer getGoods_num() {
		if(goods_num ==null)
			goods_num=1;
		return goods_num;
	}
	public void setGoods_num(Integer goods_num) {
		this.goods_num = goods_num;
	}
	public String getApply_desc() {
		return apply_desc;
	}
	public void setApply_desc(String apply_desc) {
		this.apply_desc = apply_desc;
	}
	public Double getSales_price() {
		return sales_price;
	}
	public void setSales_price(Double sales_price) {
		this.sales_price = sales_price;
	}
	public String getShip_day() {
		return ship_day;
	}
	public void setShip_day(String ship_day) {
		this.ship_day = ship_day;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_addr() {
		return ship_addr;
	}
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	public String getShip_tel() {
		return ship_tel;
	}
	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getShip_id() {
		return ship_id;
	}
	public void setShip_id(String ship_id) {
		this.ship_id = ship_id;
	}
	
	public String getUserid() {
		if(StringUtil.isEmpty(order_type))
			throw new RuntimeException("订单类型不能为空");
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}
	public String getOrder_type() {
		if(StringUtil.isEmpty(order_type))
			order_type = "1";//默认为正常单
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getReturned_ids() {
		return returned_ids;
	}
	public void setReturned_ids(String returned_ids) {
		this.returned_ids = returned_ids;
	}
	public String getBegin_nbr() {
		return begin_nbr;
	}
	public void setBegin_nbr(String begin_nbr) {
		this.begin_nbr = begin_nbr;
	}
	public String getEnd_nbr() {
		return end_nbr;
	}
	public void setEnd_nbr(String end_nbr) {
		this.end_nbr = end_nbr;
	}
	public String getShip_mobile() {
		return ship_mobile;
	}
	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}
	public CardInfRequest getCardInfRequest() {
		return cardInfRequest;
	}
	public void setCardInfRequest(CardInfRequest cardInfRequest) {
		this.cardInfRequest = cardInfRequest;
	}
	public String getApply_from() {
		return apply_from;
	}
	public void setApply_from(String apply_from) {
		this.apply_from = apply_from;
	}
	public String getBill_flag() {
		return bill_flag;
	}
	public void setBill_flag(String bill_flag) {
		this.bill_flag = bill_flag;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	

	
}