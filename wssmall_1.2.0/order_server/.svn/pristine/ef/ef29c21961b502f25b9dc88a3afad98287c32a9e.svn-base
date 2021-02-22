package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.WarehousePurorder;

/**
 * 采购单manager
 * @作者 MoChunrun
 * @创建日期 2013-11-11 
 * @版本 V 1.0
 */
public interface IWarehousePurorderMamager {

	/**
	 * 添加
	 * @作者 MoChunrun
	 * @创建日期 2013-11-11 
	 * @param warehousePurorder
	 */
	public void add(WarehousePurorder warehousePurorder);
	/**
	 * 修改采购单状态
	 * @作者 MoChunrun
	 * @创建日期 2013-11-11 
	 * @param order_id
	 * @param status
	 */
	public void updateAuditStatus(String order_id,String status);
	
	/*
	 * 查询
	 */
	public Page search(String store_action_type,String pru_order_id, String pru_order_name,String order_id,String company_name,String audit_status,String create_type, int page,
			int pageSize); 	
	/**
	 * 按批量号查找
	 * @作者 MoChunrun
	 * @创建日期 2013-11-12 
	 * @param batch_id
	 * @return
	 */
	public WarehousePurorder qryWarehousePurorderByBatchId(String batch_id);
	
	/*
	 * 根据订单名查看些订单是否存在
	 * 
	 */
	public boolean pruOrderNameIsExits(String pru_order_name); 
	
	/**
	 * 
	 * 根据订单编号查询
	 * @return
	 */
	public WarehousePurorder qryWarehousePurorderByOrderId(String orderId);
	
	/**
	 * 根据采购单编号查询
	 * @param pru_order_id
	 * @return
	 */
	public WarehousePurorder qryWarehousePurorderByPruOrderId(String pru_order_id);
	
}
