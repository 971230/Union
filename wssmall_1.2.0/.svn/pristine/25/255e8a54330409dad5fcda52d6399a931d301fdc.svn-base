package com.ztesoft.net.server.jsonserver.objreplacepojo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ObjectReplaceBusiOrder implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "请求发起时间", type = "String", isNecessary = "Y", desc = "请求发起时间")
	private String create_time;// yyyymmddhh24miss
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识分类", type = "String", isNecessary = "Y", desc = "发起方系统标识分类")
	private String source_system_type;
	@ZteSoftCommentAnnotationParam(name = "商城系统单号", type = "String", isNecessary = "Y", desc = "商城系统单号")
	private String mall_order_id;// 外部系统订单号
	@ZteSoftCommentAnnotationParam(name = "订单归属省份", type = "String", isNecessary = "Y", desc = "订单归属省份")
	private String order_prov_code;// 6位标准编码，见城市编码
	@ZteSoftCommentAnnotationParam(name = "订单归属地市", type = "String", isNecessary = "Y", desc = "订单归属地市")
	private String order_city_code;// 6位标准编码，见城市编码
	@ZteSoftCommentAnnotationParam(name = "订单归属县分", type = "String", isNecessary = "Y", desc = "订单归属县分")
	private String order_county_code;// 6位标准编码，见城市编码
	@ZteSoftCommentAnnotationParam(name = "订单应收金额", type = "String", isNecessary = "Y", desc = "订单应收金额")
	private String order_amount;// 单位厘
	@ZteSoftCommentAnnotationParam(name = "订单实收金额", type = "String", isNecessary = "Y", desc = "订单实收金额")
	private String pay_amount;// 单位厘
	@ZteSoftCommentAnnotationParam(name = "订单减免金额", type = "String", isNecessary = "Y", desc = "订单减免金额")
	private String discount_amount;// 单位厘
	@ZteSoftCommentAnnotationParam(name = "订单减免原因", type = "String", isNecessary = "N", desc = "订单减免原因")
	private String discount_reason;
	@ZteSoftCommentAnnotationParam(name = "买家留言", type = "String", isNecessary = "N", desc = "买家留言")
	private String buyer_message;
	@ZteSoftCommentAnnotationParam(name = "订单备注", type = "String", isNecessary = "N", desc = "订单备注")
	private String order_remark;
	@ZteSoftCommentAnnotationParam(name = "业务号码", type = "String", isNecessary = "Y", desc = "业务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "是否收费", type = "String", isNecessary = "Y", desc = "是否收费")
	private String is_pay ;
	@ZteSoftCommentAnnotationParam(name = "联系人信息", type = "String", isNecessary = "1", desc = "联系人信息")
	private ObjectReplaceContactInfo contact_info;
	@ZteSoftCommentAnnotationParam(name = "商品信息节点", type = "String", isNecessary = "1-n", desc = "商品信息节点")
	private List<ObjectReplaceGoodsInfo> goods_info;//
	@ZteSoftCommentAnnotationParam(name = "客户信息节点", type = "String", isNecessary = "1", desc = "客户信息节点")
	private ObjectReplaceCustInfo cust_info;
	@ZteSoftCommentAnnotationParam(name = "支付信息节点", type = "String", isNecessary = "1", desc = "支付信息节点")
	private ObjectReplacePayInfo pay_info;
	@ZteSoftCommentAnnotationParam(name = "终端更换信息节点", type = "String", isNecessary = "1", desc = "终端更换信息节点")
	private ObjectReplaceObjectInfo object_info ;
	
	public String getIs_pay() {
		return is_pay;
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}
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
	public ObjectReplaceObjectInfo getObject_info() {
		return object_info;
	}
	public void setObject_info(ObjectReplaceObjectInfo object_info) {
		this.object_info = object_info;
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
	public String getOrder_county_code() {
		return order_county_code;
	}
	public void setOrder_county_code(String order_county_code) {
		this.order_county_code = order_county_code;
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
	public String getService_num() {
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public ObjectReplaceContactInfo getContact_info() {
		return contact_info;
	}
	public void setContact_info(ObjectReplaceContactInfo contact_info) {
		this.contact_info = contact_info;
	}
	public List<ObjectReplaceGoodsInfo> getGoods_info() {
		return goods_info;
	}
	public void setGoods_info(List<ObjectReplaceGoodsInfo> goods_info) {
		this.goods_info = goods_info;
	}
	public ObjectReplaceCustInfo getCust_info() {
		return cust_info;
	}
	public void setCust_info(ObjectReplaceCustInfo cust_info) {
		this.cust_info = cust_info;
	}
	public ObjectReplacePayInfo getPay_info() {
		return pay_info;
	}
	public void setPay_info(ObjectReplacePayInfo pay_info) {
		this.pay_info = pay_info;
	}
	
	
}
