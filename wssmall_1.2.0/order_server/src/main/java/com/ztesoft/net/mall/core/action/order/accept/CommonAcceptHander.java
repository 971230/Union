package com.ztesoft.net.mall.core.action.order.accept;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.AbstractHander;

/**
 * 
 * @author wui
 * 业务受理
 *
 */
public class CommonAcceptHander   extends AbstractHander implements IAcceptHander {


	public boolean  canAccept(){
		if(getOrder().getStatus().equals(OrderStatus.ORDER_NOT_PAY)) 
			return true;
		return false;
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accept() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
		
	}
	

}
