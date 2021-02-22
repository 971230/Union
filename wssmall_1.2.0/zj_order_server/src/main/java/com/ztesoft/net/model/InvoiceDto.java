/**
 * 
 */
package com.ztesoft.net.model;

import java.util.List;

/**
 * @author ZX
 * @时间 2014-11-13 14:56:56
 * InvoiceDto.java
 * 2014-11-13
 */
public class InvoiceDto {

	private InvoiceModelVO invoiceModelVO;
	private List<FieldModelVO> fieldModelVoList;
	
	public InvoiceModelVO getInvoiceModelVO() {
		return invoiceModelVO;
	}
	public void setInvoiceModelVO(InvoiceModelVO invoiceModelVO) {
		this.invoiceModelVO = invoiceModelVO;
	}
	public List<FieldModelVO> getFieldModelVoList() {
		return fieldModelVoList;
	}
	public void setFieldModelVoList(List<FieldModelVO> fieldModelVoList) {
		this.fieldModelVoList = fieldModelVoList;
	}
	
}
