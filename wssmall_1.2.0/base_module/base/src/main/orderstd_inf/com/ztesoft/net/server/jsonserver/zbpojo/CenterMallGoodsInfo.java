package com.ztesoft.net.server.jsonserver.zbpojo;

import java.util.List;

/***********************************************
 * 
 * @author duanshaochu
 * 商品信息
 */
public class CenterMallGoodsInfo {

	
	//商品编码
	private String goodsCode = ""; 
	//商品名称
	private String goodsName = "";
	//商品应收，单位为厘
	private String goodsOrigFee = "";
	//商品实收，单位为厘
	private String goodsRealFee = "";
	//商品减免原因
	private String goodsReliefRes = "";
	//商品类型
	/*
	 * ZX add 2016-01-06 start 注释
	private String goodsType = "";
	 * ZX add 2016-01-06 end 注释
	 */
	//商品附属信息
	private List<CenterMallGoodsAttInfo> goodsAttInfo;
	//费用明细(总部商城),见订单附属信息“费用明细信息
	private List<CenterMallFeeInfo> feeInfo;
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsOrigFee() {
		return goodsOrigFee;
	}
	public void setGoodsOrigFee(String goodsOrigFee) {
		this.goodsOrigFee = goodsOrigFee;
	}
	public String getGoodsRealFee() {
		return goodsRealFee;
	}
	public void setGoodsRealFee(String goodsRealFee) {
		this.goodsRealFee = goodsRealFee;
	}
	public String getGoodsReliefRes() {
		return goodsReliefRes;
	}
	public void setGoodsReliefRes(String goodsReliefRes) {
		this.goodsReliefRes = goodsReliefRes;
	}
//	public String getGoodsType() {
//		return goodsType;
//	}
//	public void setGoodsType(String goodsType) {
//		this.goodsType = goodsType;
//	}

	
	public List<CenterMallGoodsAttInfo> getGoodsAttInfo() {
		return goodsAttInfo;
	}
	public void setGoodsAttInfo(List<CenterMallGoodsAttInfo> goodsAttInfo) {
		this.goodsAttInfo = goodsAttInfo;
	}
	public List<CenterMallFeeInfo> getFeeInfo() {
		return feeInfo;
	}
	public void setFeeInfo(List<CenterMallFeeInfo> feeInfo) {
		this.feeInfo = feeInfo;
	}
	
	
}
