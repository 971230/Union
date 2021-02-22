package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.InvoiceApply;

import java.util.List;

/**
 * 索取发票管理
 * @author kingapex
 *
 */
public interface IInvoiceApplyManager {
	
	public void add(InvoiceApply invoiceApply);
	
	public void edit(InvoiceApply invoiceApply);
	
	public void updateState(Integer id,int state);
	
	public InvoiceApply get(Integer id);
	
	public void delete(Integer id);
	
	public Page list(int page ,int pageSize);
	
	public List listMember();
	
	public void refuse(Integer id,String reson);
}

