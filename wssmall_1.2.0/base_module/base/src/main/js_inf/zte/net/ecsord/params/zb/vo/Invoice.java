package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Invoice implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "发票类型", type = "String", isNecessary = "Y", desc = "InvoiceType：发票类型")
	private String InvoiceType;

	@ZteSoftCommentAnnotationParam(name = "发票抬头", type = "String", isNecessary = "Y", desc = "InvoiceTitle：发票抬头")
	private String InvoiceTitle;

	@ZteSoftCommentAnnotationParam(name = "发票内容", type = "String", isNecessary = "Y", desc = "InvoiceDesc：发票内容")
	private String InvoiceDesc;

	@ZteSoftCommentAnnotationParam(name = "发票号码", type = "String", isNecessary = "N", desc = "InvoiceNo：发票号码")
	private String InvoiceNo;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderIdOrderId：订单编号")
	private String notNeedReqStrOrderIdOrderId;

	public String getNotNeedReqStrOrderIdOrderId() {
		return notNeedReqStrOrderIdOrderId;
	}

	public void setNotNeedReqStrOrderIdOrderId(
			String notNeedReqStrOrderIdOrderId) {
		this.notNeedReqStrOrderIdOrderId = notNeedReqStrOrderIdOrderId;
	}

	public String getInvoiceType() {
		return InvoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		InvoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return InvoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		InvoiceTitle = invoiceTitle;
	}

	public String getInvoiceDesc() {
		return InvoiceDesc;
	}

	public void setInvoiceDesc(String invoiceDesc) {
		InvoiceDesc = invoiceDesc;
	}

	public String getInvoiceNo() {
		return InvoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}
}
