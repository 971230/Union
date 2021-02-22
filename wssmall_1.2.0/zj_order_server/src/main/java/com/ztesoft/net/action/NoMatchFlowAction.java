package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.AuditBusinessVO;
import com.ztesoft.net.service.INoMatchFlowManager;


/**
 * @Description 无匹配流水
 * @author  yanPengJun
 * @date    2016年10月18日
 * @version 1.0
 */


public class NoMatchFlowAction extends WWAction{
	@Resource
	private INoMatchFlowManager noMatchFlowManager;
	private String is_zero;
	private String bss_or_cbss;
	private String import_date; 
	private String includePage;
	private List<AuditBusinessVO> auditBusi;
	private String operation_time;
	private String serial_number;
	private String phone_number;
	private Float busi_money;
	private String business_type;
	private String operator;
	private String data_from;
	private String user_phone_num;
	private String order_id;
	private Float money;
	private String audit_note;
	
	public String queryNoMatchFlowList(){
		this.webpage = noMatchFlowManager.queryList(is_zero, bss_or_cbss, import_date, this.getPage(), this.getPageSize());
		return "no_match_flow_list";
		
	}
	
	public String openOrder(){
		assembleAuditBusi();
		user_phone_num = phone_number;
		money = busi_money;
		this.webpage = noMatchFlowManager.queryOrderListByPhoneNum(order_id,user_phone_num, this.getPage(), this.getPageSize());
		return "no_match_flow_order";
	}
	
	public String queryOrder(){
		assembleAuditBusi();
		this.webpage = noMatchFlowManager.queryOrderListById(order_id, user_phone_num , this.getPage(), this.getPageSize());
		return "no_match_flow_order";
		
	}
	 
	public void assembleAuditBusi(){
		AuditBusinessVO auditBusiness = new AuditBusinessVO();
		List<AuditBusinessVO> auditBusiList = new ArrayList<AuditBusinessVO>();
		auditBusiness.setOperation_time(operation_time.replace("@", " "));
		auditBusiness.setSerial_number(serial_number);
		auditBusiness.setData_from(data_from);
		auditBusiness.setPhone_number(phone_number);
		auditBusiness.setBusi_money(busi_money);
		auditBusiness.setBusiness_type(business_type);
		auditBusiness.setOperator(operator);
		auditBusiness.setData_from(data_from);
		auditBusiList.add(auditBusiness);
		auditBusi=auditBusiList;
	}

	public INoMatchFlowManager getNoMatchFlowManager() {
		return noMatchFlowManager;
	}

	public void setNoMatchFlowManager(INoMatchFlowManager noMatchFlowManager) {
		this.noMatchFlowManager = noMatchFlowManager;
	}

	public String addFlow(){
		 try{
			 Map result = noMatchFlowManager.updateFlow(order_id, audit_note,serial_number,"add" );
			 this.json = "{result:0,message:'操作成功'}";
		 }catch(Exception e){
			 e.printStackTrace();
			 String msg = e.getMessage();
			 this.json = "{result:0,message:'"+msg+"'}";
		 }
		
		return this.JSON_MESSAGE;
		
	}
	
	public String submitFlow(){
		 try{
			 Map result = noMatchFlowManager.updateFlow(order_id, audit_note,serial_number,"submit" );
			 this.json = "{result:0,message:'操作成功'}";
		 }catch(Exception e){
			 e.printStackTrace();
			 String msg = e.getMessage();
			 this.json = "{result:0,message:'"+msg+"'}";
		 }
		
		return this.JSON_MESSAGE;
		
	}
	public String getIs_zero() {
		return is_zero;
	}

	public void setIs_zero(String is_zero) {
		this.is_zero = is_zero;
	}

	public String getBss_or_cbss() {
		return bss_or_cbss;
	}

	public void setBss_or_cbss(String bss_or_cbss) {
		this.bss_or_cbss = bss_or_cbss;
	}

	public String getImport_date() {
		return import_date;
	}

	public void setImport_date(String import_date) {
		this.import_date = import_date;
	}

	public String getIncludePage() {
		return includePage;
	}

	public void setIncludePage(String includePage) {
		this.includePage = includePage;
	}

	public List<AuditBusinessVO> getAuditBusi() {
		return auditBusi;
	}

	public void setAuditBusi(List<AuditBusinessVO> auditBusi) {
		this.auditBusi = auditBusi;
	}

	public String getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(String operation_time) {
		this.operation_time = operation_time;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Float getBusi_money() {
		return busi_money;
	}

	public void setBusi_money(Float busi_money) {
		this.busi_money = busi_money;
	}

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getData_from() {
		return data_from;
	}

	public void setData_from(String data_from) {
		this.data_from = data_from;
	}

	public String getUser_phone_num() {
		return user_phone_num;
	}

	public void setUser_phone_num(String user_phone_num) {
		this.user_phone_num = user_phone_num;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public String getAudit_note() {
		return audit_note;
	}

	public void setAudit_note(String audit_note) {
		this.audit_note = audit_note;
	}

	
}
