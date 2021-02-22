package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;


import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class AccountInfo implements Serializable{

	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="SerialNumber：服务号码")
	private String SerialNumber;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="卡信息",type="String",isNecessary="N",desc="SimCardNo：卡信息")
	private String SimCardNo;
	
	@ZteSoftCommentAnnotationParam(name="串码信息",type="String",isNecessary="N",desc="Imei：串码信息")
	private String Imei;
	
	@ZteSoftCommentAnnotationParam(name="ESS订单编号",type="String",isNecessary="Y",desc="TradeId:ESS订单编号")
	private String TradeId;
	
	@ZteSoftCommentAnnotationParam(name="省份BSS订单ID",type="String",isNecessary="N",desc="BssOrderID:省份BSS订单ID")
	private String BssOrderID;
	
	@ZteSoftCommentAnnotationParam(name="开户时间",type="String",isNecessary="Y",desc="OpenTime：开户时间")
	private String OpenTime;
	
	@ZteSoftCommentAnnotationParam(name="开户员工信息",type="String",isNecessary="Y",desc="OpenStaff：开户员工信息")
	private String OpenStaff;
	
	@ZteSoftCommentAnnotationParam(name="开户员工姓名",type="String",isNecessary="Y",desc="OpenStaffName：开户员工姓名")
	private String OpenStaffName;
	
	@ZteSoftCommentAnnotationParam(name="发展人",type="String",isNecessary="Y",desc="DevelopCode:发展人")
	private String DevelopCode;
	
	@ZteSoftCommentAnnotationParam(name="发展人姓名",type="String",isNecessary="Y",desc="DevelopName:发展人姓名")
	private String DevelopName;
	
	@ZteSoftCommentAnnotationParam(name="税控码",type="String",isNecessary="N",desc="TaxNo：税控码")
	private String TaxNo;
	
	@ZteSoftCommentAnnotationParam(name="受理单模板编码",type="String",isNecessary="N",desc="AcceptanceTp:受理单模板编码")
	private String AcceptanceTp;
	
	@ZteSoftCommentAnnotationParam(name="打印方式",type="String",isNecessary="N",desc="AcceptanceMode:打印方式")
	private String AcceptanceMode;
	
	@ZteSoftCommentAnnotationParam(name="受理单打印内容",type="String",isNecessary="N",desc="AcceptanceForm:受理单打印内容")
	private String AcceptanceForm;
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="Par",desc="Para:保留字段", isNecessary = "Y")
	private Par Para;
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}
	public String getSimCardNo() {
		return SimCardNo;
	}
	public void setSimCardNo(String simCardNo) {
		SimCardNo = simCardNo;
	}
	public String getImei() {
		return Imei;
	}
	public void setImei(String imei) {
		Imei = imei;
	}
	public String getTradeId() {
		return TradeId;
	}
	public void setTradeId(String tradeId) {
		TradeId = tradeId;
	}
	public String getBssOrderID() {
		return BssOrderID;
	}
	public void setBssOrderID(String bssOrderID) {
		BssOrderID = bssOrderID;
	}
	public String getOpenTime() {
		return OpenTime;
	}
	public void setOpenTime(String openTime) {
		OpenTime = openTime;
	}
	public String getOpenStaff() {
		return OpenStaff;
	}
	public void setOpenStaff(String openStaff) {
		OpenStaff = openStaff;
	}
	public String getOpenStaffName() {
		return OpenStaffName;
	}
	public void setOpenStaffName(String openStaffName) {
		OpenStaffName = openStaffName;
	}
	public String getDevelopCode() {
		return DevelopCode;
	}
	public void setDevelopCode(String developCode) {
		DevelopCode = developCode;
	}
	public String getDevelopName() {
		return DevelopName;
	}
	public void setDevelopName(String developName) {
		DevelopName = developName;
	}
	public String getTaxNo() {
		return TaxNo;
	}
	public void setTaxNo(String taxNo) {
		TaxNo = taxNo;
	}
	public String getAcceptanceTp() {
		
		return AcceptanceTp;
	}
	public void setAcceptanceTp(String acceptanceTp) {
		AcceptanceTp = acceptanceTp;
	}
	public String getAcceptanceMode() {
		return AcceptanceMode;
	}
	public void setAcceptanceMode(String acceptanceMode) {
		AcceptanceMode = acceptanceMode;
	}
	public String getAcceptanceForm() {
	
		
		return AcceptanceForm;
	}
	public void setAcceptanceForm(String acceptanceForm) {
		AcceptanceForm = acceptanceForm;
	}
	public Par getPara() {
		return Para;
	}
	public void setPara(Par para) {
		Para = para;
	}
	
	
	
}
