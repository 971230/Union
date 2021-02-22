package com.ztesoft.net.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.utils.AttrUtils;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class AutoOrderTree implements Serializable {
	private static Logger logger = Logger.getLogger(AutoOrderTree.class);
	public String getLock_user_realname() {
		return lock_user_realname;
	}

	public void setLock_user_realname(String lock_user_realname) {
		this.lock_user_realname = lock_user_realname;
	}

	private OrderTreeBusiRequest orderTree;
	
	private String phone_num;
	private String terminal;
	private String goods_package;
	private String bss_status;//BSS业务办理状态
	private String shipping_type;
	private String shipping_type_name;
	
	private String order_from_c;
	
	private String lock_clazz;
	
	private String ess_pre_desc;
	private String bss_pre_desc;
	private String bss_time_type;
	
	private String refund_status;
	private String bss_refund_status;
	
	private String oper_remark;//外呼原因
	private String outcall_type_c;//外呼类型
	
	private String city_name; //城市
	private String goods_type_name;//商品类型名称
	private String goods_name;//商品名称
	private String offer_name;//套餐名称
	private String tid_time;//订单创建时间
	private String out_tid;//外系统订单
	private String pay_type_name;//支付类型 DIC_PAY_TYPE
	private String development_code;//发展人编码 
	private String development_name;//发展人名称 
	private String channel_mark;//推广渠道  DIC_CHANNEL_MARK
	private String exception_desc;//异常原因
	private String wms_refund_status;//WMS退单状态
	private int ship_status;//发货状态
	private String flow_trace; //订单环节
	private String currTraceId;//订单环节编码 为了区分并行环节
	private String invoice_num;//发票号码
	private String work_state;//工单状态
	private String work_operator;//工单处理人
	
	private String if_Send_Photos;//证件上传状态
	private String pay_type;//支付类型
	private String invoice_type_name;//发票类型
	private String invoice_print_type_name;//发票打印方式
	private String invoice_title;//发票抬头
	private String invoice_group_content_name;//发票内容
	//批量预处理添加 
		private String order_id;	//内部订单ID
		private String contact_addr;	//收货地址
		private String lock_user_id;	//锁单人id
		private String lock_status;	//锁单状态
		private String lock_user_name;	//锁单人
		private String lock_user_realname; //锁定人真实姓名
		private String ship_tel;	//收货电话
		private String ship_name;	//收件人
		//批量预处理添加

	private boolean hasDiscout;//是否有优惠信息
	
	private String zk_white_cart_type_name;//主卡卡类型
	private String zk_sim_type_name;//主卡成白卡
	private String archive_type;//归档类型：0:待归档、1:回单归档、2:未回单归档(没有回单环节)、3:作废单归档、4:积压归档
	private List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList;
	//订单发货页面展示
	private String receiver_mobile;//联系方式
	private String ship_addr;//收货地址
	private String pay_method;//支付方式
	private String specificatio_nname;//手机型号
	private String activity_name;//活动
	
	private String handle_flag; //批量处理成功失败标志 0 成功  非0 不成功
	private String cert_card_num; //证件号码
	private String cert_address;//证件地址
	private String goods_cat_id;
	
	/**
	 * 操作类型：00：订购；01：退订；02：变更
	 */
	private String optType;  
	
	public String getGoods_cat_id() {
		return goods_cat_id;
	}

	public void setGoods_cat_id(String goods_cat_id) {
		this.goods_cat_id = goods_cat_id;
	}

	public String getCurrTraceId() {
		return currTraceId;
	}

	public void setCurrTraceId(String currTraceId) {
		this.currTraceId = currTraceId;
	}

	public String getPay_type_name() {
		return pay_type_name;
	}

	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}

	public String getDevelopment_code() {
		if(orderTree!=null){
			development_code = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.DEVELOPMENT_CODE);
		}
		return development_code;
	}

	public void setDevelopment_code(String development_code) {
		this.development_code = development_code;
	}

	public String getDevelopment_name() {
		if(orderTree!=null){
			development_name = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.DEVELOPMENT_NAME);
		}
		return development_name;
	}

	public void setDevelopment_name(String development_name) {
		this.development_name = development_name;
	}

	public String getChannel_mark() {
		return channel_mark;
	}

	public void setChannel_mark(String channel_mark) {
		this.channel_mark = channel_mark;
	}

	public String getException_desc() {
		if(orderTree!=null){
			this.exception_desc =  orderTree.getOrderExtBusiRequest().getException_desc();
		}
		return exception_desc;
	}
	public void setException_desc(String exception_desc) {
		this.exception_desc = exception_desc;
	}

	public String getRefund_status() {
		if(orderTree!=null){
			String status = orderTree.getOrderExtBusiRequest().getRefund_status();
			if(EcsOrderConsts.REFUND_STATUS_00.equals(status)){
				refund_status = "正常单";
			}else if(EcsOrderConsts.REFUND_STATUS_01.equals(status)){
				refund_status = "退单确认";
			}else if(EcsOrderConsts.REFUND_STATUS_02.equals(status)){
//				refund_status = "BSS已返销";
				refund_status = "退单确认";
			}
			else if(EcsOrderConsts.REFUND_STATUS_03.equals(status)){
//				refund_status = "ESS已返销";
				refund_status = "退单确认";
			}
			else if(EcsOrderConsts.REFUND_STATUS_04.equals(status)){
//				refund_status = "已退款";
				refund_status = "已退单";
			}else if(EcsOrderConsts.REFUND_STATUS_05.equals(status)){
//				refund_status = "退单完成";
				refund_status = "已退单";
			}else if(EcsOrderConsts.REFUND_STATUS_06.equals(status)){
//				refund_status = "已归档";
				refund_status = "已退单";
			}else if(EcsOrderConsts.REFUND_STATUS_07.equals(status)){
//				refund_status = "退单结果已通知商城";
				refund_status = "退单确认";
			}else if(EcsOrderConsts.REFUND_STATUS_08.equals(status)){
				refund_status = "退单申请";
			}else{
				refund_status = "正常单";
			}
		}
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	public String getEss_pre_desc() {
		return ess_pre_desc;
	}

	public void setEss_pre_desc(String ess_pre_desc) {
		this.ess_pre_desc = ess_pre_desc;
	}

	public String getBss_pre_desc() {
		return bss_pre_desc;
	}

	public void setBss_pre_desc(String bss_pre_desc) {
		this.bss_pre_desc = bss_pre_desc;
	}

	public String getOrder_from_c() {
		return order_from_c;
	}

	public void setOrder_from_c(String order_from_c) {
		this.order_from_c = order_from_c;
	}

	//订单详细页面url
	private String action_url;

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public String getAction_url() {
		return action_url;
	}

	public void setAction_url(String action_url) {
		this.action_url = action_url;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getGoods_package() {
		return goods_package;
	}

	public void setGoods_package(String goods_package) {
		this.goods_package = goods_package;
	}

	public String getShipping_type() {
		return shipping_type;
	}

	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
	}
	
	public String getShipping_type_name() {
		return shipping_type_name;
	}

	public void setShipping_type_name(String shipping_type_name) {
		this.shipping_type_name = shipping_type_name;
	}

	public String getLock_clazz() {
		return lock_clazz;
	}

	public void setLock_clazz(String lock_clazz) {
		this.lock_clazz = lock_clazz;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getGoods_type_name() {
		return goods_type_name;
	}

	public void setGoods_type_name(String goods_type_name) {
		this.goods_type_name = goods_type_name;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getOffer_name() {
		return offer_name;
	}

	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}

	public String getTid_time() {
		return tid_time;
	}

	public void setTid_time(String tid_time) {
		this.tid_time = tid_time;
	}

	public String getOut_tid() {
		return out_tid;
	}

	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}

	public String getBss_status() {
		return bss_status;
	}

	public void setBss_status(String bss_status) {
		this.bss_status = bss_status;
	}

	public String getWms_refund_status() {
		return wms_refund_status;
	}

	public void setWms_refund_status(String wms_refund_status) {
		this.wms_refund_status = wms_refund_status;
	}

	public int getShip_status() {
		return ship_status;
	}

	public void setShip_status(int ship_status) {
		this.ship_status = ship_status;
	}

	public String getFlow_trace() {
		return flow_trace;
	}

	public void setFlow_trace(String flow_trace) {
		this.flow_trace = flow_trace;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public String getBss_refund_status() {
		if(orderTree!=null){
			logger.info("退款状态跟踪====R10====order_id："+orderTree.getOrder_id()+",getAttrFieldValue："+CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.BSS_REFUND_STATUS));
			logger.info("退款状态跟踪====R10====order_id："+orderTree.getOrder_id()+",getAttrFieldValueHis："+CommonDataFactory.getInstance().getAttrFieldValueHis(orderTree.getOrder_id(), AttrConsts.BSS_REFUND_STATUS));
		 	
			String bssrefundstatus = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.BSS_REFUND_STATUS);
			if(StringUtils.isEmpty(bssrefundstatus)){
				bssrefundstatus = CommonDataFactory.getInstance().getAttrFieldValueHis(orderTree.getOrder_id(), AttrConsts.BSS_REFUND_STATUS);
			}
			bssrefundstatus = StringUtils.isEmpty(bssrefundstatus)?"0":bssrefundstatus;
            bss_refund_status = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_BSS_REFUND_STATUS, bssrefundstatus); 			
		}
		return bss_refund_status;
	}

	public void setBss_refund_status(String bss_refund_status) {
		this.bss_refund_status = bss_refund_status;
	}

	public String getIf_Send_Photos() {	    
		String bssrefundstatus = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.CERT_UPLOAD_STATUS);
		if_Send_Photos = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_CERT_UPLOAD_STATUS, bssrefundstatus); 	
		return if_Send_Photos;
	}

	public void setIf_Send_Photos(String if_Send_Photos) {
		this.if_Send_Photos = if_Send_Photos;
	}

	public String getPay_type() {
		pay_type = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.PAY_TYPE);
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getInvoice_title() {
		invoice_title = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.RESERVE8);
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public String getInvoice_type_name() {
		String invoice_type = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.INVOICE_TYPE);
		invoice_type_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.INVOICE_TYPE, invoice_type); 
		return invoice_type_name;
	}

	public void setInvoice_type_name(String invoice_type_name) {
		this.invoice_type_name = invoice_type_name;
	}

	public String getInvoice_print_type_name() {
		String invoice_print_type = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.INVOICE_PRINT_TYPE);
		invoice_print_type_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.INVOICE_PRINT_TYPE, invoice_print_type); 
		return invoice_print_type_name;
	}

	public void setInvoice_print_type_name(String invoice_print_type_name) {
		this.invoice_print_type_name = invoice_print_type_name;
	}

	public String getInvoice_group_content_name() {
		String invoice_group_content = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.INVOICE_GROUP_CONTENT);
		invoice_group_content_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_INVOICE_GROUP_CONTENT, invoice_group_content); 
		return invoice_group_content_name;
	}

	public void setInvoice_group_content_name(String invoice_group_content_name) {
		this.invoice_group_content_name = invoice_group_content_name;
	}

	public boolean isHasDiscout() {
		hasDiscout = false;
		if(orderTree.getDiscountInfoBusiRequests().size()>0){
			hasDiscout = true;
		}
		return hasDiscout;
	}

	public void setHasDiscout(boolean hasDiscout) {
		this.hasDiscout = hasDiscout;
	}

	public List<OrderPhoneInfoFukaBusiRequest> getOrderPhoneInfoFukaList() {
		List<OrderPhoneInfoFukaBusiRequest> oldList = orderTree.getOrderPhoneInfoFukaBusiRequests();
		List<OrderPhoneInfoFukaBusiRequest> rsList = new ArrayList<OrderPhoneInfoFukaBusiRequest>();
		String userTypeName = "";
		String whiteCardTypeName = "";
		for(OrderPhoneInfoFukaBusiRequest vo : oldList){
			userTypeName = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_USER_TYPE, vo.getUserType()); 
			whiteCardTypeName  = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.CARD_TYPE, vo.getCardType()); 
			vo.setUserType(userTypeName);
			vo.setCardType(whiteCardTypeName);
			if(!StringUtils.equals(vo.getPhoneNum(), "0"))rsList.add(vo);
		}
		return rsList;
	}

	public void setOrderPhoneInfoFukaList(List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList) {
		this.orderPhoneInfoFukaList = orderPhoneInfoFukaList;
	}

	public String getZk_white_cart_type_name() {
		String zk_white_cart_type = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.WHITE_CART_TYPE);
		zk_white_cart_type_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.CHENG_CARD_SKU, zk_white_cart_type); 
		return zk_white_cart_type_name;
	}

	public void setZk_white_cart_type_name(String zk_white_cart_type_name) {
		this.zk_white_cart_type_name = zk_white_cart_type_name;
	}

	public String getZk_sim_type_name() {
		String zk_sim_type = CommonDataFactory.getInstance().getAttrFieldValue(orderTree.getOrder_id(), AttrConsts.SIM_TYPE);
		zk_sim_type_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.CARD_TYPE, zk_sim_type); 
		return zk_sim_type_name;
	}

	public void setZk_sim_type_name(String zk_sim_type_name) {
		this.zk_sim_type_name = zk_sim_type_name;
	}

	public String getArchive_type() {
		return archive_type;
	}

	public void setArchive_type(String archive_type) {
		this.archive_type = archive_type;
	}


	public String getBss_time_type() {
		return bss_time_type;
	}

	public void setBss_time_type(String bss_time_type) {
		this.bss_time_type = bss_time_type;
	}


	public String getOper_remark() {
		return oper_remark;
	}

	public void setOper_remark(String oper_remark) {
		this.oper_remark = oper_remark;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getContact_addr() {
		return contact_addr;
	}

	public void setContact_addr(String contact_addr) {
		this.contact_addr = contact_addr;
	}

	public String getLock_user_id() {
		return lock_user_id;
	}

	public void setLock_user_id(String lock_user_id) {
		this.lock_user_id = lock_user_id;
	}

	public String getLock_status() {
		return lock_status;
	}

	public void setLock_status(String lock_status) {
		this.lock_status = lock_status;
	}

	public String getLock_user_name() {
		return lock_user_name;
	}

	public void setLock_user_name(String lock_user_name) {
		this.lock_user_name = lock_user_name;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public String getHandle_flag() {
		return handle_flag;
	}

	public void setHandle_flag(String handle_flag) {
		this.handle_flag = handle_flag;
	}
	
	public String getReceiver_mobile() {
		return receiver_mobile;
	}

	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getSpecificatio_nname() {
		return specificatio_nname;
	}

	public void setSpecificatio_nname(String specificatio_nname) {
		this.specificatio_nname = specificatio_nname;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public String getCert_card_num() {
		return cert_card_num;
	}

	public void setCert_card_num(String cert_card_num) {
		this.cert_card_num = cert_card_num;
	}

	public String getCert_address() {
		return cert_address;
	}

	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}

	public String getOutcall_type_c() {
		return outcall_type_c;
	}

	public void setOutcall_type_c(String outcall_type_c) {
		this.outcall_type_c = outcall_type_c;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getWork_state() {
		return work_state;
	}

	public void setWork_state(String work_state) {
		this.work_state = work_state;
	}

	public String getWork_operator() {
		return work_operator;
	}

	public void setWork_operator(String work_operator) {
		this.work_operator = work_operator;
	}
	
}
