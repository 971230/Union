package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.common.util.StringUtils;

public class OrderQueryParams implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String QUERY_TYPE_RETURNED = "returned";// 退单查询
	public static final String QUERY_TYPE_ORDER = "order";// 正常订单查询
	public static final String QUERY_TYPE_EXCEPTION = "exception";// 异常单查询
	public static final String QUERY_TYPE_RETURNED_CFM = "returned_cfm";// 退单处理查询
	public static final String QUERY_TYPE_YCL = "ycl";// 预处理
	public static final String D_TYPE_CFM = "cfm";// 申请确认
	public static final String QUERY_TYPE_SUPPLY = "supply";//
	public static final String QUERY_TYPE_ORDER_VIEW = "order_view";// 查看所有单

	private String order_from;// 订单来源 ES_ORDER_EXT->order_from
	private String order_from_c;
	private String item_from;// 订单子来源 no 待定
	private String pay_start;// 支付开始时间 es_order->pay_time
	private String pay_end;// 支付结束时间 es_order->pay_time
	private String order_city_code;// 订单归属地市 es_order->order_city_code
	private String order_city_code_c;
	private String out_tid;// 外单编号 ES_ORDER_EXT->ES_ORDER_EXT
	private String order_id;// 内部单号 es_order->order_id
	private String status;// 处理状态 （全部 挂起 锁定 未锁定）待定
	private String shipping_id;// 配送方式 es_order->shipping_id
	private String shipping_id_c;
	private String order_channel;// 订单发展归属(订单渠道（推广渠道）)
									// ES_ORDER_EXT->order_channel
	private String order_channel_c;
	private String goods_pagekage_type;// 商品包类型 es_goods_type->type_id
	private String model_code;// 机型 es_goods->model_code
	// private String member_name;//所属用户 es_member->uname
	private String deal_type;// 处理类型 (全部、闪电送) no 待定;资源释放处理方式：0未处理 1线下处理 2接口处理
	private String quick_shipping_company;// 闪电送公司 待定
	private String quick_shipping_status;// 闪电送状态 待定
	private String create_start;// 创建时间
								// ES_ORDER_EXT->tid_time;资源释放失败时间es_order_release_record.create_time
	private String create_end;// 创建时间
								// ES_ORDER_EXT->tid_time;资源释放失败时间es_order_release_record.create_time
	private String payment_id;// 支付方式; es_order->payment_id
	private String payment_id_c;
	private String is_exception;// 是否异常订单( 全部订单、正常单、异常单) 待定
	private String exception_type;// 异常类型
	private String order_sn;// 订单条形码
	private String order_status;// 订单状态
	private String delivery_id;// 物流ID
	private String order_role;// 订单查询角色

	private String exception_code;// 异常编码
	private String exception_code_c;

	private String flow_id;// 订单环节流程ID ES_ORDER_EXT->flow_trace_id
	private String flow_id_c;//

	private String outcall_type;// 订单外呼类型
	private String outcall_type_c;

	private int visible_status = 0;// 是否可见，0可见 1不可见 -1全部

	private String not_flow_model_code;// 非生产模式

	private String refund_status; // 退单状态
	private String refund_deal_type;// = EcsOrderConsts.REFUND_DEAL_TYPE_01;
									// //订单状态类型 01正常单 02异常单

	private String query_type = QUERY_TYPE_ORDER;// 查询类型

	private String logi_no; // 物流单号

	private String phone_num; // 开户号码
	private String ship_tel; // 收货人电话
	private String ship_name; // 收货人

	private String order_supply_status;// 补寄状态 ，0-有待补寄 1-补寄完成
	private String order_is_his; // 1-查询归档的订单，0-查询还没归档的订单

	private String lock_user_id;// 订单锁定人号码;资源释放预占工号

	private String query_btn_flag;
	private String order_model;// 生产模式ES_ORDER_EXT->order_model
	private String order_model_c;// 生产模式ES_ORDER_EXT->order_model

	private String pay_status;// 支付状态

	private boolean memberQuery = false;// 是否会员查询

	private boolean retOrderTree = true;// 是否返回orderTree

	private String wms_refund_status;

	private int ship_status = -1;// 发货状态

	private String isProxy = "no";// 一件代发标记：yes_一件代发 no_代理商店铺查询

	// private String bssStatus;//bss业务办理状态
	// private String itemsBssStatus;//bss业务办理状态---zengxianlian
	private String invoice_num;// 发票号20150414实时监控报表增加查询条件

	private String receipt_status;// 回单状态

	private String sign_status;// 签收状态

	// private String is_zhwj;//是否智慧沃家产品:1-是 0-否

	private String goods_name;// 商品名称

	private String abnormal_type;// 人工异常
	private String exception_desc;// 人工异常原因
	private String auto_abnormal_type;// 自动化异常
	private String auto_exception_desc;// 自动化异常原因
	private String cust_type;// 客户类型
	private String group_code;// 集团编码
	private String group_name;// 集团名称
	private String type;// 资源释放类型
	private String prokey;// 资源释放预占关键字es_order_release_record.prokey
	private String wm_isreservation_from;// 是否预约单
	private String order_type;// 订单类型
	private String order_type_c;//
	private String is_old;// 是否老用户
	private String bss_refund_status;// bss退款状态
	private String exists_business_to_deal_with;// 代办业务

	private String iccid;

	private String bss_time_type;// 是否当月处理

	private String lock_status;// 锁单状态

	private String order_src;
	private String data_src;
	private String pay_type;
	private String area_id;
	private String buyer;
	private String subs_svc_number;
	private String express_type;

	private String username;// 订单处理人工号

	private String active_type;// 激活方式
	private String active_flag;// 激活状态
	private String account_vali;// 证件审核状态
	private String later_active_flag;// 实名制方式
	private String station_id;// 写卡位编码
	private String machine_id;// 写卡机编码
	private String bat_id;// 批次号
	private String goods_id; // 批量预处理goods_id
	private String goods_id_c; // 批量预处理
	private String newOrderList;

	private String handle_status;// 批量审核状态 0 未审核 非0 审核失败

	private String input_inst_id;

	private String order_qry_status;// 0正常，1退单，2外呼,3异常
	private String order_qry_status_c;

	private String goodsname;// 商品名称
	private String phone_owner_name;// 入网人姓名
	private String reference_phone;// 手机
	private String data_from_c;
	private String data_from;// 数据来源

	private String dcmodeOrderStatus_id;// 订单状态环节
	private String dcmodeOrderStatus_id_c;
	private String dcmodeGoodsType_id;// 商品类型
	private String dcmodeGoodsType_id_c;
	private String dcmodeActivityType_id;// 活动类型
	private String dcmodeActivityType_id_c;
	private String ship_mobile;// 收货人联系电话

	private String certi_type;// 证件类型
	private String cert_card_num;// 证件号码

	private String intent_order_id;// 意向单号
	private String order_county_code;// 行政县分
	private String order_county_code_c;
	
	private String report_search_type;//意向单报表查询方式 1--地市，2--县分

	private String status_c;
	
	private String if_Send_Photos; //证件上传类型
	private String if_Send_Photos_Name; 
	
	
	private String sms_id; 
	private String sms_level; 
	private String is_delete; 
	private String sms_template; 
	
	private String is_work_custom;//是否自定义流程 1=自定义  add by cqq 20181128
	
	private String permission_level;//权限层级：1-省，2-地市，3-县分
	private String top_seed_professional_line; //顶级种子专业线
	private String top_seed_type;  //顶级种子类型
	private String top_seed_group_id;  //顶级种子类型
	private String top_seed_professional_line_c;
	private String top_seed_type_c;
	private String top_seed_group_id_c; 
	private String share_svc_num; //用户种子号码
	private String out_call; //是否是外呼
	
	
	//private String  order_id;
	private String  pspt_id;//证件号码
	private String  contact_name;//联系人名称
	private String  contact_phone;//联系人号码
	//private String  num_city;//地市
	private String  serial_number;//业务号码
	private String  create_date;//下单时间
	//private String  order_type;//订单类型
	//private String done_status;//处理状态
	private String  done_time_start;//处理时间
	private String  done_time_end;//处理时间
	
		
	//短信异步
	private String service_num;//接收号码
	private String bill_num;//发送号码
	private String sms_data;//发送内容
	private String sms_flag;//0：短内容 1：长内容
	private String sms_type;//短信类型（sms本网，cmc异网）
	private String send_status;//发送状态（1：已发送，0：未发送）
		

	public String getService_num() {
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getBill_num() {
		return bill_num;
	}

	public void setBill_num(String bill_num) {
		this.bill_num = bill_num;
	}

	public String getSms_data() {
		return sms_data;
	}

	public void setSms_data(String sms_data) {
		this.sms_data = sms_data;
	}

	public String getSms_flag() {
		return sms_flag;
	}

	public void setSms_flag(String sms_flag) {
		this.sms_flag = sms_flag;
	}

	public String getSms_type() {
		return sms_type;
	}

	public void setSms_type(String sms_type) {
		this.sms_type = sms_type;
	}

	public String getSend_status() {
		return send_status;
	}

	public void setSend_status(String send_status) {
		this.send_status = send_status;
	}

	public String getOut_call() {
		return out_call;
	}
	
	public void setOut_call(String out_call) {
		this.out_call = out_call;
	}

	public String getPspt_id() {
		return pspt_id;
	}

	public void setPspt_id(String pspt_id) {
		this.pspt_id = pspt_id;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
	public String getCreate_date() {
		return create_date;
	}

		

	public String getDone_time_start() {
		return done_time_start;
	}

	public void setDone_time_start(String done_time_start) {
		this.done_time_start = done_time_start;
	}
		
		

	public String getDone_time_end() {
		return done_time_end;
	}

	public void setDone_time_end(String done_time_end) {
		this.done_time_end = done_time_end;
	}

		


	public String getTop_seed_group_id() {
		return top_seed_group_id;
	}

	public void setTop_seed_group_id(String top_seed_group_id) {
		this.top_seed_group_id = top_seed_group_id;
	}

	public String getTop_seed_group_id_c() {
		return top_seed_group_id_c;
	}

	public void setTop_seed_group_id_c(String top_seed_group_id_c) {
		this.top_seed_group_id_c = top_seed_group_id_c;
	}

	public String getTop_seed_professional_line() {
		return top_seed_professional_line;
	}

	public void setTop_seed_professional_line(String top_seed_professional_line) {
		this.top_seed_professional_line = top_seed_professional_line;
	}

	public String getTop_seed_type() {
		return top_seed_type;
	}

	public void setTop_seed_type(String top_seed_type) {
		this.top_seed_type = top_seed_type;
	}
	

	public String getTop_seed_professional_line_c() {
		return top_seed_professional_line_c;
	}

	public void setTop_seed_professional_line_c(String top_seed_professional_line_c) {
		this.top_seed_professional_line_c = top_seed_professional_line_c;
	}

	public String getTop_seed_type_c() {
		return top_seed_type_c;
	}

	public void setTop_seed_type_c(String top_seed_type_c) {
		this.top_seed_type_c = top_seed_type_c;
	}

	public String getStatus_c() {
		return status_c;
	}

	public void setStatus_c(String status_c) {
		this.status_c = status_c;
	}

	public String getInput_inst_id() {
		return input_inst_id;
	}

	public void setInput_inst_id(String input_inst_id) {
		this.input_inst_id = input_inst_id;
	}

	public String getCerti_type() {
		return certi_type;
	}

	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}

	public String getCert_card_num() {
		return cert_card_num;
	}

	public void setCert_card_num(String cert_card_num) {
		this.cert_card_num = cert_card_num;
	}

	public String getOrder_src() {
		return order_src;
	}

	public void setOrder_src(String order_src) {
		this.order_src = order_src;
	}

	public String getData_src() {
		return data_src;
	}

	public void setData_src(String data_src) {
		this.data_src = data_src;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getSubs_svc_number() {
		return subs_svc_number;
	}

	public void setSubs_svc_number(String subs_svc_number) {
		this.subs_svc_number = subs_svc_number;
	}

	public String getExpress_type() {
		return express_type;
	}

	public void setExpress_type(String express_type) {
		this.express_type = express_type;
	}

	public String getBss_refund_status() {
		return bss_refund_status;
	}

	public void setBss_refund_status(String bss_refund_status) {
		this.bss_refund_status = bss_refund_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public boolean isMemberQuery() {
		return memberQuery;
	}

	public void setMemberQuery(boolean memberQuery) {
		this.memberQuery = memberQuery;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getItem_from() {
		return item_from;
	}

	public void setItem_from(String item_from) {
		this.item_from = item_from;
	}

	public String getPay_start() {
		return pay_start;
	}

	public void setPay_start(String pay_start) {
		this.pay_start = pay_start;
	}

	public String getPay_end() {
		return pay_end;
	}

	public void setPay_end(String pay_end) {
		this.pay_end = pay_end;
	}

	public String getOrder_city_code() {
		return order_city_code;
	}

	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}

	public String getOut_tid() {
		return out_tid;
	}

	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}

	public String getOrder_channel() {
		return order_channel;
	}

	public void setOrder_channel(String order_channel) {
		this.order_channel = order_channel;
	}

	public String getModel_code() {
		return model_code;
	}

	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}

	// public String getMember_name() {
	// return member_name;
	// }
	// public void setMember_name(String member_name) {
	// this.member_name = member_name;
	// }
	public String getDeal_type() {
		return deal_type;
	}

	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}

	public String getQuick_shipping_company() {
		return quick_shipping_company;
	}

	public void setQuick_shipping_company(String quick_shipping_company) {
		this.quick_shipping_company = quick_shipping_company;
	}

	public String getQuick_shipping_status() {
		return quick_shipping_status;
	}

	public void setQuick_shipping_status(String quick_shipping_status) {
		this.quick_shipping_status = quick_shipping_status;
	}

	public String getCreate_start() {
		return create_start;
	}

	public void setCreate_start(String create_start) {
		this.create_start = create_start;
	}

	public String getCreate_end() {
		return create_end;
	}

	public void setCreate_end(String create_end) {
		this.create_end = create_end;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getIs_exception() {
		return is_exception;
	}

	public void setIs_exception(String is_exception) {
		this.is_exception = is_exception;
	}

	public String getGoods_pagekage_type() {
		return goods_pagekage_type;
	}

	public void setGoods_pagekage_type(String goods_pagekage_type) {
		this.goods_pagekage_type = goods_pagekage_type;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

	public String getOrder_from_c() {
		return order_from_c;
	}

	public void setOrder_from_c(String order_from_c) {
		this.order_from_c = order_from_c;
	}

	public String getOrder_city_code_c() {
		return order_city_code_c;
	}

	public void setOrder_city_code_c(String order_city_code_c) {
		this.order_city_code_c = order_city_code_c;
	}

	public String getShipping_id_c() {
		return shipping_id_c;
	}

	public void setShipping_id_c(String shipping_id_c) {
		this.shipping_id_c = shipping_id_c;
	}

	public String getOrder_channel_c() {
		return order_channel_c;
	}

	public void setOrder_channel_c(String order_channel_c) {
		this.order_channel_c = order_channel_c;
	}

	public String getPayment_id_c() {
		return payment_id_c;
	}

	public void setPayment_id_c(String payment_id_c) {
		this.payment_id_c = payment_id_c;
	}

	public String getException_type() {
		return exception_type;
	}

	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}

	public int getVisible_status() {
		return visible_status;
	}

	public void setVisible_status(int visible_status) {
		this.visible_status = visible_status;
	}

	public String getException_code() {
		return exception_code;
	}

	public void setException_code(String exception_code) {
		this.exception_code = exception_code;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getNot_flow_model_code() {
		return not_flow_model_code;
	}

	public void setNot_flow_model_code(String not_flow_model_code) {
		this.not_flow_model_code = not_flow_model_code;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	public String getRefund_deal_type() {
		return refund_deal_type;
	}

	public void setRefund_deal_type(String refund_deal_type) {
		this.refund_deal_type = refund_deal_type;
	}

	public String getOrder_supply_status() {
		return order_supply_status;
	}

	public void setOrder_supply_status(String order_supply_status) {
		this.order_supply_status = order_supply_status;
	}

	public String getOrder_is_his() {
		return order_is_his;
	}

	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}

	public String getLock_user_id() {
		return lock_user_id;
	}

	public void setLock_user_id(String lock_user_id) {
		this.lock_user_id = lock_user_id;
	}

	public String getQuery_btn_flag() {
		return query_btn_flag;
	}

	public void setQuery_btn_flag(String query_btn_flag) {
		this.query_btn_flag = query_btn_flag;
	}

	public String getOrder_model() {
		return order_model;
	}

	public void setOrder_model(String order_model) {
		this.order_model = order_model;
	}

	public String getWms_refund_status() {
		return wms_refund_status;
	}

	public void setWms_refund_status(String wms_refund_status) {
		this.wms_refund_status = wms_refund_status;
	}

	public String getFlow_id_c() {
		return flow_id_c;
	}

	public void setFlow_id_c(String flow_id_c) {
		this.flow_id_c = flow_id_c;
	}

	public boolean isRetOrderTree() {
		return retOrderTree;
	}

	public void setRetOrderTree(boolean retOrderTree) {
		this.retOrderTree = retOrderTree;
	}

	/**
	 * @return the ship_status
	 */
	public int getShip_status() {
		return ship_status;
	}

	/**
	 * @param ship_status
	 *            the ship_status to set
	 */
	public void setShip_status(int ship_status) {
		this.ship_status = ship_status;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getIsProxy() {
		return isProxy;
	}

	public void setIsProxy(String isProxy) {
		this.isProxy = isProxy;
	}
	// public String getBssStatus() {
	// return bssStatus;
	// }
	// public void setBssStatus(String bssStatus) {
	// this.bssStatus = bssStatus;
	// }

	public String getReceipt_status() {
		return receipt_status;
	}

	public void setReceipt_status(String receipt_status) {
		this.receipt_status = receipt_status;
	}

	public String getSign_status() {
		return sign_status;
	}

	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	// public String getIs_zhwj() {
	// return is_zhwj;
	// }
	// public void setIs_zhwj(String is_zhwj) {
	// this.is_zhwj = is_zhwj;
	// }
	public String getGoods_name() {
		if (goods_name != null) {
			goods_name = goods_name.trim();
		}
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getAbnormal_type() {
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getException_desc() {
		return exception_desc;
	}

	public void setException_desc(String exception_desc) {
		this.exception_desc = exception_desc;
	}

	public String getAuto_abnormal_type() {
		return auto_abnormal_type;
	}

	public void setAuto_abnormal_type(String auto_abnormal_type) {
		this.auto_abnormal_type = auto_abnormal_type;
	}

	public String getAuto_exception_desc() {
		return auto_exception_desc;
	}

	public void setAuto_exception_desc(String auto_exception_desc) {
		this.auto_exception_desc = auto_exception_desc;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
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

	// public String getItemsBssStatus() {
	// return itemsBssStatus;
	// }
	// public void setItemsBssStatus(String itemsBssStatus) {
	// this.itemsBssStatus = itemsBssStatus;
	// }
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProkey() {
		return prokey;
	}

	public void setProkey(String prokey) {
		this.prokey = prokey;
	}

	public String getWm_isreservation_from() {
		return wm_isreservation_from;
	}

	public void setWm_isreservation_from(String wm_isreservation_from) {
		this.wm_isreservation_from = wm_isreservation_from;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public String getOrder_type_c() {
		return order_type_c;
	}

	public void setOrder_type_c(String order_type_c) {
		this.order_type_c = order_type_c;
	}

	public String getIs_old() {
		return is_old;
	}

	public void setIs_old(String is_old) {
		this.is_old = is_old;
	}

	public String getOrder_model_c() {
		return order_model_c;
	}

	public void setOrder_model_c(String order_model_c) {
		this.order_model_c = order_model_c;
	}

	public String getExists_business_to_deal_with() {
		return exists_business_to_deal_with;
	}

	public void setExists_business_to_deal_with(String exists_business_to_deal_with) {
		this.exists_business_to_deal_with = exists_business_to_deal_with;
	}

	public String getException_code_c() {
		return exception_code_c;
	}

	public void setException_code_c(String exception_code_c) {
		this.exception_code_c = exception_code_c;
	}

	public String getBss_time_type() {
		return bss_time_type;
	}

	public void setBss_time_type(String bss_time_type) {
		this.bss_time_type = bss_time_type;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getOrder_sn() {
		return order_sn;
	}

	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	
	public String getLock_status() {
		return lock_status;
	}

	public void setLock_status(String lock_status) {
		this.lock_status = lock_status;
	}

	public String getActive_type() {
		return active_type;
	}

	public void setActive_type(String active_type) {
		this.active_type = active_type;
	}

	public String getActive_flag() {
		return active_flag;
	}

	public void setActive_flag(String active_flag) {
		this.active_flag = active_flag;
	}

	public String getAccount_vali() {
		return account_vali;
	}

	public void setAccount_vali(String account_vali) {
		this.account_vali = account_vali;
	}

	public String getLater_active_flag() {
		return later_active_flag;
	}

	public void setLater_active_flag(String later_active_flag) {
		this.later_active_flag = later_active_flag;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getMachine_id() {
		return machine_id;
	}

	public void setMachine_id(String machine_id) {
		this.machine_id = machine_id;
	}

	public String getBat_id() {
		return bat_id;
	}

	public void setBat_id(String bat_id) {
		this.bat_id = bat_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_id_c() {
		return goods_id_c;
	}

	public void setGoods_id_c(String goods_id_c) {
		this.goods_id_c = goods_id_c;
	}

	public String getNewOrderList() {
		return newOrderList;
	}

	public void setNewOrderList(String newOrderList) {
		this.newOrderList = newOrderList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHandle_status() {
		return handle_status;
	}

	public void setHandle_status(String handle_status) {
		this.handle_status = handle_status;
	}

	public String getOrder_qry_status() {
		return order_qry_status;
	}

	public void setOrder_qry_status(String order_qry_status) {
		this.order_qry_status = order_qry_status;
	}

	public String getOrder_qry_status_c() {
		return order_qry_status_c;
	}

	public void setOrder_qry_status_c(String order_qry_status_c) {
		this.order_qry_status_c = order_qry_status_c;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getPhone_owner_name() {
		return phone_owner_name;
	}

	public void setPhone_owner_name(String phone_owner_name) {
		this.phone_owner_name = phone_owner_name;
	}

	public String getReference_phone() {
		return reference_phone;
	}

	public void setReference_phone(String reference_phone) {
		this.reference_phone = reference_phone;
	}

	public String getDcmodeOrderStatus_id() {
		return dcmodeOrderStatus_id;
	}

	public void setDcmodeOrderStatus_id(String dcmodeOrderStatus_id) {
		this.dcmodeOrderStatus_id = dcmodeOrderStatus_id;
	}

	public String getDcmodeOrderStatus_id_c() {
		return dcmodeOrderStatus_id_c;
	}

	public void setDcmodeOrderStatus_id_c(String dcmodeOrderStatus_id_c) {
		this.dcmodeOrderStatus_id_c = dcmodeOrderStatus_id_c;
	}

	public String getDcmodeGoodsType_id() {
		return dcmodeGoodsType_id;
	}

	public void setDcmodeGoodsType_id(String dcmodeGoodsType_id) {
		this.dcmodeGoodsType_id = dcmodeGoodsType_id;
	}

	public String getDcmodeGoodsType_id_c() {
		return dcmodeGoodsType_id_c;
	}

	public void setDcmodeGoodsType_id_c(String dcmodeGoodsType_id_c) {
		this.dcmodeGoodsType_id_c = dcmodeGoodsType_id_c;
	}

	public String getDcmodeActivityType_id() {
		return dcmodeActivityType_id;
	}

	public void setDcmodeActivityType_id(String dcmodeActivityType_id) {
		this.dcmodeActivityType_id = dcmodeActivityType_id;
	}

	public String getDcmodeActivityType_id_c() {
		return dcmodeActivityType_id_c;
	}

	public void setDcmodeActivityType_id_c(String dcmodeActivityType_id_c) {
		this.dcmodeActivityType_id_c = dcmodeActivityType_id_c;
	}

	public String getShip_mobile() {
		return ship_mobile;
	}

	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}

	public String getData_from_c() {
		return data_from_c;
	}

	public void setData_from_c(String data_from_c) {
		this.data_from_c = data_from_c;
	}

	public String getData_from() {
		return data_from;
	}

	public void setData_from(String data_from) {
		this.data_from = data_from;
	}

	public String getOutcall_type() {
		return outcall_type;
	}

	public void setOutcall_type(String outcall_type) {
		this.outcall_type = outcall_type;
	}

	public String getOutcall_type_c() {
		return outcall_type_c;
	}

	public void setOutcall_type_c(String outcall_type_c) {
		this.outcall_type_c = outcall_type_c;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getIntent_order_id() {
		return intent_order_id;
	}

	public void setIntent_order_id(String intent_order_id) {
		this.intent_order_id = intent_order_id;
	}

	public String getOrder_county_code() {
		return order_county_code;
	}

	public void setOrder_county_code(String order_county_code) {
		this.order_county_code = order_county_code;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getReport_search_type() {
		return report_search_type;
	}

	public void setReport_search_type(String report_search_type) {
		this.report_search_type = report_search_type;
	}

	public String getIf_Send_Photos() {
		return if_Send_Photos;
	}

	public void setIf_Send_Photos(String if_Send_Photos) {
		this.if_Send_Photos = if_Send_Photos;
	}

	public String getIf_Send_Photos_Name() {
		return if_Send_Photos_Name;
	}

	public void setIf_Send_Photos_Name(String if_Send_Photos_Name) {
		this.if_Send_Photos_Name = if_Send_Photos_Name;
	}
	
	
	public String getOrder_county_code_c() {
		return order_county_code_c;
	}

	public void setOrder_county_code_c(String order_county_code_c) {
		this.order_county_code_c = order_county_code_c;
	}

	public String getSms_id() {
		return sms_id;
	}

	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}

	public String getSms_level() {
		if(StringUtils.isEmpty(sms_level)){
			sms_level = "0";
		}
		return sms_level;
	}

	public void setSms_level(String sms_level) {
		this.sms_level = sms_level;
	}

	public String getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}

	public String getSms_template() {
		return sms_template;
	}

	public void setSms_template(String sms_template) {
		this.sms_template = sms_template;
	}

	public String getIs_work_custom() {
		return is_work_custom;
	}

	public void setIs_work_custom(String is_work_custom) {
		this.is_work_custom = is_work_custom;
	}

	public String getOrder_role() {
		return order_role;
	}

	public void setOrder_role(String order_role) {
		this.order_role = order_role;
	}

	public String getPermission_level() {
		return permission_level;
	}

	public void setPermission_level(String permission_level) {
		this.permission_level = permission_level;
	}
	
	public String getShare_svc_num() {
		return share_svc_num;
	}

	public void setShare_svc_num(String share_svc_num) {
		this.share_svc_num = share_svc_num;
	}

}
