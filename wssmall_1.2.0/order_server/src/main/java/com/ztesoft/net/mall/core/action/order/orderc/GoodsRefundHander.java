package com.ztesoft.net.mall.core.action.order.orderc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import services.PaymentInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;
import com.ztesoft.net.sqls.SF;

public class GoodsRefundHander extends AbstractHander{
	
	private IOrderApplycMamager orderApplycMamager;

	public IOrderApplycMamager getOrderApplycMamager() {
		return orderApplycMamager;
	}

	public void setOrderApplycMamager(IOrderApplycMamager orderApplycMamager) {
		this.orderApplycMamager = orderApplycMamager;
	}

	@Override
	public boolean isCanExecute() {
		return true;
	}

	@Resource
	private PaymentInf paymentServ;
	
	@Override
	public void execute() {
		//String state = getLastAuditState();
		List<OrderApply> list = orderApplycMamager.queryApplyByOrderId(getOrderRequst().getOrderId(),OrderStatus.ORDER_TYPE_2, OrderStatus.ORDER_APPLY_STATUS_1);
		if(list==null || list.size()<1) throw new  IllegalArgumentException("order not allow refund");
		Order order = this.getOrder();
		for(OrderApply oa:list){
			
			PaymentList paymentlist = new PaymentList();
			paymentlist.setCreate_date(DBTUtil.current());
			paymentlist.setDeal_date(DBTUtil.current());
			paymentlist.setDeal_flag("2");
			paymentlist.setSequ(0);
			paymentlist.setState("1");
			paymentlist.setPay_amount((int)(oa.getPay_price()*100));
			//paymentlist.setPay_type(req.getPay_type());
			paymentlist.setPay_date(DBTUtil.current());
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			paymentlist.setTransdate(df.format(new Date()));
			paymentlist.setOper_time(DBTUtil.current());
			paymentlist.setOrder_id(order.getOrder_id());
			paymentlist.setPaytype(0);
			paymentServ.insertPaymentList(paymentlist);
			
			PaymentLog payment = new PaymentLog();
			payment.setTransaction_id(paymentlist.getTransaction_id());
			payment.setOrder_id(oa.getA_order_item_id());
			payment.setStatus(OrderStatus.PAY_STATUS_1);
			payment.setStatus_time("sysdate");
			payment.setCreate_time("sysdate");
			payment.setMoney(oa.getPay_price());
			payment.setPay_type(oa.getRefund_type());
			payment.setType(OrderStatus.PAY_TYPE_2);
			payment.setPaytype(0);
			payment.setPay_method(Consts.PAYMENT_ID_BANK.toString());
			//payment.setPay_method(oa.getRefund_type());
			orderNFlowManager.refund(payment);
			//此处做退款操作=======
			if(OrderStatus.REFUND_TYPE_1.equals(oa.getRefund_type())){
				//退款至账户余额
				logger.info("退款至账户余额");
			}else if(OrderStatus.REFUND_TYPE_2.equals(oa.getRefund_type())){
				//原支付方式返回
				String transactionid = order.getTransaction_id();
				double bak_money = Double.valueOf(oa.getRefund_value());
				logger.info("原支付方式返回");
			}else if(OrderStatus.REFUND_TYPE_3.equals(oa.getRefund_type())){
				//退款至银行卡
				String bank_account = oa.getBank_account();
				String account_name = oa.getAccount_holder_name();
				
				logger.info("退款至银行卡");
			}
			//此处做退款操作=======
			//更新关系状态
			orderApplycMamager.updateApplyState(oa.getOrder_apply_id(), OrderStatus.ORDER_APPLY_STATUS_5);
			this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_REL_UPDATE"), OrderStatus.ORDER_APPLY_REL_STATE_1,oa.getOrder_apply_id());
		}
		
		/*if (state.equals(OrderStatus.ORDER_AUDIT_STATE_4)) {
			PaymentLog payment = getOrderRequst().getPayment();
			payment.setOrder_id(getOrder().getOrder_id());
			payment.setStatus(OrderStatus.PAY_STATUS_1);
			payment.setMoney(getOrder().getPaymoney());
			//充值卡退费处理,设置退费金额
			if(OrderStatus.ORDER_TYPE_2.equals(getOrder().getOrder_type()) && OrderBuilder.CARD_KEY.equals(getOrder().getType_code())){
				payment.setMoney(-getOrder().getOrder_amount());
			}
			orderNFlowManager.refund(payment);
			
			//更新关系状态
			OrderRel orderRel = new OrderRel();
			orderRel.setA_order_id(getOrder().getOrder_id());
			orderRel.setRel_type(OrderStatus.ORDER_TYPE_2);
			orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
			orderRel.setState_date(Consts.SYSDATE);
			orderNFlowManager.updateOrderRel(orderRel);
		}else{
			 throw new  IllegalArgumentException("order not allow refund");
		}*/
	}

	@Override
	public void display() {
		
	}

}
