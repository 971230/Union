package com.ztesoft.net.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;

import com.ztesoft.net.model.FieldVO;
import com.ztesoft.net.model.InvoiceFieldVO;
import com.ztesoft.net.model.InvoiceModeFieldParams;
import com.ztesoft.net.model.InvoiceModeFieldVO;
import com.ztesoft.net.model.InvoiceModeVO;
import com.ztesoft.net.model.ModeSelectFieldVO;

public interface IInvoicePrintManager {
	/**
	 * 模板查询
	 * @param invoice
	 * @param pageNo
	 * @param pageSize
	 */
	public Page search(InvoiceModeVO invoice,int pageNo, int pageSize) ;
	/**
	 * 模板字段查询
	 * @param invoiceModeVO
	 */
	public List templateInvoiceFieldInformation();
	/**
	 * 已选择的模板字段查询
	 * @param invoiceModeVO
	 */
	public List<FieldVO> templateFieldInformation(InvoiceModeVO invoiceModeVO);
	/**
	 * 模板信息查询
	 * @param invoiceModeVO
	 */
	public InvoiceModeVO templateInvoiceModeInformation(InvoiceModeVO invoiceModeVO);
	
	/**
	 * 模板字段修改
	 * @param invoiceModeFieldParams
	 */
	public void editTemplateInformation(List<InvoiceModeFieldVO> invoiceModeFieldVOList);
	
	/**
	 * 模板页面编辑
	 * @param invoiceModeFieldParams
	 */
	
	public ModeSelectFieldVO selectOne(InvoiceModeFieldParams invoiceModeFieldParams);
	/**
	 * 模板页面编辑
	 * @param invoiceModeFieldVO
	 */
	public void editFieldPosition(InvoiceModeFieldVO invoiceModeFieldVO);
	/**
	 * 模板信息查询
	 * @param model_cd
	 * @param field_cd
	 * return InvoiceModeFieldVO
	 */
	public InvoiceModeFieldVO templateInvoiceModelFieldInformation(String model_cd,String field_cd);
	
	
	
}
