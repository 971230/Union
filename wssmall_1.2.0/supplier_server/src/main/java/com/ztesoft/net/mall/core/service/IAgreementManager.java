package com.ztesoft.net.mall.core.service;

import java.io.File;

import com.ztesoft.net.mall.core.model.AllAgreement;

public interface IAgreementManager {
    public String uploadFile(String file_url);
    
    //public String download();
    
    public  void insertAgreement(AllAgreement supplierAgreement,File file_url);
}
