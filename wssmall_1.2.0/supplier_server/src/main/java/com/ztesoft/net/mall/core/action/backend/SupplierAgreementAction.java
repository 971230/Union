package com.ztesoft.net.mall.core.action.backend;

import java.io.File;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.AllAgreement;
import com.ztesoft.net.mall.core.service.impl.AgreementManager;

public class SupplierAgreementAction extends WWAction{
	
	private AgreementManager supplierAgreementManager;
	private AllAgreement supplierAgreement;
	private String supplier_id;
	private File file_url;

    public String addSupplierAgreement(){
    	try{
    		this.supplierAgreement.setAgt_rel_id(supplier_id);//供货商ID
    		this.supplierAgreement.setAgt_rel_desc("供货商协议");
    		this.supplierAgreement.setAgt_rel_table("es_supplier");
			this.supplierAgreementManager.insertAgreement(this.supplierAgreement,this.file_url);
			this.json = "{result:0,message:'上传廉政协议成功'}";
			
		}catch(RuntimeException e){
			 this.json = "{result:0,message:\"上传廉政协议失败：" + e.getMessage() + "\"}";
			
		}
	  
		return WWAction.JSON_MESSAGE;
    }
    
    public String upload(){
    
    	return null;

    }
	public AgreementManager getSupplierAgreementManager() {
		return supplierAgreementManager;
	}
	public void setSupplierAgreementManager(
			AgreementManager supplierAgreementManager) {
		this.supplierAgreementManager = supplierAgreementManager;
	}
	public AllAgreement getSupplierAgreement() {
		return supplierAgreement;
	}
	public void setSupplierAgreement(AllAgreement supplierAgreement) {
		this.supplierAgreement = supplierAgreement;
	}

	public String getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(String supplier_id) {
		this.supplier_id = supplier_id;
	}

	public File getFile_url() {
		return file_url;
	}

	public void setFile_url(File file_url) {
		this.file_url = file_url;
	}
	
    
    
}
