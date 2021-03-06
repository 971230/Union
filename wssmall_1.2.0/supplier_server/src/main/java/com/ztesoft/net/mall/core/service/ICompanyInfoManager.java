package com.ztesoft.net.mall.core.service;

import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.CompanyInfo;

public interface ICompanyInfoManager {

	public void add(CompanyInfo companyInfo);
	
	public CompanyInfo findCompanyInfoBySupplierId(String supplierId);
	
	public Page fineCompanyByName(String companyName,int pageNo, int pageSize);
	
	public void dataDeal(String company_name, String english_name, String ticket_type, int enterprise_type, String abbreviation, String website, int is_abroad, String register_address, String register_date, String legal_name, String legal_id_card, String register_funds, int currency, String period_validity, String license_number, String license_url, String country_registr_number, String general_tax_url, String place_registr_number, String place_registr_url, String organ_code, String supplier_id);

	public String[] onFillCompanyInfoEditInput(Map companyInfo,String url);

	public void modifyCompany(String company_id, String company_name,
			String english_name, String ticket_type, int enterprise_type,
			String abbreviation, String website, int is_abroad,
			String register_address, String register_date, String legal_name,
			String legal_id_card, String register_funds, int currency,
			String period_validity, String license_number, String license_url,
			String country_registr_number, String general_tax_url,
			String place_registr_number, String place_registr_url,
			String organ_code);
	
	public void edit(CompanyInfo companyInfo,String supplier_state);
	
	public String getCompanyInfoSequence();
}
