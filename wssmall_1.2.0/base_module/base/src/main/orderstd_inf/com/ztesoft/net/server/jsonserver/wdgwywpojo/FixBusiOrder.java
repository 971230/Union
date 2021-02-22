package com.ztesoft.net.server.jsonserver.wdgwywpojo;

import java.util.List;

public class FixBusiOrder {

	private String serial_no;// 序列号
	private String create_time;// create_time
	private String source_system;// 发起方系统标识
	private String source_system_type;// 发起方系统标识小类
	private String mall_order_id;// 商城系统单号
	private String order_prov_code;// order_prov_code
	private String order_city_code;// order_city_code
	private String order_amount;// 订单应收金额
	private String pay_amount;// 订单实收金额
	private String discount_amount;// 订单减免金额
	private String discount_reason;// 订单减免原因
	private String buyer_message;// 买家留言
	private String order_remark;// 订单备注
	private FixBusiOrderDeliveryInfo delivery_info;// 联系人信息
	private List<FixBusiOrderGoodsInfo> goods_info;// 商品信息节点
	private FixBusiOrderDeveloperInfo developer_info;// 人员信息节点
	private FixBusiOrderBroadInfo broad_info;// 固网信息节点
	private FixBusiOrderCustInfo cust_info;// 客户信息节点
	private FixBusiOrderPayInfo pay_info;// 支付信息节点
	private List<FixBusiOrderSubProdInfo> sub_prod_info;// 关联商品信息节点
	private FixBusiOrderSubsOtherInfo subs_other_info;// 集客业务专属信息节点
	private String rel_order_id;

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getSource_system_type() {
		return source_system_type;
	}

	public void setSource_system_type(String source_system_type) {
		this.source_system_type = source_system_type;
	}

	public String getMall_order_id() {
		return mall_order_id;
	}

	public void setMall_order_id(String mall_order_id) {
		this.mall_order_id = mall_order_id;
	}

	public String getOrder_prov_code() {
		return order_prov_code;
	}

	public void setOrder_prov_code(String order_prov_code) {
		this.order_prov_code = order_prov_code;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}

	public String getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(String discount_amount) {
		this.discount_amount = discount_amount;
	}

	public String getDiscount_reason() {
		return discount_reason;
	}

	public void setDiscount_reason(String discount_reason) {
		this.discount_reason = discount_reason;
	}

	public String getBuyer_message() {
		return buyer_message;
	}

	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}

	public String getOrder_remark() {
		return order_remark;
	}

	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}

	public FixBusiOrderDeliveryInfo getDelivery_info() {
		return delivery_info;
	}

	public void setDelivery_info(FixBusiOrderDeliveryInfo delivery_info) {
		this.delivery_info = delivery_info;
	}

	public List<FixBusiOrderGoodsInfo> getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(List<FixBusiOrderGoodsInfo> goods_info) {
		this.goods_info = goods_info;
	}

	public FixBusiOrderDeveloperInfo getDeveloper_info() {
		return developer_info;
	}

	public void setDeveloper_info(FixBusiOrderDeveloperInfo developer_info) {
		this.developer_info = developer_info;
	}

	public FixBusiOrderBroadInfo getBroad_info() {
		return broad_info;
	}

	public void setBroad_info(FixBusiOrderBroadInfo broad_info) {
		this.broad_info = broad_info;
	}

	public FixBusiOrderCustInfo getCust_info() {
		return cust_info;
	}

	public void setCust_info(FixBusiOrderCustInfo cust_info) {
		this.cust_info = cust_info;
	}

	public FixBusiOrderPayInfo getPay_info() {
		return pay_info;
	}

	public void setPay_info(FixBusiOrderPayInfo pay_info) {
		this.pay_info = pay_info;
	}

	public List<FixBusiOrderSubProdInfo> getSub_prod_info() {
		return sub_prod_info;
	}

	public void setSub_prod_info(List<FixBusiOrderSubProdInfo> sub_prod_info) {
		this.sub_prod_info = sub_prod_info;
	}

	public FixBusiOrderSubsOtherInfo getSubs_other_info() {
		return subs_other_info;
	}

	public void setSubs_other_info(FixBusiOrderSubsOtherInfo subs_other_info) {
		this.subs_other_info = subs_other_info;
	}

	public String getRel_order_id() {
		return rel_order_id;
	}

	public void setRel_order_id(String rel_order_id) {
		this.rel_order_id = rel_order_id;
	}

}
