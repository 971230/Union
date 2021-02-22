package com.ztesoft.net.model;

public class PayResult {

	private int dealResult; //0成功 1 异常单 2 失败 3 MD5验证不通过
	private String deal_flag;//银行处理结果代码
	private String resultMsg;
	private String money;//支付金额
	private String transaction_id;//es_payment_list->transaction_id
	public String getDeal_flag() {
		return deal_flag;
	}
	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}
	public int getDealResult() {
		return dealResult;
	}
	public void setDealResult(int dealResult) {
		this.dealResult = dealResult;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
}
