package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;


/**
 * 商品使用用户
 * @author kingapex
 *2010-4-25下午09:40:24
 */
public class GoodsUsers implements java.io.Serializable {
	
	
	private String pay_group_id;
	private String pay_user_id;
	
	private String accept_group_id;
	private String accept_user_id;
	
	private String ship_group_id;
	private String ship_user_id;
	
	private String confirm_group_id;
	private String confirm_user_id;
	private String order_id;
	private String goods_id;
	private String create_time;
	private String disabled;
	
	
	private String query_group_id;
	private String query_user_id;
	private String source_from;
	private String user_type;
	private String service_code;
	private String oper_id;
	
	private String goods_name;//非数据库字段
	private String qrType;
	private String payType;
	private String deliveryType;
	private String acceptType;
	private String queryType;
	private String[] goodsIds;
	
	private String qr_user_id;
	private String qr_group_id;
	private String qr_group_name;
	private String pay_group_name;
	private String ship_group_name;
	private String accept_group_name;
	private String query_group_name;
	
	private String service_code_name;
	public String getPay_group_id() {
		return pay_group_id;
	}
	public void setPay_group_id(String pay_group_id) {
		this.pay_group_id = pay_group_id;
	}
	public String getPay_user_id() {
		return pay_user_id;
	}
	public void setPay_user_id(String pay_user_id) {
		this.pay_user_id = pay_user_id;
	}
	public String getAccept_group_id() {
		return accept_group_id;
	}
	public void setAccept_group_id(String accept_group_id) {
		this.accept_group_id = accept_group_id;
	}
	public String getAccept_user_id() {
		return accept_user_id;
	}
	public void setAccept_user_id(String accept_user_id) {
		this.accept_user_id = accept_user_id;
	}
	public String getShip_group_id() {
		return ship_group_id;
	}
	public void setShip_group_id(String ship_group_id) {
		this.ship_group_id = ship_group_id;
	}
	public String getShip_user_id() {
		return ship_user_id;
	}
	public void setShip_user_id(String ship_user_id) {
		this.ship_user_id = ship_user_id;
	}
	public String getConfirm_group_id() {
		return confirm_group_id;
	}
	public void setConfirm_group_id(String confirm_group_id) {
		this.confirm_group_id = confirm_group_id;
	}
	public String getConfirm_user_id() {
		return confirm_user_id;
	}
	public void setConfirm_user_id(String confirm_user_id) {
		this.confirm_user_id = confirm_user_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getQuery_group_id() {
		return query_group_id;
	}
	public void setQuery_group_id(String query_group_id) {
		this.query_group_id = query_group_id;
	}
	public String getQuery_user_id() {
		return query_user_id;
	}
	public void setQuery_user_id(String query_user_id) {
		this.query_user_id = query_user_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	@NotDbField
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	@NotDbField
	public String[] getGoodsIds() {
		return goodsIds;
	}
	public void setGoodsIds(String[] goodsIds) {
		this.goodsIds = goodsIds;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	@NotDbField
	public String getQrType() {
		return qrType;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public void setQrType(String qrType) {
		this.qrType = qrType;
	}
	@NotDbField
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	@NotDbField
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	@NotDbField
	public String getAcceptType() {
		return acceptType;
	}
	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}
	@NotDbField
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	@NotDbField
	public String getQr_user_id() {
		return qr_user_id;
	}
	public void setQr_user_id(String qr_user_id) {
		this.qr_user_id = qr_user_id;
	}
	@NotDbField
	public String getQr_group_id() {
		return qr_group_id;
	}
	public void setQr_group_id(String qr_group_id) {
		this.qr_group_id = qr_group_id;
	}
	@NotDbField
	public String getQr_group_name() {
		return qr_group_name;
	}
	public void setQr_group_name(String qr_group_name) {
		this.qr_group_name = qr_group_name;
	}
	@NotDbField
	public String getPay_group_name() {
		return pay_group_name;
	}
	public void setPay_group_name(String pay_group_name) {
		this.pay_group_name = pay_group_name;
	}
	@NotDbField
	public String getShip_group_name() {
		return ship_group_name;
	}
	public void setShip_group_name(String ship_group_name) {
		this.ship_group_name = ship_group_name;
	}
	@NotDbField
	public String getAccept_group_name() {
		return accept_group_name;
	}
	public void setAccept_group_name(String accept_group_name) {
		this.accept_group_name = accept_group_name;
	}
	@NotDbField
	public String getQuery_group_name() {
		return query_group_name;
	}
	public void setQuery_group_name(String query_group_name) {
		this.query_group_name = query_group_name;
	}
	@NotDbField
	public String getService_code_name() {
		return service_code_name;
	}
	@NotDbField
	public void setService_code_name(String service_code_name) {
		this.service_code_name = service_code_name;
	}
	
	
}