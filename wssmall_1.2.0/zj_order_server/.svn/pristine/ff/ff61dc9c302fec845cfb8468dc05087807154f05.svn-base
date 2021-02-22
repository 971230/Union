package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class OrderMakeupReq extends ZteRequest {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "订单来源", type = "String", isNecessary = "Y", desc = "订单来源")
	private String source_from;
	@ZteSoftCommentAnnotationParam(name = "营业款(元)", type = "String", isNecessary = "Y", desc = "营业款(元)")
	private String ess_money;
	@ZteSoftCommentAnnotationParam(name = "买家留言", type = "String", isNecessary = "N", desc = "买家留言")
	private String buyer_message;
	@ZteSoftCommentAnnotationParam(name = "收货人姓名", type = "String", isNecessary = "Y", desc = "收货人姓名")
	private String ship_name;
	@ZteSoftCommentAnnotationParam(name = "新老用户", type = "String", isNecessary = "Y", desc = "新老用户")
	private String is_old;
	@ZteSoftCommentAnnotationParam(name = "客户类型", type = "String", isNecessary = "Y", desc = "")
	private String customer_type;
	@ZteSoftCommentAnnotationParam(name = "用户姓名", type = "String", isNecessary = "Y", desc = "用户姓名")
	private String cust_name;
	@ZteSoftCommentAnnotationParam(name = "备注", type = "String", isNecessary = "N", desc = "备注")
	private String service_remarks;
	@ZteSoftCommentAnnotationParam(name = "商品编码", type = "String", isNecessary = "Y", desc = "商品编码")
	private String prod_offer_code;
	@ZteSoftCommentAnnotationParam(name = "收货地址", type = "String", isNecessary = "Y", desc = "收货地址")
	private String ship_addr;
	@ZteSoftCommentAnnotationParam(name = "用户号码", type = "String", isNecessary = "Y", desc = "用户号码")
	private String acc_nbr;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "证件号码")
	private String certi_num;
	@ZteSoftCommentAnnotationParam(name = "合约期", type = "String", isNecessary = "N", desc = "合约期")
	private String contract_month;
	@ZteSoftCommentAnnotationParam(name = "是否实名", type = "String", isNecessary = "Y", desc = "是否实名")
	private String is_realname;
	@ZteSoftCommentAnnotationParam(name = "物流单号", type = "String", isNecessary = "N", desc = "物流单号")
	private String logi_no;
	@ZteSoftCommentAnnotationParam(name = "手机串号", type = "String", isNecessary = "N", desc = "手机串号")
	private String terminal_num;
	@ZteSoftCommentAnnotationParam(name = "收货人电话", type = "String", isNecessary = "Y", desc = "收货人电话")
	private String ship_phone;
	@ZteSoftCommentAnnotationParam(name = "商城实收(元)", type = "String", isNecessary = "Y", desc = "商城实收(元)")
	private String pay_money;
	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "Y", desc = "支付方式")
	private String pay_method;
	@ZteSoftCommentAnnotationParam(name = "商品名称", type = "String", isNecessary = "Y", desc = "商品名称")
	private String prod_offer_name;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "地市")
	private String order_city_code;
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "证件类型")
	private String certi_type;
	@ZteSoftCommentAnnotationParam(name = "发票号码", type = "String", isNecessary = "N", desc = "发票号码")
	private String invoice_no;
	// 订单接收文档里新增的字段（比较的对象是前台订单补录传回的字段）
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name = "外系统单号", type = "String", isNecessary = "N", desc = "行销APP系统单号")
	private String out_order_id;
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "N", desc = "操作点")
	private String deal_office_id;
	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "N", desc = "操作员")
	private String deal_operator;
	// 订单接收接口文档里没有的字段，但是前台订单补录传回有以下字段，暂时保留，不删除
	@ZteSoftCommentAnnotationParam(name = "套餐名称", type = "String", isNecessary = "Y", desc = "套餐名称")
	private String plan_title;
	@ZteSoftCommentAnnotationParam(name = "当前套餐", type = "String", isNecessary = "N", desc = "当前套餐")
	private String old_plan_title;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型")
	private String special_busi_type;
	@ZteSoftCommentAnnotationParam(name = "首月", type = "String", isNecessary = "N", desc = "首月")
	private String offer_eff_type;
	@ZteSoftCommentAnnotationParam(name = "商品分类", type = "String", isNecessary = "Y", desc = "商品分类")
	private String prod_offer_type;
	@ZteSoftCommentAnnotationParam(name = "商品分类名称", type = "String", isNecessary = "Y", desc = "商品分类名称")
	private String prod_offer_type_name;
	@ZteSoftCommentAnnotationParam(name = "靓号信息", type = "List", isNecessary = "N", desc = "靓号信息")
	private Map nice_info;
	@ZteSoftCommentAnnotationParam(name = "拍照流水", type = "String", isNecessary = "N", desc = "拍照流水")
	private String req_swift_num;
	@ZteSoftCommentAnnotationParam(name = "是否收费", type = "String", isNecessary = "N", desc = "是否收费")
	private String is_pay;
	@ZteSoftCommentAnnotationParam(name = "总部4G号卡销售模式类型", type = "String", isNecessary = "N", desc = "销售模式类型 0：自营厅行销模式 1：行销渠道直销模式")
	private String saleModType;
	@ZteSoftCommentAnnotationParam(name = "总部4G号卡是否行销", type = "String", isNecessary = "N", desc = "是否行销装备，0：否，1：是 该字段没传的时候默认 0：否")
	private String markingTag;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "N", desc = "渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "渠道ID", type = "String", isNecessary = "N", desc = "渠道ID")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "是否总部号卡", type = "String", isNecessary = "N", desc = "是否总部号卡1 aop 2 bss")
	private String is_aop;
	@ZteSoftCommentAnnotationParam(name = "cbss单号", type = "String", isNecessary = "N", desc = "缴费单的cb单号")
	private String cbss_order_id;
	@ZteSoftCommentAnnotationParam(name = "bss单号", type = "String", isNecessary = "N", desc = "缴费单的bss单号")
	private String bss_order_id;
	@ZteSoftCommentAnnotationParam(name = "bss单号", type = "String", isNecessary = "N", desc = "BSS发展人部门编码")
	private String develop_code;
	@ZteSoftCommentAnnotationParam(name = "bss单号", type = "String", isNecessary = "N", desc = "BSS发展人编码")
	private String develop_name;
	@ZteSoftCommentAnnotationParam(name = "收入归集集团15位编号", type = "String", isNecessary = "N", desc = "收入归集集团15位编号")
	private String group_code;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "")
	private String group_name;
	@ZteSoftCommentAnnotationParam(name = "集团客户编号,可空", type = "String", isNecessary = "N", desc = "集团客户编号,可空")
	private String cust_id;
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人标识【1】 0责任人，1使用人", type = "String", isNecessary = "N", desc = "责任人/使用人标识【1】 0责任人，1使用人")
	private String certify_flag;
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人证件类型", type = "String", isNecessary = "N", desc = "责任人/使用人证件类型")
	private String certify_cert_type;
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人证件号码", type = "String", isNecessary = "N", desc = "责任人/使用人证件号码")
	private String certify_cert_num;
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人客户姓名", type = "String", isNecessary = "N", desc = "责任人/使用人客户姓名")
	private String certify_cust_name;
	@ZteSoftCommentAnnotationParam(name = "责任人/使用人客户证件地址", type = "String", isNecessary = "N", desc = "责任人/使用人客户证件地址")
	private String certify_cert_addr;
	@ZteSoftCommentAnnotationParam(name = "老客户ID", type = "String", isNecessary = "N", desc = "老客户ID")
	private String old_cust_id;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "List", isNecessary = "N", desc = "客户信息")
	private Map cust_info;
	@ZteSoftCommentAnnotationParam(name = "发展人渠道", type = "String", isNecessary = "N", desc = "展人渠道")
	private String develop_departId;
	@ZteSoftCommentAnnotationParam(name = "意向单号", type = "String", isNecessary = "N", desc = "意向单号")
	private String rel_order_id;
	/*
	 * 收货地址省份 否 ship_province 收货地址地市 否 ship_city 收货地址县分 否 ship_county 收货地址邮编 否
	 * ship_code
	 */
	@ZteSoftCommentAnnotationParam(name = "收货地址省份", type = "String", isNecessary = "N", desc = "收货地址省份")
	private String ship_province;
	@ZteSoftCommentAnnotationParam(name = "收货地址地市", type = "String", isNecessary = "N", desc = "收货地址地市")
	private String ship_city;
	@ZteSoftCommentAnnotationParam(name = "收货地址县分", type = "String", isNecessary = "N", desc = "收货地址县分")
	private String ship_county;
	@ZteSoftCommentAnnotationParam(name = "收货地址邮编", type = "String", isNecessary = "N", desc = "收货地址邮编")
	private String ship_code;

	@ZteSoftCommentAnnotationParam(name = "无线宽带号码", type = "String", isNecessary = "N", desc = "无线宽带号码")
	private String evdo_num ;
	@ZteSoftCommentAnnotationParam(name = "无线宽带用户种类", type = "String", isNecessary = "N", desc = "无线宽带用户种类")
	private String user_kind ;
	
	@ZteSoftCommentAnnotationParam(name = "线上转线下订单处理方式", type = "String", isNecessary = "N", desc = "线上转线下订单处理方式。1--线上，2--线下")
	private String order_deal_method ;
	
	@ZteSoftCommentAnnotationParam(name = "是否更新订单", type = "String", isNecessary = "N", desc = "是否更新订单")
	private String is_update ;
	
	@ZteSoftCommentAnnotationParam(name = "流程编码", type = "String", isNecessary = "N", desc = "流程编码")
	private String flow_code ;
	
    @ZteSoftCommentAnnotationParam(name = "是否新装", type = "String", isNecessary = "N", desc = "1 是  0 否")
    private String is_new;
	    
    @ZteSoftCommentAnnotationParam(name = "是否白卡", type = "String", isNecessary = "N", desc = "1是 0否")
    private String is_blankcard;
    @ZteSoftCommentAnnotationParam(name = "iccid", type = "String", isNecessary = "N", desc = "iccid")
    private String iccid;
	
	@ZteSoftCommentAnnotationParam(name = "扩展属性", type = "Map", isNecessary = "N", desc = "")
	private Map<String, Object> extMap;


	public String getIs_aop() {
		return is_aop;
	}

	public void setIs_aop(String is_aop) {
		this.is_aop = is_aop;
	}

	public String getEvdo_num() {
		return evdo_num;
	}

	public void setEvdo_num(String evdo_num) {
		this.evdo_num = evdo_num;
	}

	public String getShip_province() {
		return ship_province;
	}

	public void setShip_province(String ship_province) {
		this.ship_province = ship_province;
	}

	public String getShip_city() {
		return ship_city;
	}

	public void setShip_city(String ship_city) {
		this.ship_city = ship_city;
	}

	public String getShip_county() {
		return ship_county;
	}

	public void setShip_county(String ship_county) {
		this.ship_county = ship_county;
	}

	public String getShip_code() {
		return ship_code;
	}

	public void setShip_code(String ship_code) {
		this.ship_code = ship_code;
	}

	public String getCbss_order_id() {
		return cbss_order_id;
	}

	public void setCbss_order_id(String cbss_order_id) {
		this.cbss_order_id = cbss_order_id;
	}

	public String getBss_order_id() {
		return bss_order_id;
	}

	public void setBss_order_id(String bss_order_id) {
		this.bss_order_id = bss_order_id;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSaleModType() {
		return saleModType;
	}

	public void setSaleModType(String saleModType) {
		this.saleModType = saleModType;
	}

	public String getMarkingTag() {
		return markingTag;
	}

	public void setMarkingTag(String markingTag) {
		this.markingTag = markingTag;
	}

	public Map getNice_info() {
		// if (nice_info==null) {
		// nice_info = new HashMap();
		// }
		return nice_info;
	}

	public void setNice_info(Map nice_info) {
		this.nice_info = nice_info;
	}

	// public List<NiceInfoVO> getNice_info() {
	// return nice_info;
	// }
	// public void setNice_info(Map map) {
	// List<NiceInfoVO> nice_info = new ArrayList<NiceInfoVO>();
	// NiceInfoVO bean = new NiceInfoVO();
	// bean.fromMap(map);
	// nice_info.add(bean);
	// this.nice_info = nice_info;
	// }
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.orderMakeupInsert";
	}

	public String getProd_offer_type() {
		return prod_offer_type;
	}

	public void setProd_offer_type(String prod_offer_type) {
		this.prod_offer_type = prod_offer_type;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getEss_money() {
		return ess_money;
	}

	public void setEss_money(String ess_money) {
		this.ess_money = ess_money;
	}

	public String getBuyer_message() {
		return buyer_message;
	}

	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}

	public String getProd_offer_type_name() {
		return prod_offer_type_name;
	}

	public void setProd_offer_type_name(String prod_offer_type_name) {
		this.prod_offer_type_name = prod_offer_type_name;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getPlan_title() {
		return plan_title;
	}

	public void setPlan_title(String plan_title) {
		this.plan_title = plan_title;
	}

	public String getIs_old() {
		return is_old;
	}

	public void setIs_old(String is_old) {
		this.is_old = is_old;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getService_remarks() {
		return service_remarks;
	}

	public void setService_remarks(String service_remarks) {
		this.service_remarks = service_remarks;
	}

	public String getOld_plan_title() {
		return old_plan_title;
	}

	public void setOld_plan_title(String old_plan_title) {
		this.old_plan_title = old_plan_title;
	}

	public String getProd_offer_code() {
		return prod_offer_code;
	}

	public void setProd_offer_code(String prod_offer_code) {
		this.prod_offer_code = prod_offer_code;
	}

	public String getOffer_eff_type() {
		return offer_eff_type;
	}

	public void setOffer_eff_type(String offer_eff_type) {
		this.offer_eff_type = offer_eff_type;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getCerti_num() {
		return certi_num;
	}

	public void setCerti_num(String certi_num) {
		this.certi_num = certi_num;
	}

	public String getContract_month() {
		return contract_month;
	}

	public void setContract_month(String contract_month) {
		this.contract_month = contract_month;
	}

	public String getSpecial_busi_type() {
		return special_busi_type;
	}

	public void setSpecial_busi_type(String special_busi_type) {
		this.special_busi_type = special_busi_type;
	}

	public String getIs_realname() {
		return is_realname;
	}

	public void setIs_realname(String is_realname) {
		this.is_realname = is_realname;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getTerminal_num() {
		return terminal_num;
	}

	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}

	public String getShip_phone() {
		return ship_phone;
	}

	public void setShip_phone(String ship_phone) {
		this.ship_phone = ship_phone;
	}

	public String getPay_money() {
		return pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getProd_offer_name() {
		return prod_offer_name;
	}

	public void setProd_offer_name(String prod_offer_name) {
		this.prod_offer_name = prod_offer_name;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getCerti_type() {
		return certi_type;
	}

	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getReceive_system() {
		return receive_system;
	}

	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
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

	public String getReq_swift_num() {
		return req_swift_num;
	}

	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}

	public String getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public String getDevelop_code() {
		return develop_code;
	}

	public void setDevelop_code(String develop_code) {
		this.develop_code = develop_code;
	}

	public String getDevelop_name() {
		return develop_name;
	}

	public void setDevelop_name(String develop_name) {
		this.develop_name = develop_name;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCertify_flag() {
		return certify_flag;
	}

	public void setCertify_flag(String certify_flag) {
		this.certify_flag = certify_flag;
	}

	public String getCertify_cert_type() {
		return certify_cert_type;
	}

	public void setCertify_cert_type(String certify_cert_type) {
		this.certify_cert_type = certify_cert_type;
	}

	public String getCertify_cert_num() {
		return certify_cert_num;
	}

	public void setCertify_cert_num(String certify_cert_num) {
		this.certify_cert_num = certify_cert_num;
	}

	public String getCertify_cust_name() {
		return certify_cust_name;
	}

	public void setCertify_cust_name(String certify_cust_name) {
		this.certify_cust_name = certify_cust_name;
	}

	public String getCertify_cert_addr() {
		return certify_cert_addr;
	}

	public void setCertify_cert_addr(String certify_cert_addr) {
		this.certify_cert_addr = certify_cert_addr;
	}

	public String getOld_cust_id() {
		return old_cust_id;
	}

	public void setOld_cust_id(String old_cust_id) {
		this.old_cust_id = old_cust_id;
	}

	public Map getCust_info() {
		return cust_info;
	}

	public void setCust_info(Map cust_info) {
		this.cust_info = cust_info;
	}

	public String getDevelop_departId() {
		return develop_departId;
	}

	public void setDevelop_departId(String develop_departId) {
		this.develop_departId = develop_departId;
	}

	public String getRel_order_id() {
		return rel_order_id;
	}

	public void setRel_order_id(String rel_order_id) {
		this.rel_order_id = rel_order_id;
	}

	public Map<String, Object> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, Object> extMap) {
		this.extMap = extMap;
	}


	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getOrder_deal_method() {
		return order_deal_method;
	}

	public void setOrder_deal_method(String order_deal_method) {
		this.order_deal_method = order_deal_method;
	}

	public String getIs_update() {
		return is_update;
	}

	public void setIs_update(String is_update) {
		this.is_update = is_update;
	}

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getIs_blankcard() {
        return is_blankcard;
    }

    public void setIs_blankcard(String is_blankcard) {
        this.is_blankcard = is_blankcard;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

	public String getFlow_code() {
		return flow_code;
	}

	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}
    
}
