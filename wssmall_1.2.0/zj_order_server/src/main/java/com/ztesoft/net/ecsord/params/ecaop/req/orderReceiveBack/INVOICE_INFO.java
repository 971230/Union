package com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack;

public class INVOICE_INFO {

	private String RECEIVE_MESSAGE_EMAIL;
	private String RECEIVE_MESSAGE_NUM;
	private String BUYER_TAX_PAYER_ID;
	private String BUYER_BANK_ACCOUNT;
	private String IN_VOICE_HEADER;
	private String BUYER_ADDR;
	private String INVOICE_URL;
	private double FEE;
	private String FEE_ITEM_CODE;
	private String FEE_ITEM_NAME;
	private String IS_PRINT;
	private String TICKET_TYPE_CODE;
	private String TICKET_ID;
	private String INVOICE_CHECK_CODE;
	private String TAX_NO;

	public void setRECEIVE_MESSAGE_EMAIL(String RECEIVE_MESSAGE_EMAIL) {
		this.RECEIVE_MESSAGE_EMAIL = RECEIVE_MESSAGE_EMAIL;
	}

	public String getRECEIVE_MESSAGE_EMAIL() {
		return RECEIVE_MESSAGE_EMAIL;
	}

	public void setRECEIVE_MESSAGE_NUM(String RECEIVE_MESSAGE_NUM) {
		this.RECEIVE_MESSAGE_NUM = RECEIVE_MESSAGE_NUM;
	}

	public String getRECEIVE_MESSAGE_NUM() {
		return RECEIVE_MESSAGE_NUM;
	}

	public void setBUYER_TAX_PAYER_ID(String BUYER_TAX_PAYER_ID) {
		this.BUYER_TAX_PAYER_ID = BUYER_TAX_PAYER_ID;
	}

	public String getBUYER_TAX_PAYER_ID() {
		return BUYER_TAX_PAYER_ID;
	}

	public void setBUYER_BANK_ACCOUNT(String BUYER_BANK_ACCOUNT) {
		this.BUYER_BANK_ACCOUNT = BUYER_BANK_ACCOUNT;
	}

	public String getBUYER_BANK_ACCOUNT() {
		return BUYER_BANK_ACCOUNT;
	}

	public void setIN_VOICE_HEADER(String IN_VOICE_HEADER) {
		this.IN_VOICE_HEADER = IN_VOICE_HEADER;
	}

	public String getIN_VOICE_HEADER() {
		return IN_VOICE_HEADER;
	}

	public void setBUYER_ADDR(String BUYER_ADDR) {
		this.BUYER_ADDR = BUYER_ADDR;
	}

	public String getBUYER_ADDR() {
		return BUYER_ADDR;
	}

	public void setINVOICE_URL(String INVOICE_URL) {
		this.INVOICE_URL = INVOICE_URL;
	}

	public String getINVOICE_URL() {
		return INVOICE_URL;
	}

	public double getFEE() {
		return FEE;
	}

	public void setFEE(double fEE) {
		FEE = fEE;
	}

	public void setFEE_ITEM_CODE(String FEE_ITEM_CODE) {
		this.FEE_ITEM_CODE = FEE_ITEM_CODE;
	}

	public String getFEE_ITEM_CODE() {
		return FEE_ITEM_CODE;
	}

	public void setFEE_ITEM_NAME(String FEE_ITEM_NAME) {
		this.FEE_ITEM_NAME = FEE_ITEM_NAME;
	}

	public String getFEE_ITEM_NAME() {
		return FEE_ITEM_NAME;
	}

	public void setIS_PRINT(String IS_PRINT) {
		this.IS_PRINT = IS_PRINT;
	}

	public String getIS_PRINT() {
		return IS_PRINT;
	}

	public void setTICKET_TYPE_CODE(String TICKET_TYPE_CODE) {
		this.TICKET_TYPE_CODE = TICKET_TYPE_CODE;
	}

	public String getTICKET_TYPE_CODE() {
		return TICKET_TYPE_CODE;
	}

	public void setTICKET_ID(String TICKET_ID) {
		this.TICKET_ID = TICKET_ID;
	}

	public String getTICKET_ID() {
		return TICKET_ID;
	}

	public void setINVOICE_CHECK_CODE(String INVOICE_CHECK_CODE) {
		this.INVOICE_CHECK_CODE = INVOICE_CHECK_CODE;
	}

	public String getINVOICE_CHECK_CODE() {
		return INVOICE_CHECK_CODE;
	}

	public void setTAX_NO(String TAX_NO) {
		this.TAX_NO = TAX_NO;
	}

	public String getTAX_NO() {
		return TAX_NO;
	}

}