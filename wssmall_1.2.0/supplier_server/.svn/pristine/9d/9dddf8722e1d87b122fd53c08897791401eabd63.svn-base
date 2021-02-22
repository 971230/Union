package com.ztesoft.net.mall.core.service;

import java.util.HashMap;
import java.util.List;

import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.framework.database.Page;

public interface ISupplierManager {

	public List showEnterpriseType();

	public List isAbroad();

	public List showCurrency();

	public List showTicketType();

	public Page supplierList(String user_name,String phone, int page, int pageSize);
	
	public boolean isCompanyNameExits(String companyName);

	public boolean isAccountNumberExits(String companyName);
	
	public List<Supplier> getSupplierByCatId(String cat_id);
	

	public List<Supplier> findSupplieridByAccount_number(String number);

	public String getSupplierSequences();

	public List showAgentType();

	public String add(Supplier supplier);

	public String dataDeal(String supplier_id, String company_name,
			String parent_id, String email, String id_card, String other_phone,
			String password, String qq, String sex, String user_name,
			String account_number, String phone, String adminUserId,
			String supplier_type);

	public Page list(String company_name, String user_name, String phone,
			String state, String supplier_type, String is_administrator,String date_type,String remd,String suppler_id,
			int page, int pageSize);

	public Page list_check(String companyName, String username, String qq, String email,String beginTime,String endTime, int page, int pageSize);
	public int delete(String id);

	public Page auditlist(String company_name, String user_name,
			String supplier_type, String phone, int page, int pageSize);

	public Supplier getSupplierSequM1AndState0(String supplier_id);

	public Supplier getSupplierById(String id);

	public boolean isSaveExits(String supplierId, String supplierState);

	public int saveAuditSupplier(String supplier_id, String supplier_state);

	// 优质供货商
	public Page searchHotSupper(int page, int pageSize);

	public Supplier findSupplierIdBycurrUserId(String currUserId);

	public int cancellation(String id);

	public Page findExamineSupplier(int page, int pageSize, String username,
			String company_name);

	public void addMember(Supplier supplier);

	public Supplier findSupplierById(String supplier);

	/**
	 * 根据登录用户获取供货商
	 */
	public Supplier getSupplierByUserId(String userid);

	/*
	 * 修改供货商的信息
	 */
	public void edit(Supplier supplier);

	public void modifySupplier(String supplier_id, String company_name,
			String parent_id, String email, String id_card, String other_phone,
			String qq, String sex, String user_name, String account_number,
			String phone, String supplier_type);

	public void registerApply(String adminUserId, String supplier_id);

	public int findSupplierByUserName(String userName);

	public int waitAuditSupplier();

	public int waitAuditAgency();

	public int cooperationSupplier();

	public int endSupplier();

	public int todayNewSupplier();

	public int yestodayNewSupplier();

	public int todayNewAgency();

	public int yestodayNewAgency();
	
	public void RegisterApplyAudit(String supplier_id);
	
	public List listSupplierSalesRank();
	
	public boolean setSupplierState(String supplier_id);
	public List getSupplierByCond(HashMap params);
}
