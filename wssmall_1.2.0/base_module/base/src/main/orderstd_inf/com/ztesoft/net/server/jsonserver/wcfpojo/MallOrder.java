package com.ztesoft.net.server.jsonserver.wcfpojo;

import java.util.List;
import java.util.Map;

public class MallOrder {

	private Map<String, Object> ext_params;

	// 序列号
	private String serial_no = "";

	// 时间
	private String time = "";

	// 发起方系统标识
	private String source_system = "";

	// 接收方系统标识
	private String receive_system = "";

	// 订单编码(外系统)
	private String order_id = "";

	// 订单类型
	private String order_type = "";

	// 订单状态
	private String status = "";

	// 是否2G、3G升4G
	private String is_to4g = "";

	// 订单来源系统
	private String source_from_system = "";

	// 订单来源
	private String source_from = "";

	// 归属地市
	private String order_city = "";

	// 渠道标记
	private String channel_mark = "";

	// 推广渠道
	private String spread_channel = "";

	//
	private String develop_name = "";
	private String develop_code = "";

	// //收入归集集团15位编号
	// private String group_code = "";

	// 集团客户编号,可空
	private String cust_id = "";

	// 责任人/使用人标识【1】 0责任人，1使用人
	private String certify_flag = "";

	// 责任人/使用人证件类型
	private String certify_cert_type = "";

	// 责任人/使用人证件号码
	private String certify_cert_num = "";

	// 责任人/使用人客户姓名
	private String certify_cust_name = "";
	private String certify_cert_addr = "";
	private String channel_type = "";

	// 渠道标识
	private String channel_id = "";

	// 渠道名称
	private String chanel_name = "";

	// 发展人编码
	private String development_code = "";

	// 推荐人手机
	private String reference_phone = "";

	// 推荐人名称
	private String reference_name = "";

	// 下单时间
	private String create_time = "";

	// 支付时间
	private String pay_time = "";

	// 支付类型
	private String pay_type = "";

	// 支付方式
	private String payment_type = "";

	// 支付流水号
	private String payment_serial_no = "";

	// 支付机构编码
	private String payment_code = "";

	// 支付机构名称
	private String payment_name = "";

	// 支付渠道编码
	private String payment_channel_code = "";

	// 支付渠道名称
	private String payment_channel_name = "";

	// 外部支付账号
	private String alipay_id = "";

	// 买家名称
	private String name = "";

	// 买家标识
	private String uid = "-1";

	// 买家昵称
	private String uname = "";

	// 发货状态
	private String delivery_status = "";

	// 异常状态
	private String abnormal_status = "正常";

	// 外部平台状态
	private String platform_status = "";

	// 订单总价
	private String order_amount = "";

	// 订单重量(KG)
	private String order_heavy = "";

	// 优惠金额
	private String order_disacount = "";

	// 实收金额
	private String pay_money = "";

	// 是否作废---zengxianlian
	private String if_cancel;

	// 应收运费
	private String shipping_amount = "0";

	// 实收运费
	private String n_shipping_amount = "0";

	// 订单兑换积分
	private String order_points = "";

	// 积分兑换用户
	private String points_user = "";
	private String bss_operator = ""; // BSS工号
	private String oss_operator = ""; // 订单支撑系统工号
	private String bss_operator_name = ""; // BSS工号名称
	private String terminal_num = ""; // 终端串号
	private String agent_code = ""; // 代理商编码
	private String agent_name = ""; // 代理商名称
	private String agent_city = ""; // 代理商归属地市
	private String agent_area = ""; // 代理商归属区县
	private String out_office; // 外部操作点【智慧沃企】
	private String out_operator; // 外部操作员【智慧沃企】
	private String is_pay; //
	private String couponbatchid; //
	private String sale_mod_type; //
	private String marking_tag; //
	private String district_code; //
	private String channelid; //
	private String bssOrderId; //
	private String is_aop; //
	private String old_cust_id;

	// 物流公司编码
	private String shipping_company = "";

	// 是否闪电送
	private String shipping_quick = "01";

	// 配送方式
	private String shipping_type = "";

	// 配送时间
	private String shipping_time = "";

	// 收货人姓名
	private String ship_name = "";

	// 地址地市
	private String ship_city = "";

	// 地址区县
	private String ship_country = "";

	// 订单归属省份编码
	private String order_provinc_code = "";

	// 订单归属地市编码
	private String order_city_code = "";

	// 地址商圈
	private String ship_area = "";

	// 地址
	private String ship_addr = "";

	// 邮件编码
	private String postcode = "";

	// 固定电话
	private String ship_tel = "";

	// 手机号码
	private String ship_phone = "";

	// 电子邮件
	private String ship_email = "";

	// 百度账号
	private String baidu_account = "";

	// 百度冻结流水号
	private String baidu_no = "";

	// 百度冻结款
	private String baidu_money = "";

	// 百度冻结开始时间
	private String baidu_begin_time = "";

	// 百度冻结结束时间
	private String baidu_end_time = "";

	// 基金类型
	private String fund_type;

	// 买家留言
	private String buyer_message = "";

	// 卖家留言
	private String seller_message = "";

	// 所属用户
	private String ssyh = "";

	// 是否已办理完成
	private String is_deal = "0";

	// 订单备注
	private String order_comment = "";

	// 订单清单
	private List<MallOrder_List> order_list;

	// 集团编码
	private String group_code = "";

	// 集团名称
	private String group_name = "";
	private String industry_source; // 行业来源【智慧沃企】
	private String service_type; // 服务类型【智慧沃企】
									// 行业应用类别
	private String industry_type = "";

	// 应用子类别
	private String industry_sub_type = "";

	// 销售商品名称
	private String sales_name = "";

	// ICCID
	private String iccid = "";
	private String develop_departId;
	private String intent_order_id;
	private String is_realname;
	private Map<String, Object> extMap;
	private String is_examine_card = "1";
	
	private String evdo_num;
	
	private String user_kind ;
	
	private String order_deal_method ;//线上转线下订单处理方式。1--线上，2--线下
	
	// add by zhaochen 流程编码
	private String flow_code;
	
	// add by sgf
    private String is_new = "";
    private String is_blankcard = "";
    // add by mh
    private Map<String, Object> nice_info ;
	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getEvdo_num() {
		return evdo_num;
	}

	public void setEvdo_num(String evdo_num) {
		this.evdo_num = evdo_num;
	}

	public String getCertify_cert_addr() {
		return certify_cert_addr;
	}

	public void setCertify_cert_addr(String certify_cert_addr) {
		this.certify_cert_addr = certify_cert_addr;
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

	public String getDevelop_name() {
		return develop_name;
	}

	public void setDevelop_name(String develop_name) {
		this.develop_name = develop_name;
	}

	public String getDevelop_code() {
		return develop_code;
	}

	public void setDevelop_code(String develop_code) {
		this.develop_code = develop_code;
	}

	public String getSpread_channel() {
		return spread_channel;
	}

	public void setSpread_channel(String spread_channel) {
		this.spread_channel = spread_channel;
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getBss_operator_name() {
		return bss_operator_name;
	}

	public void setBss_operator_name(String bss_operator_name) {
		this.bss_operator_name = bss_operator_name;
	}

	public String getSale_mod_type() {
		return sale_mod_type;
	}

	public void setSale_mod_type(String sale_mod_type) {
		this.sale_mod_type = sale_mod_type;
	}

	public String getMarking_tag() {
		return marking_tag;
	}

	public void setMarking_tag(String marking_tag) {
		this.marking_tag = marking_tag;
	}

	public String getDistrict_code() {
		return district_code;
	}

	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getBssOrderId() {
		return bssOrderId;
	}

	public void setBssOrderId(String bssOrderId) {
		this.bssOrderId = bssOrderId;
	}

	public String getIs_aop() {
		return is_aop;
	}

	public void setIs_aop(String is_aop) {
		this.is_aop = is_aop;
	}

	public String getAgent_code() {
		return agent_code;
	}

	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getAgent_city() {
		return agent_city;
	}

	public void setAgent_city(String agent_city) {
		this.agent_city = agent_city;
	}

	public String getAgent_area() {
		return agent_area;
	}

	public void setAgent_area(String agent_area) {
		this.agent_area = agent_area;
	}

	public String getTerminal_num() {
		return terminal_num;
	}

	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}

	public String getBss_operator() {
		return bss_operator;
	}

	public void setBss_operator(String bss_operator) {
		this.bss_operator = bss_operator;
	}

	public String getOss_operator() {
		return oss_operator;
	}

	public void setOss_operator(String oss_operator) {
		this.oss_operator = oss_operator;
	}

	public String getPoints_user() {
		return points_user;
	}

	public void setPoints_user(String points_user) {
		this.points_user = points_user;
	}

	public String getIs_realname() {
		return is_realname;
	}

	public void setIs_realname(String is_realname) {
		this.is_realname = is_realname;
	}

	public String getDevelop_departId() {
		return develop_departId;
	}

	public void setDevelop_departId(String develop_departId) {
		this.develop_departId = develop_departId;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getOrder_points() {
		return order_points;
	}

	public void setOrder_points(String order_points) {
		this.order_points = order_points;
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

	public String getIndustry_source() {
		return industry_source;
	}

	public void setIndustry_source(String industry_source) {
		this.industry_source = industry_source;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getIndustry_type() {
		return industry_type;
	}

	public void setIndustry_type(String industry_type) {
		this.industry_type = industry_type;
	}

	public String getIndustry_sub_type() {
		return industry_sub_type;
	}

	public void setIndustry_sub_type(String industry_sub_type) {
		this.industry_sub_type = industry_sub_type;
	}

	public String getIs_to4g() {
		return is_to4g;
	}

	public void setIs_to4g(String is_to4g) {
		this.is_to4g = is_to4g;
	}

	public String getPayment_code() {
		return payment_code;
	}

	public void setPayment_code(String payment_code) {
		this.payment_code = payment_code;
	}

	public String getPayment_name() {
		return payment_name;
	}

	public void setPayment_name(String payment_name) {
		this.payment_name = payment_name;
	}

	public String getPayment_channel_code() {
		return payment_channel_code;
	}

	public void setPayment_channel_code(String payment_channel_code) {
		this.payment_channel_code = payment_channel_code;
	}

	public String getPayment_channel_name() {
		return payment_channel_name;
	}

	public void setPayment_channel_name(String payment_channel_name) {
		this.payment_channel_name = payment_channel_name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getN_shipping_amount() {
		return n_shipping_amount;
	}

	public void setN_shipping_amount(String n_shipping_amount) {
		this.n_shipping_amount = n_shipping_amount;
	}

	public String getShipping_quick() {
		return shipping_quick;
	}

	public void setShipping_quick(String shipping_quick) {
		this.shipping_quick = shipping_quick;
	}

	public String getIs_deal() {
		return is_deal;
	}

	public void setIs_deal(String is_deal) {
		this.is_deal = is_deal;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getReceive_system() {
		return receive_system;
	}

	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource_from_system() {
		return source_from_system;
	}

	public void setSource_from_system(String source_from_system) {
		this.source_from_system = source_from_system;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getOrder_city() {
		return order_city;
	}

	public void setOrder_city(String order_city) {
		this.order_city = order_city;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getChanel_name() {
		return chanel_name;
	}

	public void setChanel_name(String chanel_name) {
		this.chanel_name = chanel_name;
	}

	public String getDevelopment_code() {
		return development_code;
	}

	public void setDevelopment_code(String development_code) {
		this.development_code = development_code;
	}

	public String getReference_phone() {
		return reference_phone;
	}

	public void setReference_phone(String reference_phone) {
		this.reference_phone = reference_phone;
	}

	public String getReference_name() {
		return reference_name;
	}

	public void setReference_name(String reference_name) {
		this.reference_name = reference_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getPayment_serial_no() {
		return payment_serial_no;
	}

	public void setPayment_serial_no(String payment_serial_no) {
		this.payment_serial_no = payment_serial_no;
	}

	public String getAlipay_id() {
		return alipay_id;
	}

	public void setAlipay_id(String alipay_id) {
		this.alipay_id = alipay_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getDelivery_status() {
		return delivery_status;
	}

	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}

	public String getAbnormal_status() {
		return abnormal_status;
	}

	public void setAbnormal_status(String abnormal_status) {
		this.abnormal_status = abnormal_status;
	}

	public String getPlatform_status() {
		return platform_status;
	}

	public void setPlatform_status(String platform_status) {
		this.platform_status = platform_status;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getOrder_heavy() {
		return order_heavy;
	}

	public void setOrder_heavy(String order_heavy) {
		this.order_heavy = order_heavy;
	}

	public String getOrder_disacount() {
		return order_disacount;
	}

	public void setOrder_disacount(String order_disacount) {
		this.order_disacount = order_disacount;
	}

	public String getPay_money() {
		return pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getShipping_amount() {
		return shipping_amount;
	}

	public void setShipping_amount(String shipping_amount) {
		this.shipping_amount = shipping_amount;
	}

	public String getShipping_company() {
		return shipping_company;
	}

	public void setShipping_company(String shipping_company) {
		this.shipping_company = shipping_company;
	}

	public String getShipping_type() {
		return shipping_type;
	}

	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
	}

	public String getShipping_time() {
		return shipping_time;
	}

	public void setShipping_time(String shipping_time) {
		this.shipping_time = shipping_time;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getShip_city() {
		return ship_city;
	}

	public void setShip_city(String ship_city) {
		this.ship_city = ship_city;
	}

	public String getShip_country() {
		return ship_country;
	}

	public void setShip_country(String ship_country) {
		this.ship_country = ship_country;
	}

	public String getOrder_provinc_code() {
		return order_provinc_code;
	}

	public void setOrder_provinc_code(String order_provinc_code) {
		this.order_provinc_code = order_provinc_code;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getShip_area() {
		return ship_area;
	}

	public void setShip_area(String ship_area) {
		this.ship_area = ship_area;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getShip_phone() {
		return ship_phone;
	}

	public void setShip_phone(String ship_phone) {
		this.ship_phone = ship_phone;
	}

	public String getShip_email() {
		return ship_email;
	}

	public void setShip_email(String ship_email) {
		this.ship_email = ship_email;
	}

	public String getBaidu_account() {
		return baidu_account;
	}

	public void setBaidu_account(String baidu_account) {
		this.baidu_account = baidu_account;
	}

	public String getBaidu_no() {
		return baidu_no;
	}

	public void setBaidu_no(String baidu_no) {
		this.baidu_no = baidu_no;
	}

	public String getBaidu_money() {
		return baidu_money;
	}

	public void setBaidu_money(String baidu_money) {
		this.baidu_money = baidu_money;
	}

	public String getBaidu_begin_time() {
		return baidu_begin_time;
	}

	public void setBaidu_begin_time(String baidu_begin_time) {
		this.baidu_begin_time = baidu_begin_time;
	}

	public String getBaidu_end_time() {
		return baidu_end_time;
	}

	public void setBaidu_end_time(String baidu_end_time) {
		this.baidu_end_time = baidu_end_time;
	}

	public String getBuyer_message() {
		return buyer_message;
	}

	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}

	public String getSeller_message() {
		return seller_message;
	}

	public void setSeller_message(String seller_message) {
		this.seller_message = seller_message;
	}

	public String getSsyh() {
		return ssyh;
	}

	public void setSsyh(String ssyh) {
		this.ssyh = ssyh;
	}

	public String getOrder_comment() {
		return order_comment;
	}

	public void setOrder_comment(String order_comment) {
		this.order_comment = order_comment;
	}

	public List<MallOrder_List> getOrder_list() {
		return order_list;
	}

	public void setOrder_list(List<MallOrder_List> order_list) {
		this.order_list = order_list;
	}

	/**
	 * 获取订单来源的中文名称
	 *
	 * @param source_from
	 * @return
	 */
	public String getSourceFromName(String source_from) {
		if ("10001".equals(source_from)) {
			return "淘宝";
		} else if ("10002".equals(source_from)) {
			return "联通商城";
		} else if ("10003".equals(source_from)) {
			return "总部商城";
		} else if ("10004".equals(source_from)) {
			return "网盟店铺";
		} else if ("10005".equals(source_from)) {
			return "拍拍";
		} else if ("10006".equals(source_from)) {
			return "农行商城";
		} else if ("10007".equals(source_from)) {
			return "360商城";
		} else if ("10008".equals(source_from)) {
			return "沃云购";
		} else if ("10009".equals(source_from)) {
			return "订单系统";
		} else if ("10010".equals(source_from)) {
			return "WMS";
		} else if ("10011".equals(source_from)) {
			return "商品管理系统";
		} else if ("10012".equals(source_from)) {
			return "淘宝分销";
		} else if ("10036".equals(source_from)) {
			return "沃商城";
		} else if ("10037".equals(source_from)) {
			return "CPS";
		} else if ("10038".equals(source_from)) {
			return "异业联盟";
		} else if ("10039".equals(source_from)) {
			return "百度担保";
		} else if ("10015".equals(source_from)) {
			return "电话商城";
		} else if ("10030".equals(source_from)) {
			return "微商城";
		} else if ("10031".equals(source_from)) {
			return "沃货架";
		} else if ("10032".equals(source_from)) {
			return "营业厅U惠站";
		} else if ("10033".equals(source_from)) {
			return "销售联盟";
		} else if ("10034".equals(source_from)) {
			return "vip商城";
		} else if ("10035".equals(source_from)) {
			return "电子沃店";
		} else if ("10014".equals(source_from) || "10040".equals(source_from)) {
			return "沃财富";
		} else if ("10057".equals(source_from)) {
			return "广州天猫";
		} else if ("10053".equals(source_from)) {
			return "B2B商城";
		} else {
			return "其他";
		}
	}

	/**
	 * 获取订单来源系统的中文名称
	 *
	 * @param source_from_system
	 * @return
	 */
	public String getSourceFromSystemName(String source_from_system) {
		if ("10001".equals(source_from_system)) {
			return "淘宝";
		} else if ("10002".equals(source_from_system)) {
			return "联通商城";
		} else if ("10003".equals(source_from_system)) {
			return "总部商城";
		} else if ("10004".equals(source_from_system)) {
			return "网盟店铺";
		} else if ("10005".equals(source_from_system)) {
			return "拍拍";
		} else if ("10006".equals(source_from_system)) {
			return "农行商城";
		} else if ("10007".equals(source_from_system)) {
			return "360商城";
		} else if ("10008".equals(source_from_system)) {
			return "沃云购";
		} else if ("10009".equals(source_from_system)) {
			return "订单系统";
		} else if ("10010".equals(source_from_system)) {
			return "WMS";
		} else if ("10011".equals(source_from_system)) {
			return "商品管理系统";
		} else if ("10012".equals(source_from_system)) {
			return "淘宝分销";
		} else if ("10036".equals(source_from_system)) {
			return "沃商城";
		} else if ("10037".equals(source_from_system)) {
			return "CPS";
		} else if ("10038".equals(source_from_system)) {
			return "异业联盟";
		} else if ("10015".equals(source_from_system)) {
			return "电话商城";
		} else if ("10030".equals(source_from_system)) {
			return "微商城";
		} else if ("10031".equals(source_from_system)) {
			return "沃货架";
		} else if ("10032".equals(source_from_system)) {
			return "营业厅U惠站";
		} else if ("10033".equals(source_from_system)) {
			return "销售联盟";
		} else if ("10034".equals(source_from_system)) {
			return "vip商城";
		} else if ("10035".equals(source_from_system)) {
			return "电子沃店";
		} else if ("10039".equals(source_from_system)) {
			return "百度担保";
		} else if ("10013".equals(source_from_system)) {
			return "深圳联通商城";
		} else if ("10057".equals(source_from_system)) {
			return "广州天猫";
		} else if ("10053".equals(source_from_system)) {
			return "B2B商城";
		} else {
			return "其他";
		}
	}

	/**
	 * 获取配送方式
	 *
	 * @param shipping_type
	 * @return
	 */
	public String getSendingType(String shipping_type) {
		if ("KD".equalsIgnoreCase(shipping_type)) {
			return "快递";
		} else if ("SH".equalsIgnoreCase(shipping_type)) {
			return "送货";
		} else if ("ZT".equalsIgnoreCase(shipping_type)) {
			return "自提";
		} else if ("SDS".equalsIgnoreCase(shipping_type)) {
			return "闪电送";
		} else {
			return "其他";
		}
	}

	/**
	 * 获取渠道类型中文名称
	 *
	 * @param channel_type
	 * @return
	 */
	public String getChannelTypeName(String channel_type) {
		if ("77".equals(channel_type)) {
			return "自有渠道";
		} else if ("97".equals(channel_type)) {
			return "社会渠道";
		} else if ("137".equals(channel_type)) {
			return "其它渠道";
		} else if ("0".equals(channel_type)) {
			return "自营";
		} else if ("1".equals(channel_type)) {
			return "代理商";
		} else if ("2".equals(channel_type)) {
			return "店铺";
		} else {
			return "其他渠道";
		}
	}

	/**
	 * 获取卡类型
	 *
	 * @param card_type
	 * @return
	 */
	public String getCardType(String card_type) {
		if ("NM".equalsIgnoreCase(card_type)) {
			return "1";
		} else if ("MC".equalsIgnoreCase(card_type)) {
			return "2";
		} else if ("NN".equalsIgnoreCase(card_type)) {
			return "3";
		} else {
			return card_type;
		}
	}

	public String getChannel_mark() {
		return channel_mark;
	}

	public void setChannel_mark(String channel_mark) {
		this.channel_mark = channel_mark;
	}

	public String getFund_type() {
		return fund_type;
	}

	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}

	public String getIs_examine_card() {
		return is_examine_card.equals("") ? "1" : is_examine_card;
	}

	public void setIs_examine_card(String is_examine_card) {
		this.is_examine_card = is_examine_card;
	}

	public String getIf_cancel() {
		return if_cancel;
	}

	public void setIf_cancel(String if_cancel) {
		this.if_cancel = if_cancel;
	}

	public Map<String, Object> getExt_params() {
		return ext_params;
	}

	public void setExt_params(Map<String, Object> ext_params) {
		this.ext_params = ext_params;
	}

	public String getSales_name() {
		return sales_name;
	}

	public void setSales_name(String sales_name) {
		this.sales_name = sales_name;
	}

	public String getOut_office() {
		return out_office;
	}

	public void setOut_office(String out_office) {
		this.out_office = out_office;
	}

	public String getOut_operator() {
		return out_operator;
	}

	public void setOut_operator(String out_operator) {
		this.out_operator = out_operator;
	}

	public String getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public String getCouponbatchid() {
		return couponbatchid;
	}

	public void setCouponbatchid(String couponbatchid) {
		this.couponbatchid = couponbatchid;
	}

	public String getOld_cust_id() {
		return old_cust_id;
	}

	public void setOld_cust_id(String old_cust_id) {
		this.old_cust_id = old_cust_id;
	}

	public String getIntent_order_id() {
		return intent_order_id;
	}

	public void setIntent_order_id(String intent_order_id) {
		this.intent_order_id = intent_order_id;
	}

	public Map<String, Object> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, Object> extMap) {
		this.extMap = extMap;
	}

	public String getOrder_deal_method() {
		return order_deal_method;
	}

	public void setOrder_deal_method(String order_deal_method) {
		this.order_deal_method = order_deal_method;
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

	public Map<String, Object> getNice_info() {
		return nice_info;
	}

	public void setNice_info(Map<String, Object> nice_info) {
		this.nice_info = nice_info;
	}

	public String getFlow_code() {
		return flow_code;
	}

	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}


}
