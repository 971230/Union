package com.ztesoft.net.server.jsonserver.hspojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HSMallOrderInvoice implements Serializable {

	private List<HSMallOrderInvoiceItem> InvoiceItem = new ArrayList<HSMallOrderInvoiceItem>();

	public List<HSMallOrderInvoiceItem> getInvoiceItem() {
		return InvoiceItem;
	}

	public void setInvoiceItem(List<HSMallOrderInvoiceItem> invoiceItem) {
		InvoiceItem = invoiceItem;
	}
	
	
	
}
