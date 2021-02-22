package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.SupplierCertificate;

public interface ISupplierCertificateManager {

	public void add(SupplierCertificate supplierCertificate,String state);
	
	public void dataDeal(String certificate_number,String certificate_name, String certificate_period_validity, String certificate_url, String license_issuing_organs, String supplier_id);

	public List list(String supplier_id);
	
	public List findCertificateByUserId(String currUserId);
	
	public SupplierCertificate findCertificateById(String certificate_id);
	
	public List<SupplierCertificate> findCertificateBySupplierId(String suplierid);
	
	public int delete(String certificate_id);
	
	public void edit(SupplierCertificate supplierCertificate,String supplier_state);
}
