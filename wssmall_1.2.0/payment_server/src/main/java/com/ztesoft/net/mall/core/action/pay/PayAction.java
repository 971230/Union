package com.ztesoft.net.mall.core.action.pay;

import java.util.List;

import com.ztesoft.net.framework.action.WWAction;

/**
 * 已不用
 * @作者 MoChunrun
 * @创建日期 2013-12-27 
 * @版本 V 1.0
 */
@Deprecated
public class PayAction extends WWAction{
	
	private String UPTRANSEQ ;
	private String RETNCODE;
	private String RETNINFO;
	private String ORDERSEQ;
	private Integer ORDERAMOUNT;
	private String ENCODETYPE;
	private String TRANDATE;
	private String SIGN;
	
	private String uptranSeq;
	private String retnCode;
	private String retnInfo;
	private String ordeSeq;
	private Integer orderAmount;
	private String encodeType;
	private String tranDate;
	private String sign;
	private String dealFlag;
	private List bankList ;
	
	
	

	/**
	 * 
	 * @function 银行返回后台信息处理
	 */
	
	public String payNotify(){
		
 		uptranSeq = UPTRANSEQ;
 		retnCode = RETNCODE;
 		retnInfo = RETNINFO;
 		ordeSeq = ORDERSEQ;
 		orderAmount = ORDERAMOUNT;
 		encodeType = ENCODETYPE;
 		tranDate = TRANDATE;
 		sign = SIGN;
 		
		dealFlag = "0";
		
		
		logger.info("SIGN========>:" + sign);		
		return "notify";
	}
	
	

	public String payShow(){
		dealFlag = "0";
		if(ordeSeq==null || "".equals(ordeSeq))
			ordeSeq = ORDERSEQ;
		uptranSeq = UPTRANSEQ;
 		retnCode = RETNCODE;
 		retnInfo = RETNINFO;
 		orderAmount = ORDERAMOUNT;
 		encodeType = ENCODETYPE;
 		tranDate = TRANDATE;
 		sign = SIGN;
		
		return "result";
	}
	
	
	public String payTipDialog(){
		return "pay_dialog";
	}
	
	
	/**
	 * 
	 * 获取银行列表
	 */
	
	public String getBkList(){
		
		return "bankList";
	}
	
	
	
	public String getUptranSeq() {
		return uptranSeq;
	}

	public void setUptranSeq(String uptranSeq) {
		this.uptranSeq = uptranSeq;
	}

	public String getRetnCode() {
		return retnCode;
	}

	public void setRetnCode(String retnCode) {
		this.retnCode = retnCode;
	}

	public String getRetnInfo() {
		return retnInfo;
	}

	public void setRetnInfo(String retnInfo) {
		this.retnInfo = retnInfo;
	}

	public String getOrdeSeq() {
		return ordeSeq;
	}

	public void setOrdeSeq(String ordeSeq) {
		this.ordeSeq = ordeSeq;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	public List getBankList() {
		return bankList;
	}

	public void setBankList(List bankList) {
		this.bankList = bankList;
	}

	

	public String getUPTRANSEQ() {
		return UPTRANSEQ;
	}

	public void setUPTRANSEQ(String uptranseq) {
		UPTRANSEQ = uptranseq;
	}

	public String getRETNCODE() {
		return RETNCODE;
	}

	public void setRETNCODE(String retncode) {
		RETNCODE = retncode;
	}

	public String getRETNINFO() {
		return RETNINFO;
	}

	public void setRETNINFO(String retninfo) {
		RETNINFO = retninfo;
	}

	public String getORDERSEQ() {
		return ORDERSEQ;
	}

	public void setORDERSEQ(String orderseq) {
		ORDERSEQ = orderseq;
	}

	public Integer getORDERAMOUNT() {
		return ORDERAMOUNT;
	}

	public void setORDERAMOUNT(Integer orderamount) {
		ORDERAMOUNT = orderamount;
	}

	public String getENCODETYPE() {
		return ENCODETYPE;
	}

	public void setENCODETYPE(String encodetype) {
		ENCODETYPE = encodetype;
	}

	public String getTRANDATE() {
		return TRANDATE;
	}

	public void setTRANDATE(String trandate) {
		TRANDATE = trandate;
	}

	public String getSIGN() {
		return SIGN;
	}

	public void setSIGN(String sign) {
		SIGN = sign;
	}
}
