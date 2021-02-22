package com.ztesoft.net.mall.core.model;

/**
 * 
 * 充值卡充值请求参数
 * 
 * @author wui
 */
public class CardInfRequest implements java.io.Serializable {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ordeSeq ;//淘宝支付流水号
	private String userId; //充值接口归属商户
	private String accNbr; //充值号码
	private String orderAmount; //充值金额
	private String tranDate; // 支付日期
	private String retnCode;//返回编码
	private String retnInfo;//返回信息
	private String goodsId; //充值对应分销平台的商品id
	private String lan_id; //充值本地网id
	
	public CardInfRequest(){
		
	}
	public CardInfRequest( String ordeSeq,String userId,String accNbr,String orderAmount, String tranDate,String retnCode,String retnInfo,String goodsId,String lan_id){
		this.ordeSeq = ordeSeq;
		this.userId=userId;
		this.accNbr =accNbr; 
		this.orderAmount = orderAmount; 
		this.tranDate =tranDate;
		this.retnCode =retnCode;
		this.retnInfo=retnInfo;
		this.goodsId =goodsId; 
		this.lan_id =lan_id;
	}
	public String getOrdeSeq() {
		return ordeSeq;
	}
	public void setOrdeSeq(String ordeSeq) {
		this.ordeSeq = ordeSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccNbr() {
		return accNbr;
	}
	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
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
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	
	
	

}