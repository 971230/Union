package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.model.AccNbrRequest;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.IOrderManager;

/**
 * 号码调拨处理
 * 
 * @author wui
 * 
 */
public class AccAction extends WWAction {

	private AccNbrRequest accNbrRequest;
	private String order_id;
	private Order order;
	private IOrderManager orderManager;
	private IOrderDirector orderDirector;
	
	/**
	 * 显示号码调拨界面
	 * 
	 * @return
	 */
	public String showAccNbrDialog() {
		order = orderManager.get(order_id);
		return "accnbr_dialog";
	}

	
	// 云卡调拨处理
	public String transfer() {
		try {
			transferAccNbr();
			this.json = "{result:1,message:'订单[" + order.getSn() + "]号码调拨成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"号码调拨失败：" + e.getMessage()
					+ "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 
	 * 订单号码调拨受理
	 */
	private void transferAccNbr() {
		orderDirector.getOrderBuilder().buildOrderFlow();
		OrderRequst orderRequst = new OrderRequst();
		orderRequst.setService_name(OrderBuilder.CLOUD_KEY);
		orderRequst.setFlow_name(OrderBuilder.ACCEPT);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
		orderRequst.setOrderId(order_id);
		orderRequst.setAccNbrRequest(accNbrRequest);
		orderDirector.perform(orderRequst);
	}


	public IOrderManager getOrderManager() {
		return orderManager;
	}


	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}


	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}


	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}
	
	

}
