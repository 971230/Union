package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.SupplierStaff;

public interface ISupplierStaffManager {

	public void add(String account_number, String user_name, String sex,
			String password, String phone, String email,String supplier_id,String qq,String id_card);

	public List list(String supplier_id);

	public int delete(String staff_id);
	
	public SupplierStaff findSupplierStaffById(String staff_id);
	
	public void update(String account_number, String user_name, String sex,
			String password, String phone, String email, String staff_id,
			String qq, String id_card);
	
	public boolean isStaffAccountNumberExits(String accountNumber);
	
	public void bindingstaff(String staff_id,String adminUserId);
	
	public void unbundlingstaff(String staff_id);
}
