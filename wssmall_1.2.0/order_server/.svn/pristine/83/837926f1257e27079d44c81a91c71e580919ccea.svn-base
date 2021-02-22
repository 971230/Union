package com.ztesoft.net.mall.core.action.order.invalid;

import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;

/**
 * 
 * @author wui
 * 撤单处理类
 *
 */
public class CommonWithDrawsHander   extends AbstractHander implements IWithDrawsHander {



	@Override
	public void execute() {
		// TODO Auto-generated method stub
		withdraw();
		
	}

	

	@Override
	public void withdraw() {
		
		orderNFlowManager.withdraw(getOrder().getOrder_id());
		super.addLog("撤销订单");
		
		//云卡释放
		if(OrderBuilder.CLOUD_KEY.equals(getOrder().getType_code()))
			cloudManager.resetCloudByOrderId(getOrder().getOrder_id());
		
		//号码释放
		if(OrderBuilder.CONTRACT_KEY.equals(getOrder().getType_code()))
			accNbrManager.resetAccNbrByOrderId(getOrder().getOrder_id());
		
		
		
	}



	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean isCanExecute() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
