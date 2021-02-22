package com.ztesoft.net.mall.core.action.order.cancel;

import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;

/**
 * 
 * @author wui
 * 业务受理
 *
 */
public class CommonCancelHander   extends AbstractHander implements ICancelHander {

	@Override
	public void execute() {
		//取消订单
		orderNFlowManager.cancel_order(getOrder().getOrder_id());
		
		//云卡释放
		if(OrderBuilder.CLOUD_KEY.equals(getOrder().getType_code()))
			cloudManager.resetCloudByOrderId(getOrder().getOrder_id());
		
		//号码释放
		if(OrderBuilder.CONTRACT_KEY.equals(getOrder().getType_code()))
			accNbrManager.resetAccNbrByOrderId(getOrder().getOrder_id());
		
	}

	

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean isCanExecute() {
		return true;
	}
	

}
