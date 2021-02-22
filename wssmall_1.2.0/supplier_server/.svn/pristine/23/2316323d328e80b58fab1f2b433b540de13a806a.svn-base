package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.SupplierAccount;

public interface ISupplierAccountManager {

	public void add(SupplierAccount supplierAccount,String state);
	//数组数据处理
	public void dataDeal(String  open_bank,String address,String accoun_name,String bank_account,String accoun_attachment,String is_default,String supplier_id);
	
	public List list(String currUserId);
	
	public SupplierAccount findAccountById(String account_id);
	
	public List<SupplierAccount> findAccountBySupplierId(String supplier_id);
	
	public int delete(String staff_id);
	
	public void edit(SupplierAccount supplierAccount,String supplier_state);
}
