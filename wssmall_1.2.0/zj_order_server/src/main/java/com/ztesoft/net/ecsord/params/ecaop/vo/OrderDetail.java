package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "order_id", type = "String", isNecessary = "N", desc = "order_id：订单中心单号")
	private String order_id;

	@ZteSoftCommentAnnotationParam(name = "bus_num", type = "String", isNecessary = "N", desc = "bus_num：业务号码")
	private String bus_num;

	@ZteSoftCommentAnnotationParam(name = "source_from", type = "String", isNecessary = "N", desc = "source_from：订单来源")
	private String source_from;// 10071 浙江行销APP 详见：发起系统方标识与发起系统方小类

	@ZteSoftCommentAnnotationParam(name = "out_order_id", type = "String", isNecessary = "N", desc = "out_order_id：外系统单号")
	private String out_order_id;

	@ZteSoftCommentAnnotationParam(name = "order_status", type = "String", isNecessary = "N", desc = "order_status：订单基本状态")
	private String order_status;// 01 处理中 02 处理完成 03 已退单 04 已退款 05 异常

	@ZteSoftCommentAnnotationParam(name = "order_status_n", type = "String", isNecessary = "N", desc = "order_status_n：订单补充状态")
	private String order_status_n;// 订单补充状态
	// 移网为号码激活状态 0:未激活 2:线下激活成功 3:线上激活成功 4:人工认证中 5:人工认证失败
	// 固网为施工竣工状态 状态待补充

	@ZteSoftCommentAnnotationParam(name = "order_city_code", type = "String", isNecessary = "N", desc = "order_city_code：订单归属地市")
	private String order_city_code;

	@ZteSoftCommentAnnotationParam(name = "county_id", type = "String", isNecessary = "N", desc = "order_city_code：订单归属县分")
	private String county_id;

	@ZteSoftCommentAnnotationParam(name = "create_time", type = "String", isNecessary = "N", desc = "create_time：订单创建时间")
	private String create_time;// yyyymmdd24hhmiss

	@ZteSoftCommentAnnotationParam(name = "bss_pre_order_id", type = "String", isNecessary = "N", desc = "bss_pre_order_id：Bss/cBSS单号")
	private String bss_pre_order_id;// Bss/cBSS预提交返回单号

	@ZteSoftCommentAnnotationParam(name = "order_goods_info", type = "OrderGoodsInfo", isNecessary = "N", desc = "order_goods_info：商品信息")
	private GoodsInfo order_goods_info;

	@ZteSoftCommentAnnotationParam(name = "order_cust_info", type = "OrderCustInfo", isNecessary = "N", desc = "order_cust_info：客户信息")
	private CustInfo order_cust_info;

	@ZteSoftCommentAnnotationParam(name = "order_pay_info", type = "OrderPayInfo", isNecessary = "N", desc = "order_pay_info：客户信息")
	private PayInfo order_pay_info;

	@ZteSoftCommentAnnotationParam(name = "order_developer_info", type = "OrderDeveloperInfo", isNecessary = "N", desc = "order_developer_info：发展人(推荐人)信息")
	private DeveloperInfo order_developer_info;

	@ZteSoftCommentAnnotationParam(name = "order_delivery_info", type = "OrderDeliveryInfo", isNecessary = "N", desc = "order_delivery_info：物流信息")
	private DeliveryInfo order_delivery_info;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBus_num() {
		return bus_num;
	}

	public void setBus_num(String bus_num) {
		this.bus_num = bus_num;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getOrder_status_n() {
		return order_status_n;
	}

	public void setOrder_status_n(String order_status_n) {
		this.order_status_n = order_status_n;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getBss_pre_order_id() {
		return bss_pre_order_id;
	}

	public void setBss_pre_order_id(String bss_pre_order_id) {
		this.bss_pre_order_id = bss_pre_order_id;
	}

	public GoodsInfo getOrder_goods_info() {
		return order_goods_info;
	}

	public void setOrder_goods_info(GoodsInfo order_goods_info) {
		this.order_goods_info = order_goods_info;
	}

	public CustInfo getOrder_cust_info() {
		return order_cust_info;
	}

	public void setOrder_cust_info(CustInfo order_cust_info) {
		this.order_cust_info = order_cust_info;
	}

	public PayInfo getOrder_pay_info() {
		return order_pay_info;
	}

	public void setOrder_pay_info(PayInfo order_pay_info) {
		this.order_pay_info = order_pay_info;
	}

	public DeveloperInfo getOrder_developer_info() {
		return order_developer_info;
	}

	public void setOrder_developer_info(DeveloperInfo order_developer_info) {
		this.order_developer_info = order_developer_info;
	}

	public DeliveryInfo getOrder_delivery_info() {
		return order_delivery_info;
	}

	public void setOrder_delivery_info(DeliveryInfo order_delivery_info) {
		this.order_delivery_info = order_delivery_info;
	}

}
