package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderAudit;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.service.IOrderAuditManager;
import com.ztesoft.net.mall.core.service.IOrderFlowManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import params.paycfg.req.PaymentCfgListReq;
import services.PaymentCfgInf;

import java.util.List;

/**
 * 退款\支付action
 * 
 * @author apexking
 * 
 */
public class PaymentAction extends WWAction {

	private PaymentLog payment;
	private String orderId;
	private IOrderManager orderManager;
	private IOrderFlowManager orderFlowManager;
	//private IPaymentManager paymentManager;
	private PaymentCfgInf paymentCfgServ;
	private Order order;
	private List paymentList;
	private OrderAudit orderAudit;
	private IOrderAuditManager orderAuditManager;
	
	
	
	
	/**
	 * 显示支付对话框
	 * @return
	 */
	public String showPayDialog(){
		this.order = this.orderManager.get(orderId);
		PaymentCfgListReq req = new PaymentCfgListReq();
		this.paymentList  = paymentCfgServ.queryPaymentCfgList(req).getPayCfgList();
		return "pay_dialog";
	}
	
	
	
	/**
	 * 显示退费申请界面
	 * 
	 * @return
	 */
	public String showRefundApplyDialog() {
		order = orderManager.get(orderId);
		return "refund_apply_dialog";
	}
	
	
	/**
	 * 显示退费审核
	 * 
	 * @return
	 */
//	public String showRefundtAuditDialog() {
//		order = orderManager.get(orderId);
//		orderAudit = orderAuditManager.getLastAuditRecord(orderId);
//		return "refund_audit_dialog";
//	}
	
	
	/**
	 * 显示退款对话框
	 * @return
	 */
	public String showRefundDialog(){
		this.order = this.orderManager.get(orderId);
		PaymentCfgListReq req = new PaymentCfgListReq();
		this.paymentList  = paymentCfgServ.queryPaymentCfgList(req).getPayCfgList();
		return "refund_dialog";
	}
	
	// 支付
	public String pay() {
		try{
			payment.setOrder_id(orderId);
			orderFlowManager.pay(payment,false);
			Order order = this.orderManager.get(orderId);
			this.json="{result:1,message:'订单["+order.getSn()+"]支付成功',payStatus:"+order.getPay_status()+"}";
		}catch(RuntimeException e){
			if(logger.isDebugEnabled()){
				logger.debug(e);
			}
			this.json="{result:0,message:\"支付失败："+e.getMessage()+"\"}";
		}
		return WWAction.JSON_MESSAGE;
	}

	// 退款
	public String cancel_pay() {
		try{
			payment.setOrder_id(orderId);
			orderFlowManager.refund(payment);
			Order order = this.orderManager.get(orderId);
			this.json="{result:1,message:'订单["+order.getSn()+"]退款成功',payStatus:"+order.getPay_status()+"}";
		}catch(RuntimeException e){
			if(logger.isDebugEnabled()){
				logger.debug(e);
			}
			this.json="{result:0,message:\"退款失败："+e.getMessage()+"\"}";
		}
		return WWAction.JSON_MESSAGE;	 
	}

	public PaymentLog getPayment() {
		return payment;
	}

	public void setPayment(PaymentLog payment) {
		this.payment = payment;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public IOrderFlowManager getOrderFlowManager() {
		return orderFlowManager;
	}

	public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
		this.orderFlowManager = orderFlowManager;
	}

	/*public IPaymentManager getPaymentManager() {
		return paymentManager;
	}

	public void setPaymentManager(IPaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}
*/
	

	public List getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List paymentList) {
		this.paymentList = paymentList;
	}



	public Order getOrder() {
		return order;
	}



	public void setOrder(Order order) {
		this.order = order;
	}



	public OrderAudit getOrderAudit() {
		return orderAudit;
	}



	public void setOrderAudit(OrderAudit orderAudit) {
		this.orderAudit = orderAudit;
	}



	public IOrderAuditManager getOrderAuditManager() {
		return orderAuditManager;
	}



	public void setOrderAuditManager(IOrderAuditManager orderAuditManager) {
		this.orderAuditManager = orderAuditManager;
	}



	public PaymentCfgInf getPaymentCfgServ() {
		return paymentCfgServ;
	}



	public void setPaymentCfgServ(PaymentCfgInf paymentCfgServ) {
		this.paymentCfgServ = paymentCfgServ;
	}


}
