package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.service.IPaymentListManager;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PaymentListManager extends BaseSupport<PaymentList> implements IPaymentListManager{
	@Override
	public boolean insertPayment(PaymentList paymentList){
			
		this.baseDaoSupport.insert("payment_list", paymentList);
		return true;
	}
	
	@Override
	public PaymentList getPaymentById(String transactionId){
		try {
			String sql = "select * from payment_list  where transaction_id = ? and sequ = 0 and state = '1'";
			return this.baseDaoSupport.queryForObject(sql, PaymentList.class, transactionId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * @function 更新支付流水
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean updatePayment(String transactionId, String dealFlag){

		String sql = "select * from payment_list where transaction_id = ?";
		List<PaymentList> list = this.baseDaoSupport.queryForList(sql, PaymentList.class, transactionId);
		Integer sequ = 0;
		if (null != list && !list.isEmpty()) {
			
			PaymentList  temPayment;
			for(int i = 0; i < list.size(); i++){
				temPayment = list.get(i);
				if(temPayment.getSequ() > sequ){
					sequ = temPayment.getSequ();
				}
			}
		}
		
		PaymentList payment = this.getPaymentById(transactionId);
		if(null != payment){
			//备份原有数据
			payment.setSequ(sequ + 1);
			payment.setState("0");
			this.insertPayment(payment);
			//新增修改后数据
			payment.setSequ(0);
			payment.setState("1");
			payment.setDeal_flag(dealFlag);
			payment.setOper_time(DBTUtil.current());
			this.edit(payment);
		}
			
		return true;
	}
	
	@Override
	public boolean edit(PaymentList payment){
			
			this.baseDaoSupport.update("payment_list", payment, " transaction_id = '" + 
					payment.getTransaction_id() + "'  and sequ = 0 and state = '1'");

		return true;
	}
}
