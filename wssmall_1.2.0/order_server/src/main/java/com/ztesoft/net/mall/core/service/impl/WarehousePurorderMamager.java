package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IWarehousePurorderMamager;
import com.ztesoft.net.model.WarehousePurorder;
import com.ztesoft.net.sqls.SF;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 采购单manager
 * @作者 MoChunrun
 * @创建日期 2013-11-11 
 * @版本 V 1.0
 */
public class WarehousePurorderMamager extends BaseSupport implements
		IWarehousePurorderMamager {

	@Override
	public void add(WarehousePurorder warehousePurorder) {
		this.baseDaoSupport.insert("ES_WAREHOUSE_PURORDER", warehousePurorder);
	}

	@Override
	public void updateAuditStatus(String order_id, String status) {
		String sql = SF.orderSql("SERVICE_AUDIT_STATUS_UPDATE");
		this.baseDaoSupport.execute(sql, status,order_id);
	}

	@Override
	public Page search(String store_action_type,String house_name, String pru_order_name,String order_id,String company_name,String audit_status,String create_type, int page,
			int pageSize) {
		String sql= SF.orderSql("SERVICE_WH_PURORDER_SELECT");
		
		StringBuffer sqlAccount = new StringBuffer(" ");
		ArrayList partm = new ArrayList();
		if(StringUtils.isNotEmpty(house_name)){
			sqlAccount.append(" and w.house_name like?");
			partm.add("%"+house_name+"%");
		}
		
		if(StringUtils.isNotEmpty(pru_order_name)){
			sqlAccount.append(" and a.pru_order_name like?");
			partm.add("%"+pru_order_name+"%");
		}
		
		if(StringUtils.isNotEmpty(order_id)){
			sqlAccount.append(" and c.order_id like?");
			partm.add("%"+order_id+"%");
		}
		
		if(StringUtils.isNotEmpty(company_name)){
			sqlAccount.append(" and b.company_name like?");
			partm.add("%"+company_name+"%");
		}
		
		if(StringUtils.isNotEmpty(audit_status)){
			sqlAccount.append(" and a.audit_status = ?");
			partm.add(audit_status);
		}
		
		if(StringUtils.isNotEmpty(store_action_type)){
			sqlAccount.append(" and a.store_action_type =?");
			partm.add(store_action_type);
		}
		
		if(StringUtils.isNotEmpty(create_type)){
			sqlAccount.append(" and a.create_type =?");
			partm.add(create_type);
		}
		
		sqlAccount.append(" order by a.create_time desc");
		sql=sql.replaceAll("#cont", sqlAccount.toString());
		
		String countSql=SF.orderSql("SERVICE_WH_PURORDER_COUNT");
		countSql=countSql.replaceAll("#cont", sqlAccount.toString());
		
		return this.baseDaoSupport.queryForCPage(sql, page, pageSize, WarehousePurorder.class, countSql, partm.toArray());
	}

	@Override
	public WarehousePurorder qryWarehousePurorderByBatchId(String batch_id) {
		String sql = SF.orderSql("SERVICE_WH_P_SELECT_BY_BATCH");
		return (WarehousePurorder) this.baseDaoSupport.queryForObject(sql, WarehousePurorder.class, batch_id);
	}

	@Override
	public boolean pruOrderNameIsExits(String pru_order_name) {
		String sql = SF.orderSql("SERVICE_WH_P_SELECT_BY_NAME");
		List list = this.baseDaoSupport.queryForList(sql, pru_order_name);
		return list.size() > 0 ? true : false;
	}

	@Override
	public WarehousePurorder qryWarehousePurorderByOrderId(String orderId) {
		String sql = SF.orderSql("SERVICE_WH_P_SELECT_BY_BATCH");
		return (WarehousePurorder) this.baseDaoSupport.queryForObject(sql, WarehousePurorder.class, orderId);
	}
	
	@Override
	public WarehousePurorder qryWarehousePurorderByPruOrderId(String pru_order_id) {
		String sql = SF.orderSql("SERVICE_WH_P_SELECT_BY_ORDER");
		return (WarehousePurorder) this.baseDaoSupport.queryForObject(sql, WarehousePurorder.class, pru_order_id);
	}

}
