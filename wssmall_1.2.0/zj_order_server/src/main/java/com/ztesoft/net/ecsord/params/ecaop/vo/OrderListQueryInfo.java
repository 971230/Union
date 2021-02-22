package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 2.3.7.	订单列表查询接口查询参数
 * 
 * @author 宋琪
 *
 * @date 2017年6月1日
 */
public class OrderListQueryInfo implements Serializable {
	
	//yyyymmddhh24miss
	@ZteSoftCommentAnnotationParam(name="order_date_begin",type="String",isNecessary="N",desc="order_date_begin：订单开始时间")
	private String order_date_begin;
	
	//yyyymmddhh24miss
	@ZteSoftCommentAnnotationParam(name="order_date_end",type="String",isNecessary="N",desc="order_date_end：订单结束时间")
	private String order_date_end;
	
	@ZteSoftCommentAnnotationParam(name="order_id",type="String",isNecessary="N",desc="order_id：订单中心单号")
	private String order_id;
	
	//订单中心单号  发起方订单号
	@ZteSoftCommentAnnotationParam(name="out_order_id",type="String",isNecessary="N",desc="out_order_id：商城订单号")
	private String out_order_id;
	
	@ZteSoftCommentAnnotationParam(name="deal_office_id",type="String",isNecessary="N",desc="deal_office_id：操作点")
	private String deal_office_id;

	@ZteSoftCommentAnnotationParam(name="deal_operator",type="String",isNecessary="N",desc="deal_operator：操作员")
	private String deal_operator;
	
	//行销APP:10071
	@ZteSoftCommentAnnotationParam(name="source_system",type="String",isNecessary="N",desc="source_system：订单来源")
	private String source_system;
	
	@ZteSoftCommentAnnotationParam(name="bus_num",type="String",isNecessary="N",desc="bus_num：业务号码")
	private String bus_num;
	
	//暂无分类，后续补充
	@ZteSoftCommentAnnotationParam(name="order_type",type="String",isNecessary="N",desc="order_type：订单分类")
	private String order_type;
	
	//00 全部	01未支付	02 已支付	03 支付失败
	@ZteSoftCommentAnnotationParam(name="order_pay_status",type="String",isNecessary="N",desc="order_pay_status：支付状态")
	private String order_pay_status;
	
	//00 全部	0:未激活	2:线下激活成功3:线上激活成功	4:人工认证中	5:人工认证失败
	@ZteSoftCommentAnnotationParam(name="order_activite_status",type="String",isNecessary="N",desc="order_activite_status：后激活状态")
	private String order_activite_status;
	
	//00 全部	01 处理中	02 处理完成	03 已退单	04 已退款	05 异常
	@ZteSoftCommentAnnotationParam(name="order_status",type="String",isNecessary="N",desc="order_status：订单状态")
	private String order_status;
	
	//种子对应手机号码
	@ZteSoftCommentAnnotationParam(name="share_svc_num",type="String",isNecessary="N",desc="share_svc_num：种子对应手机号码")
	private String share_svc_num;
	
	//种子用户id
	@ZteSoftCommentAnnotationParam(name="market_user_id",type="String",isNecessary="N",desc="market_user_id：种子用户id")
	private String market_user_id;
	
	//商品id
	@ZteSoftCommentAnnotationParam(name="goods_id",type="String",isNecessary="N",desc="goods_id：商品id")
	private String goods_id;
	
	//联系电话
	@ZteSoftCommentAnnotationParam(name="ship_mobile",type="String",isNecessary="N",desc="ship_mobile：联系电话")
	private String ship_mobile;
	//证件号码
	@ZteSoftCommentAnnotationParam(name="cert_card_num",type="String",isNecessary="N",desc="cert_card_num：证件号码")
	private String cert_card_num;
	//入网姓名
	@ZteSoftCommentAnnotationParam(name="ship_name",type="String",isNecessary="N",desc="ship_name：入网姓名")
	private String ship_name;
	//每页展示条数
	@ZteSoftCommentAnnotationParam(name="page_size",type="String",isNecessary="Y",desc="page_size：每页展示条数")
	private String page_size;
	
	//具体页数
	@ZteSoftCommentAnnotationParam(name="page_no",type="String",isNecessary="Y",desc="page_no：具体页数")
	private String page_no;

	public String getOrder_date_begin() {
		return order_date_begin;
	}

	public void setOrder_date_begin(String order_date_begin) {
		this.order_date_begin = order_date_begin;
	}

	public String getOrder_date_end() {
		return order_date_end;
	}

	public void setOrder_date_end(String order_date_end) {
		this.order_date_end = order_date_end;
	}

	public String getDeal_office_id() {
		return deal_office_id;
	}

	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}

	public String getDeal_operator() {
		return deal_operator;
	}

	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getBus_num() {
		return bus_num;
	}

	public void setBus_num(String bus_num) {
		this.bus_num = bus_num;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public String getOrder_pay_status() {
		return order_pay_status;
	}

	public void setOrder_pay_status(String order_pay_status) {
		this.order_pay_status = order_pay_status;
	}

	public String getOrder_activite_status() {
		return order_activite_status;
	}

	public void setOrder_activite_status(String order_activite_status) {
		this.order_activite_status = order_activite_status;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}
	

	public String getShare_svc_num() {
		return share_svc_num;
	}

	public void setShare_svc_num(String share_svc_num) {
		this.share_svc_num = share_svc_num;
	}

	public String getMarket_user_id() {
		return market_user_id;
	}

	public void setMarket_user_id(String market_user_id) {
		this.market_user_id = market_user_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	
	public String getShip_mobile() {
		return ship_mobile;
	}

	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}

	public String getCert_card_num() {
		return cert_card_num;
	}

	public void setCert_card_num(String cert_card_num) {
		this.cert_card_num = cert_card_num;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}
	
	
}
