package com.ztesoft.net.mall.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IRefundManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;

public class RefundManager extends BaseSupport implements IRefundManager {
	
	private IOrderManager orderManager;

	@Override
	public PaymentLog qrySuccessPaymentLogByOrderId(String order_id) {
		String sql = SF.orderSql("SERVICE_SUCC_PAYMENT_LOGS_SELECT");
		List<PaymentLog> list = this.baseDaoSupport.queryForList(sql, PaymentLog.class, order_id);
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public void refund(PaymentLog paymentLog) {
		PaymentLog payment = new PaymentLog();
		try {
			BeanUtils.copyProperties(payment, paymentLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		payment.setStatus(OrderStatus.PAY_STATUS_1);
		payment.setType(OrderStatus.PAY_TYPE_2);
		payment.setPaytype(0);
		payment.setCreate_time(DBTUtil.current());
		payment.setStatus_time(DBTUtil.current());
		payment.setRemark("退费");
		payment.setPayment_id("");
		AdminUser user = ManagerUtils.getAdminUser();
		if(user!=null){
			payment.setUserid(user.getUserid());
			payment.setUser_name(user.getUsername());
		}
		this.baseDaoSupport.insert("payment_logs", payment);
		Order order = this.orderManager.get(payment.getOrder_id());
		double nm = payment.getMoney();// 当前付款金额
		double om = order.getPaymoney();// 已收金额
		int payStatus = 0;
		if (nm < om)
			payStatus = OrderStatus.PAY_PARTIAL_REFUND;// 部分退款
		if (nm == om || nm<0)
			payStatus = OrderStatus.PAY_CANCEL;// 已退款
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUE_UPDATE"), OrderStatus.ORDER_CANCEL_PAY,payStatus,payment.getMoney(),payment.getOrder_id());
	}

	@Override
	public void refund(String order_id){
		PaymentLog log = this.qrySuccessPaymentLogByOrderId(order_id);
		if(log==null)throw new RuntimeException("没找到需要退款单");
		refund(log);
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

}
