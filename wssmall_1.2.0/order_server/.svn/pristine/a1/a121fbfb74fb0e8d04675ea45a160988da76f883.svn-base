package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.OrderAudit;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;



/**
 * 合约机受理处理类
 * 
 * @author wui
 */
public interface IOrderAuditManager {
	
	public void audit(OrderAuditRequest orderAudit);
	
	public void apply(OrderAuditRequest orderAudit);
	
	
	public OrderAudit getLastAuditRecord(String order_id,String audit_type);
	
	public List listLogs(String orderId);
}
