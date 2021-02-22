package com.ztesoft.net.mall.core.action.desposit.pay;

import java.util.List;

import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.PayReponse;
import com.ztesoft.net.mall.core.model.PayRequest;
import com.ztesoft.net.mall.core.model.PaymentList;

public interface ICommonPayHander {
	
	public List getBankList();
	public Bank getBankByCode(String bankCode);
	public PayReponse bankPay(PayRequest payRequest);
	public PaymentList getPaymentById(String transactionId);
	public boolean updatePayment(String transactionId, String dealFlag);
	public String getCfInfo(String cfId);
	
	public boolean updatePayment(PaymentList paymentList);
	
	public void payNotify();
	
	//public void afterPay(String ordeSeq, Integer orderAmount, String  retnCode);
	
	public Bank getBankById(String bankId);
	
	
}
