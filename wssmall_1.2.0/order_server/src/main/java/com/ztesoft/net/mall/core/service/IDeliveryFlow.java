package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.DeliveryFlow;

public interface IDeliveryFlow {

	public void addDeliveryFlow(DeliveryFlow flow);
	
	public List<DeliveryFlow> qryDeliveryFlowByDeliveryID(String deliveryID);
	
	public List<DeliveryFlow> qryFlowInfo(String logi_no, String logi_id);
}
