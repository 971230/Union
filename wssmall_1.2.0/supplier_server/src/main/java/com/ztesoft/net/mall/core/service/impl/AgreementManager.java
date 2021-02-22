package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.mall.core.model.AllAgreement;
import com.ztesoft.net.mall.core.service.IAgreementManager;

import java.io.File;

public class AgreementManager extends BaseSupport implements IAgreementManager {

	@Override
	public void insertAgreement(AllAgreement supplierAgreement,File file_url) {
		String agt_id =  this.baseDaoSupport.getSequences("SEQ_ES_AGREEMENT");
		 supplierAgreement.setAgt_id(agt_id);
		 supplierAgreement.setCreate_time(DBTUtil.current());
		 supplierAgreement.setState("1");
		 //String file_url = supplierAgreement.getFile_url();
	     String path = UploadUtil.uploadFiles(file_url, file_url.getName(), "supper", "supper");
	     supplierAgreement.setFile_url(path);
		 this.daoSupport.insert("es_agreement", supplierAgreement);
		
	}

	@Override
	public String uploadFile(String file_url) {
		File f = new File(file_url);
		String file_name = f.getName();
		String path = "";
		 path = UploadUtil.uploadFiles(f, file_name, "supper", "supper");
		 
		
		return path;
	}

	

}
