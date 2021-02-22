package com.ztesoft.net.server.jsonserver.hspojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HSMallOrderReqItems implements Serializable {

	private List<HSMallOrderReqItemsDetail> Item = new ArrayList<HSMallOrderReqItemsDetail>();

	public List<HSMallOrderReqItemsDetail> getItem() {
		return Item;
	}

	public void setItem(List<HSMallOrderReqItemsDetail> item) {
		Item = item;
	}
	
	
	
}
