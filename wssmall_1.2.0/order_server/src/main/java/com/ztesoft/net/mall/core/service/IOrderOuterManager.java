package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderOuterQueryParam;

/**
 * 订单管理
 * 
 * @author wui 外系统订单处理类
 * 
 */
public interface IOrderOuterManager {
	//新列表
	public Page listn(int pageNO, int pageSize,int disabled, OrderOuterQueryParam ordParam, String order);
	
	//订单同步
	public String sysorder(String request);
	
	//根据订单id获取订单
	public OrderOuter getOrderByOrderId(String orderId);//
	
	public BatchResult importOrd(List inList,String service_name);
	
	public void insert(OrderOuter orderOuter);
	
	public List<OrderOuter>getListByBatchId(String batchId);
	
	public int isExistsOrder(List list);
	
	public String outOrderSync(String request);
	
	public void deleteOrder(String orderIds);
}
