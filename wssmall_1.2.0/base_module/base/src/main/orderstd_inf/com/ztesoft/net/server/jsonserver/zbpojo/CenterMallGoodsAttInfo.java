package com.ztesoft.net.server.jsonserver.zbpojo;

import java.util.List;

/***********************************************
 * 
 * @author duanshaochu
 * 商品附属信息
 */
public class CenterMallGoodsAttInfo {

	//号卡客户姓名
	private String customerName = "";
	//证件类型
	private String certType = "";
	//证件号码
	private String certNum = "";
	//客户类型
	private String customerType = "";
	/*ZX add 2015-12-24 start*/
	//集团ID
	private String groupId;
	//应用类别，当加入具体集团时为必填 0：行业应用 	1：非行业应用
	private String appType;
	//应用子类型
	private String subAppType;
	/*ZX add 2015-12-24 end*/
	//证件失效时间 YYYY-MM-DD HH24:MI:SS
	private String certLoseTime = "";
	//证件地址
	private String certAddr = "";
	/*ZX add 2015-12-24 start*/
	// 认证类型：cbss系统订单必传 01：本地认证	02：公安认证	03：二代证读卡器
	private String checkType;
	// 实名认证类型：1：未实名认证 2：已实名认证
	private String realnameType;
	// 性别，固定长度1位， M：男， F：女
	private String sex;
	/*ZX add 2015-12-24 end*/
	//推荐人名称
	private String refereeName = "";
	//推荐人号码（联系方式）
	private String refereeNum = "";
	//发展人编码
	private String developCode = "";
	//发展人名称
	private String developName = "";
	/*ZX add 2015-12-24 start*/
	private String developDepId;
	/*ZX add 2015-12-24 end*/
	//订购号码
	//private String phoneNum = "";
	//减免预存标记
	private String reliefPresFlag = "";
	//销售方式
	private String saleMode = "";
	//SIM卡号
	private String simId = "";
	//卡类型
	private String cardType = "";
	/*ZX add 2015-12-24 start*/
	// 商品类型
	private String goodsType;
	/*ZX add 2015-12-24 end*/
	//产品编码
	private String productCode = "";
	//产品名称
	private String productName = "";
	//产品网别
	private String productNet = "";
	//产品品牌
	private String productBrand = "";
	//首月资费方式
	private String firstMonBillMode = "";
	//付费类型
	private String serType = "";
	//附加产品信息
	private List<CenterMallSubProdInfo> subProdInfo;
	//产品类型，4G必传
	private String productType = "";
	//可选包信息
	private List<CenterMallPackage> Package;
	public List<CenterMallPackage> getPackage() {
		return Package;
	}
	public void setPackage(List<CenterMallPackage> package1) {
		Package = package1;
	}
	//合约信息,目前仅支持一个合约且在该模版中必须传一个合约信息
	private List<CenterMallActivityInfo> activityInfo;
	//是否为定制机
	private String isCustomized = "";
	//终端信息
	private CenterMallResourcesInfo resourcesInfo;
	//产品包编码
	private String proPacCode = "";
	//产品包说明
	private String proPacDesc = "";
	//配件类型
	private String partsType = "";
	//老用户优惠购机业务编码
	private String bipCode = "";
	//手机号码
	private String serialNumber = "";
	//产品操作标示
	private String optType = "";
	//是否补换卡
	private String isChangeCard = "";
	//主卡号码
	private String masterCardTel = "";
	//入网类型
	private String userType = "";
	//是否后激活订单（0：普通订单 1：后激活订单）
	private String LaterActFlag = "";
	
	
	public String getLaterActFlag() {
		return LaterActFlag;
	}
	public void setLaterActFlag(String laterActFlag) {
		LaterActFlag = laterActFlag;
	}
	
	//配件类节点
	//终端品牌编码
	private String resourcesBrand = "";
//	终端型号编码
	private String resourcesModel = "";
//	终端机型编码
	private String resourcesTypeId = "";
//	终端颜色编码
	private String resourcesColor = "";
	
	//号码信息
	private CenterMallGoodsPhoneInfo phoneInfo = null;
	/*主副标识（1：主号；2：副号）*/
	private String numType;
	
	public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
	}
	public CenterMallGoodsPhoneInfo getPhoneInfo() {
		return phoneInfo;
	}
	public void setPhoneInfo(CenterMallGoodsPhoneInfo phoneInfo) {
		this.phoneInfo = phoneInfo;
	}
	public String getResourcesBrand() {
		return resourcesBrand;
	}
	public void setResourcesBrand(String resourcesBrand) {
		this.resourcesBrand = resourcesBrand;
	}
	public String getResourcesModel() {
		return resourcesModel;
	}
	public void setResourcesModel(String resourcesModel) {
		this.resourcesModel = resourcesModel;
	}
	public String getResourcesTypeId() {
		return resourcesTypeId;
	}
	public void setResourcesTypeId(String resourcesTypeId) {
		this.resourcesTypeId = resourcesTypeId;
	}
	public String getResourcesColor() {
		return resourcesColor;
	}
	public void setResourcesColor(String resourcesColor) {
		this.resourcesColor = resourcesColor;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCertLoseTime() {
		return certLoseTime;
	}
	public void setCertLoseTime(String certLoseTime) {
		this.certLoseTime = certLoseTime;
	}
	public String getCertAddr() {
		return certAddr;
	}
	public void setCertAddr(String certAddr) {
		this.certAddr = certAddr;
	}
	public String getRefereeName() {
		return refereeName;
	}
	public void setRefereeName(String refereeName) {
		this.refereeName = refereeName;
	}
	public String getRefereeNum() {
		return refereeNum;
	}
	public void setRefereeNum(String refereeNum) {
		this.refereeNum = refereeNum;
	}
	public String getDevelopCode() {
		return developCode;
	}
	public void setDevelopCode(String developCode) {
		this.developCode = developCode;
	}
	public String getDevelopName() {
		return developName;
	}
	public void setDevelopName(String developName) {
		this.developName = developName;
	}
//	public String getPhoneNum() {
//		return phoneNum;
//	}
//	public void setPhoneNum(String phoneNum) {
//		this.phoneNum = phoneNum;
//	}
	public String getReliefPresFlag() {
		return reliefPresFlag;
	}
	public void setReliefPresFlag(String reliefPresFlag) {
		this.reliefPresFlag = reliefPresFlag;
	}
	public String getSaleMode() {
		return saleMode;
	}
	public void setSaleMode(String saleMode) {
		this.saleMode = saleMode;
	}
	public String getSimId() {
		return simId;
	}
	public void setSimId(String simId) {
		this.simId = simId;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductNet() {
		return productNet;
	}
	public void setProductNet(String productNet) {
		this.productNet = productNet;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getFirstMonBillMode() {
		return firstMonBillMode;
	}
	public void setFirstMonBillMode(String firstMonBillMode) {
		this.firstMonBillMode = firstMonBillMode;
	}
	public String getSerType() {
		return serType;
	}
	public void setSerType(String serType) {
		this.serType = serType;
	}
	
	public List<CenterMallSubProdInfo> getSubProdInfo() {
		return subProdInfo;
	}
	public void setSubProdInfo(List<CenterMallSubProdInfo> subProdInfo) {
		this.subProdInfo = subProdInfo;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<CenterMallActivityInfo> getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(List<CenterMallActivityInfo> activityInfo) {
		this.activityInfo = activityInfo;
	}
	public String getIsCustomized() {
		return isCustomized;
	}
	public void setIsCustomized(String isCustomized) {
		this.isCustomized = isCustomized;
	}
	public CenterMallResourcesInfo getResourcesInfo() {
		return resourcesInfo;
	}
	public void setResourcesInfo(CenterMallResourcesInfo resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}
	public String getProPacCode() {
		return proPacCode;
	}
	public void setProPacCode(String proPacCode) {
		this.proPacCode = proPacCode;
	}
	public String getProPacDesc() {
		return proPacDesc;
	}
	public void setProPacDesc(String proPacDesc) {
		this.proPacDesc = proPacDesc;
	}
	public String getPartsType() {
		return partsType;
	}
	public void setPartsType(String partsType) {
		this.partsType = partsType;
	}
	public String getBipCode() {
		return bipCode;
	}
	public void setBipCode(String bipCode) {
		this.bipCode = bipCode;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getIsChangeCard() {
		return isChangeCard;
	}
	public void setIsChangeCard(String isChangeCard) {
		this.isChangeCard = isChangeCard;
	}
	public String getMasterCardTel() {
		return masterCardTel;
	}
	public void setMasterCardTel(String masterCardTel) {
		this.masterCardTel = masterCardTel;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getSubAppType() {
		return subAppType;
	}
	public void setSubAppType(String subAppType) {
		this.subAppType = subAppType;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getRealnameType() {
		return realnameType;
	}
	public void setRealnameType(String realnameType) {
		this.realnameType = realnameType;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDevelopDepId() {
		return developDepId;
	}
	public void setDevelopDepId(String developDepId) {
		this.developDepId = developDepId;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	
	
}
