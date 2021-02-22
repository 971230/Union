package com.ztesoft.net.mall.core.action.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import params.pay.req.PaySucessReq;
import params.paycfg.req.PaymentCfgBankReq;
import params.paycfg.req.PaymentCfgListReq;
import params.paycfg.req.PaymentCfgReq;
import services.PaymentCfgInf;
import services.PaymentInf;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.action.order.orderc.OrderApply;
import com.ztesoft.net.mall.core.action.order.orderc.OrderHandleParam;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderAudit;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.PayReponse;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;
import com.ztesoft.net.mall.core.service.IOrderAuditManager;
import com.ztesoft.net.mall.core.service.IOrderFlowManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;
import com.ztesoft.net.model.OrderToDoList;

/**
 * 退款\支付action
 * 
 * @author apexking
 * 
 */
public class PaymentNAction extends WWAction {

	private PaymentLog payment;
	private String orderId;
	private IOrderManager orderManager;
	private IOrderFlowManager orderFlowManager;
	//private IPaymentManager paymentManager;
	private Order order;
	private List paymentList;
	private String payment_cfg_id;
	private IOrderUtils orderUtils;
	private OrderAudit orderAudit;
	private IOrderAuditManager orderAuditManager;
	private IOrderDirector orderDirector;
	private IOrderApplycMamager orderApplycMamager;
	private Bank bank;
	private List bankList;
	private PaymentInf paymentServ;
	private PayCfg payCfg;
	//private IBankManager bankManager;
	private PaymentCfgInf paymentCfgServ;
	
	public IOrderApplycMamager getOrderApplycMamager() {
		return orderApplycMamager;
	}



	public void setOrderApplycMamager(IOrderApplycMamager orderApplycMamager) {
		this.orderApplycMamager = orderApplycMamager;
	}



	/**
	 * 显示流量卡支付对话框
	 * @return
	 */
	public String showRatePayDialog(){
		this.order = this.orderManager.get(orderId);
		PaymentCfgListReq req = new PaymentCfgListReq();
		this.paymentList  = paymentCfgServ.queryPaymentCfgList(req).getPayCfgList();
		return "rate_pay_dialog";
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
	public String showRefundAuditDialog() {
		order = orderManager.get(orderId);
		orderAudit = orderAuditManager.getLastAuditRecord(orderId,OrderStatus.AUDIT_TYPE_00B);
		return "refund_audit_dialog";
	}
	
	
	/**
	 * 显示支付对话框
	 * 
	 * @return
	 */
	public String showPayDialog() {
		AdminUser user = ManagerUtils.getAdminUser();
		this.order = this.orderManager.get(orderId);
		if(user.getUserid().equals(order.getPay_user_id()) || orderManager.qryGroupByOrder(user.getUserid(), order.getPay_group_id()).size()>0){
			PaymentCfgReq reqid = new PaymentCfgReq();
			reqid.setPayment_cfg_id(payment_cfg_id);
			payCfg =paymentCfgServ.queryPaymentCfgById(reqid).getPaymentCfg();
					//paymentManager.get(payment_cfg_id);
			//if(!StringUtil.isEmpty(payment_cfg_id))
			//this.paymentList  = this.paymentManager.listById(payment_cfg_id);
			//else
			//	this.paymentList  = this.paymentManager.list();
			//bankList = commonPayHander.getBankList();
			PaymentCfgBankReq req = new PaymentCfgBankReq();
			req.setPayment_cfg_id(payment_cfg_id);
			bankList = paymentCfgServ.queryCfgBankList(req).getBankList();
			//bankManager.qryBankByPaymentCfgId(payment_cfg_id);
		}else{
			return "no_security";
		}
		return "pay_dialog";
	}
	
	
	public String showPayTipDialog() {
		return "pay_tip_dialog";
	}
	
	// 支付
	public String pay() {
		try {
			/*Order order = this.orderManager.get(orderId);
			orderDirector.getOrderBuilder().buildOrderFlow();
			OrderRequst orderRequst = new OrderRequst();
			//CLOUD
			//orderRequst.setService_name(order.getType_code());
			orderRequst.setService_name("TIME_CARD");
			orderRequst.setFlow_name(OrderBuilder.PAYMENT);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
			orderRequst.setPayment(payment);
			orderRequst.setOrderId(orderId);
			OrderResult orderResult = orderDirector.perform(orderRequst);
			
			
			//网银支付的展示网银支付的按钮
			PayReponse payReponse = orderResult.getPayReponse();
			if(payReponse !=null){
				this.json = "{result:1,message:'充值'," +payReponse.toString() + "}";
				return this.JSON_MESSAGE;
			}
			this.json = "{result:1,message:'订单[" + orderId+ "]支付成功',payStatus:" + order.getPay_status() + "}";*/
			String bank_id = payment==null?null:payment.getBank_id();
			Integer paytype = payment==null?0:payment.getPaytype();
			bank_id = ServletActionContext.getRequest().getParameter("bankId");
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
			
			boolean flag = true;
			if(7==order.getPayment_id()){
				flag = false;
				PaySucessReq req = new PaySucessReq();
				AdminUser user = ManagerUtils.getAdminUser();
				if(user!=null)
					req.setUserid(user.getUserid());
				req.setIp(ServletActionContext.getRequest().getRemoteAddr());
				req.setOrder_id(orderId);
				req.setPay_source("001");
				paymentServ.paySuccess(req);
				json = "{result:1,message:'订单[" + orderId+ "]支付成功',payStatus:" + order.getPay_status()+"}";
			}
			if(flag){
				List<PaymentLog> payments = orderManager.listOrderPayMentLog(orderId);
				payment = null;
				for(PaymentLog p:payments){
					if(p.getStatus()==OrderStatus.PAY_STATUS_NOT_CONFIRM && OrderStatus.PAY_TYPE_1==p.getType()){
						payment = p;
						break;
					}
				}
				if(payment==null){
					json = "{result:2,message:'支订单不能支付'}";
					return WWAction.JSON_MESSAGE;
				}
				payment.setBank_id(bank_id);
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
				
				//网银支付的展示网银支付的按钮
				PayReponse payReponse = orderResult.getPayReponse();
				logger.info("json:="+payReponse.toString());
				if(payReponse !=null){
					json = "{result:1,message:'支付'," +payReponse.toString() + "}";
				}else{
					json = "{result:1,message:'订单[" + orderId+ "]支付成功',payStatus:" + order.getPay_status() + "}";
				}
			}
				
		} catch (RuntimeException e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"支付失败：" + e.getMessage() + "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	

	
	/**
	 * 显示退款对话框
	 * @return
	 */
	public String showRefundDialog(){
		this.order = this.orderManager.get(orderId);
		if(!StringUtil.isEmpty(payment_cfg_id)){
			paymentList = new ArrayList();
			PaymentCfgReq req = new PaymentCfgReq();
			req.setPayment_cfg_id(payment_cfg_id);
			paymentList.add(paymentCfgServ.queryPaymentCfgById(req).getPaymentCfg());
			//this.paymentList  = this.paymentManager.listById(payment_cfg_id);
		}else{
			PaymentCfgListReq req = new PaymentCfgListReq();
			this.paymentList  = paymentCfgServ.queryPaymentCfgList(req).getPayCfgList();
		}
		return "refund_dialog";
	}
	
	private double refundAmount=0;
	private List<OrderApply> applylist;
	private double depreciation_price = 0;
	private double ship_price = 0;
	private double returned_price = 0;
	/**
	 * 显示退款窗口
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-28 
	 * @return
	 */
	public String showRefundDialogc(){
		this.order = this.orderManager.get(orderId);
		applylist = orderApplycMamager.queryApplyByOrderId(orderId,OrderStatus.ORDER_TYPE_2, OrderStatus.ORDER_APPLY_STATUS_1);
		for(OrderApply oa:applylist){
			refundAmount +=oa.getPay_price();
			depreciation_price += oa.getDepreciation_price();
			ship_price += oa.getShip_price();	
			returned_price += Double.parseDouble(oa.getRefund_value());
		}
		if(!StringUtil.isEmpty(payment_cfg_id)){
			paymentList = new ArrayList();
			PaymentCfgReq req = new PaymentCfgReq();
			req.setPayment_cfg_id(payment_cfg_id);
			paymentList.add(paymentCfgServ.queryPaymentCfgById(req).getPaymentCfg());
			//this.paymentList  = this.paymentManager.listById(payment_cfg_id);
		}else{
			PaymentCfgListReq req = new PaymentCfgListReq();
			this.paymentList  = paymentCfgServ.queryPaymentCfgList(req).getPayCfgList();
		}
		return "refund_dialog_c";
	}
	
	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public List<OrderApply> getApplylist() {
		return applylist;
	}



	public void setApplylist(List<OrderApply> applylist) {
		this.applylist = applylist;
	}

	private IOrderFlowBussManager orderFlowBussManager;
	private String flow_def_id;
	private OrderToDoList toDo;
	private int flag_status;
	private String hint;
	
	/**
	 * 退款
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-28 
	 * @return
	 */
	public String cancel_pay_c() {
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			/*toDo = orderFlowBussManager.queryUserOrderToDoList(orderId, user.getUserid());
			OrderFlow flow = orderFlowBussManager.getOrderFlowByDefId(toDo.getFlow_def_id());
			int next_status = orderFlowBussManager.next(orderId, null, null, flow_def_id, flag_status, hint, toDo,flow.getService_type());
			if(next_status!=0 || flag_status!=1){
				this.json = "{result:1,message:'成功'}";
				return JSON_MESSAGE;
			}*/
			Order order = this.orderManager.get(orderId);
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMONAGE);
			orderRequst.setFlow_name(OrderBuilder.PAYMENT);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_REFUND);
			OrderAuditRequest orderAuditRequest = new OrderAuditRequest();
			if(orderAudit ==null)
				 orderAudit = new OrderAudit();
			orderAuditRequest.setApply_message(orderAudit.getApply_message());
			orderAuditRequest.setP_audit_message(orderAudit.getP_audit_message());
			orderAuditRequest.setP_audit_state(orderAudit.getP_audit_state());
			orderAuditRequest.setAudit_type(OrderStatus.AUDIT_TYPE_00B);
			orderRequst.setOrderAuditRequest(orderAuditRequest);
			orderDirector.getOrderBuilder().buildGoodsRefundFeeFlow();
			orderRequst.setPayment(payment);
			orderRequst.setOrderId(orderId);
			
			orderDirector.perform(orderRequst);
		
			this.json="{result:1,message:'订单["+order.getSn()+"]处理成功',payStatus:"+order.getPay_status()+"}";
			json = orderFlowBussManager.cancel_pay_c(orderId, flow_def_id, null, null, flag_status, hint, orderAudit, payment);
		}catch(RuntimeException e){
			e.printStackTrace();
			if(logger.isDebugEnabled()){
				logger.debug(e);
			}
			this.json="{result:0,message:\"订单处理失败："+e.getMessage()+"\"}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	 
	}
	

	// 退款
	public String cancel_pay() {
		try{
			Order order = this.orderManager.get(orderId);
			
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMON_KEY);
			
			orderRequst.setFlow_name(OrderBuilder.PAYMENT);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_REFUND);
			
			OrderAuditRequest orderAuditRequest = new OrderAuditRequest();
			if(orderAudit ==null)
				 orderAudit = new OrderAudit();
			orderAuditRequest.setApply_message(orderAudit.getApply_message());
			orderAuditRequest.setP_audit_message(orderAudit.getP_audit_message());
			orderAuditRequest.setP_audit_state(orderAudit.getP_audit_state());
			orderAuditRequest.setAudit_type(OrderStatus.AUDIT_TYPE_00B);
			orderRequst.setOrderAuditRequest(orderAuditRequest);
			orderDirector.getOrderBuilder().buildRefundFeeFlow();
			orderRequst.setPayment(payment);
			orderRequst.setOrderId(orderId);
			
			orderDirector.perform(orderRequst);
		
			this.json="{result:1,message:'订单["+order.getSn()+"]处理成功',payStatus:"+order.getPay_status()+"}";
		}catch(RuntimeException e){
			if(logger.isDebugEnabled()){
				logger.debug(e);
			}
			this.json="{result:0,message:\"订单处理失败："+e.getMessage()+"\"}";
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
	}*/

	

	public List getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List paymentList) {
		this.paymentList = paymentList;
	}

	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}

	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}

	public String getPayment_cfg_id() {
		return payment_cfg_id;
	}

	public void setPayment_cfg_id(String payment_cfg_id) {
		this.payment_cfg_id = payment_cfg_id;
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



	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}



	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}



	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List getBankList() {
		return bankList;
	}

	public void setBankList(List bankList) {
		this.bankList = bankList;
	}

	public PaymentInf getPaymentServ() {
		return paymentServ;
	}

	public void setPaymentServ(PaymentInf paymentServ) {
		this.paymentServ = paymentServ;
	}

	public PayCfg getPayCfg() {
		return payCfg;
	}

	public void setPayCfg(PayCfg payCfg) {
		this.payCfg = payCfg;
	}

	/*public IBankManager getBankManager() {
		return bankManager;
	}

	public void setBankManager(IBankManager bankManager) {
		this.bankManager = bankManager;
	}
*/

	public PaymentCfgInf getPaymentCfgServ() {
		return paymentCfgServ;
	}

	public void setPaymentCfgServ(PaymentCfgInf paymentCfgServ) {
		this.paymentCfgServ = paymentCfgServ;
	}

	public double getDepreciation_price() {
		return depreciation_price;
	}

	public void setDepreciation_price(double depreciation_price) {
		this.depreciation_price = depreciation_price;
	}

	public double getShip_price() {
		return ship_price;
	}

	public void setShip_price(double ship_price) {
		this.ship_price = ship_price;
	}

	public double getReturned_price() {
		return returned_price;
	}

	public void setReturned_price(double returned_price) {
		this.returned_price = returned_price;
	}

	public IOrderFlowBussManager getOrderFlowBussManager() {
		return orderFlowBussManager;
	}

	public void setOrderFlowBussManager(IOrderFlowBussManager orderFlowBussManager) {
		this.orderFlowBussManager = orderFlowBussManager;
	}

	public String getFlow_def_id() {
		return flow_def_id;
	}

	public void setFlow_def_id(String flow_def_id) {
		this.flow_def_id = flow_def_id;
	}

	public OrderToDoList getToDo() {
		return toDo;
	}

	public void setToDo(OrderToDoList toDo) {
		this.toDo = toDo;
	}

	public int getFlag_status() {
		return flag_status;
	}

	public void setFlag_status(int flag_status) {
		this.flag_status = flag_status;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

}
