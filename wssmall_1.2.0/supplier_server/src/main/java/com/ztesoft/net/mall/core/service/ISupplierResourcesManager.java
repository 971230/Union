package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.SupplierResources;

public interface ISupplierResourcesManager {

	public void add(SupplierResources supplierResources,String state);
	
	public List<SupplierResources> findResourcesBySupplierId(String supplierId);
	
	public void dataDeal(String administration, String company_desc, String employees_number, String marketing_sales, String production, String research, String year, String support, String supplier_id);

	public List list(String supplier_id);
	
	public List findResourcesByUserId(String currUserId);
	
	public SupplierResources findResourcesById(String resources_id);
	
	public int delete(String resources_id);
	
	public void edit(SupplierResources supplierResources,String supplier_state);
}
