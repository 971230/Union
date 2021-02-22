package com.ztesoft.net.mall.core.pay;

import com.ztesoft.net.framework.action.WWAction;

/**
 * 已经不使用了
 * @作者 MoChunrun
 * @创建日期 2013-12-27 
 * @版本 V 1.0
 */
@Deprecated
public class PaymentcAction extends WWAction {

	//private String orderId;
	//private PaymentLog payment;
	//private IOrderDirector orderDirector;
	//private IOrderManager orderManager;
	
	public String payc(){
		/*try {
			Integer paytype = payment==null?0:payment.getPaytype();
			if(paytype==null)
				paytype = 0;
			Order order = null;//orderManager.get(orderId);
			if(paytype.intValue()==0){
				order = orderManager.get(orderId);
			}else{
				List<Order> list = orderManager.getByBatchID(orderId);
				if(list==null || list.size()<1)throw new RuntimeException("orderid is not found");
				order = list.get(0);
			}
			List<PaymentLog> payments = orderManager.listOrderPayMentLog(orderId);
			PaymentLog payment = null;
			for(PaymentLog p:payments){
				if(p.getStatus()==OrderStatus.PAY_STATUS_NOT_CONFIRM){
					payment = p;
					break;
				}
			}
			orderDirector.getOrderBuilder().buildPaymentFlow();
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(order.getType_code());
			orderRequst.setFlow_name(OrderBuilder.PAYMENT_C);
			orderRequst.setAccept_action(OrderStatus.BUTTON_CUST_ACCEPT_C);
			orderRequst.setPayment(payment);
			orderRequst.setOrderId(orderId);
			OrderHandleParam op = new OrderHandleParam();
			op.setType_code(order.getType_code());
			orderRequst.setOrderParam(op);
			OrderResult orderResult = orderDirector.perform(orderRequst);
			//PaymentHander
			
			//网银支付的展示网银支付的按钮
			PayReponse payReponse = orderResult.getPayReponse();
			logger.info("json:="+payReponse.toString());
			if(payReponse !=null){
				json = "{result:1,message:'支付'," +payReponse.toString() + "}";
			}else{
				json = "{result:1,message:'订单[" + orderId+ "]支付成功',payStatus:" + order.getPay_status() + "}";
			}
			//logger.info("json:="+json);
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			json = "{result:0,message:\"支付失败：" + e.getMessage() + "\"}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	/*public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public PaymentLog getPayment() {
		return payment;
	}

	public void setPayment(PaymentLog payment) {
		this.payment = payment;
	}

	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}

	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}*/

	/*public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}*/

}
