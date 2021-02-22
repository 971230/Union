package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.SupplierAgent;

public interface ISupplierAgentManager {

	public void add(SupplierAgent supplierAgent,String state);

	//数据处理
	public void dataDeal(String name,String number,String attachment,String type,String supplier_id);
	
	public List list(String supplier_id);
	
	public List findAgentByUserId(String currUserId);
	
	public SupplierAgent findAgentById(String agent_id);
	
	public List<SupplierAgent> findAgentBySupplierId(String supplierid);
	
	public int delete(String agent_id);
	
	public void edit(SupplierAgent supplierAgent,String supplier_state);

}
