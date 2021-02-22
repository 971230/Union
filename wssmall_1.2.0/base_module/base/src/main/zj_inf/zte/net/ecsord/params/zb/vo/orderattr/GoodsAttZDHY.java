package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.params.zb.vo.PhoneInfo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 商品附属信息:终端合约类
 * @author sguo
 *
 */
public class GoodsAttZDHY extends GoodsAtt implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "号卡客户姓名", type = "String", isNecessary = "Y", desc = "CustomerName：号卡客户姓名")
	private String UserType;

	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "CertType：老用户非必填")
	private String IsCustomized;

	@ZteSoftCommentAnnotationParam(name = "产品类型", type = "String", isNecessary = "N", desc = "ProductType：产品类型")
	private String ProductType;
	
	/*ZX add 2015-12-28 start*/
	@ZteSoftCommentAnnotationParam(name = "集团ID", type = "String", isNecessary = "Y", desc = "groupId：集团ID")
	private String groupId;
	
	@ZteSoftCommentAnnotationParam(name = "应用类别", type = "String", isNecessary = "Y", desc = "appType：应用类别")
	private String appType;
	
	@ZteSoftCommentAnnotationParam(name = "应用子类", type = "String", isNecessary = "Y", desc = "subAppType：应用子类")
	private String subAppType;
	/*ZX add 2015-12-28 end*/
	
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "CertType：证件类型")
	private String CertType;
	
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "CertNum：证件号码")
	private String CertNum;
	
	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "N", desc = "CertAddr：证件地址")
	private String CertAddr;
	
	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "N", desc = "CustomerName：客户名称")
	private String CustomerName;
	
	@ZteSoftCommentAnnotationParam(name = "客户类型", type = "String", isNecessary = "N", desc = "CustomerType：客户类型")
	private String CustomerType;
	
	@ZteSoftCommentAnnotationParam(name = "证件失效时间", type = "String", isNecessary = "N", desc = "CertLoseTime：证件失效时间")
	private String CertLoseTime;
	
	@ZteSoftCommentAnnotationParam(name = "推荐人", type = "String", isNecessary = "N", desc = "RefereeName：推荐人")
	private String RefereeName;
	
	/*ZX add 2015-12-28 start*/
	@ZteSoftCommentAnnotationParam(name = "实名认证类型", type = "String", isNecessary = "N", desc = "realnameType：实名认证类型")
	private String realnameType;
	/*ZX add 2015-12-28 end*/
	
	@ZteSoftCommentAnnotationParam(name = "推荐人号码（联系方式）", type = "String", isNecessary = "N", desc = "RefereeNum：推荐人号码（联系方式）")
	private String RefereeNum;
	
	@ZteSoftCommentAnnotationParam(name = "发展人编码", type = "String", isNecessary = "N", desc = "DevelopCode：发展人编码")
	private String DevelopCode;
	
	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "N", desc = "DevelopName：发展人名称")
	private String DevelopName;
	
	/*ZX add 2015-12-28 start*/
	@ZteSoftCommentAnnotationParam(name = "发展人部门", type = "String", isNecessary = "N", desc = "developDepId：发展人部门")
	private String developDepId;
	/*ZX add 2015-12-28 end*/
	
//	
//	@ZteSoftCommentAnnotationParam(name = "订购号码", type = "String", isNecessary = "N", desc = "PhoneNum：订购号码")
//	private String PhoneNum;
	
	@ZteSoftCommentAnnotationParam(name = "号码信息", type = "String", isNecessary = "Y", desc = "phoneInfo：号码信息")
	private PhoneInfo phoneInfo;
	
	@ZteSoftCommentAnnotationParam(name = "减免预存标记", type = "String", isNecessary = "N", desc = "ReliefPresFlag：减免预存标记")
	private String ReliefPresFlag;
	
	@ZteSoftCommentAnnotationParam(name = "销售方式", type = "String", isNecessary = "N", desc = "SaleMode：销售方式")
	private String SaleMode;
	
	@ZteSoftCommentAnnotationParam(name = "SIM卡号", type = "String", isNecessary = "N", desc = "SimId：SIM卡号")
	private String SimId;
	
	@ZteSoftCommentAnnotationParam(name = "卡类型", type = "String", isNecessary = "N", desc = "CardType：卡类型")
	private String CardType;
	
	/*ZX add 2015-12-28 start*/
	@ZteSoftCommentAnnotationParam(name = "主副标识", type = "String", isNecessary = "N", desc = "numType：主副标识")
	private String numType;
	@ZteSoftCommentAnnotationParam(name = "商品类型", type = "String", isNecessary = "N", desc = "goodsType：商品类型")
	private String goodsType;
	/*ZX add 2015-12-28 end*/
	
	@ZteSoftCommentAnnotationParam(name = "产品编码（ESS编码）", type = "String", isNecessary = "N", desc = "ProductCode：产品编码（ESS编码）")
	private String ProductCode;
	
	@ZteSoftCommentAnnotationParam(name = "产品名称（套餐名称）", type = "String", isNecessary = "N", desc = "ProductName：产品名称（套餐名称）")
	private String ProductName;
	
	@ZteSoftCommentAnnotationParam(name = "网别", type = "String", isNecessary = "N", desc = "ProductNet：网别")
	private String ProductNet;
	
	@ZteSoftCommentAnnotationParam(name = "产品品牌", type = "String", isNecessary = "N", desc = "ProductBrand：产品品牌")
	private String ProductBrand;
	
	@ZteSoftCommentAnnotationParam(name = "首月资费方式", type = "String", isNecessary = "N", desc = "FirstMonBillMode：首月资费方式")
	private String FirstMonBillMode;
	
	@ZteSoftCommentAnnotationParam(name = "付费类型", type = "String", isNecessary = "N", desc = "SerType：付费类型")
	private String SerType;
	
	@ZteSoftCommentAnnotationParam(name = "终端信息", type = "String", isNecessary = "N", desc = "ResourcesInfo：终端信息")
	private Resources ResourcesInfo;
	
	@ZteSoftCommentAnnotationParam(name = "可选包信息", type = "String", isNecessary = "N", desc = "PackageInfo：附加产品信息")
	private List<Package> Package;
	
	@ZteSoftCommentAnnotationParam(name = "附加产品信息", type = "String", isNecessary = "N", desc = "SubProdInfo：可选包信息")
	private List<SubProd> SubProdInfo;
	
	@ZteSoftCommentAnnotationParam(name = "合约信息", type = "String", isNecessary = "N", desc = "ActivityInfo：合约信息")
	private List<Activity> ActivityInfo;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "认证类型", type = "String", isNecessary = "Y", desc = "CheckType:认证类型")
	private String CheckType;
	
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "Y", desc = "性别，固定长度1位， M：男， F：女")
	private String Sex;
	
	public String getCheckType(){
		return CheckType;
	}
	public void setCheckType(String checkType){
		this.CheckType = checkType;
	}
	
	public String getSex(){
		return Sex;
	}
	public void setSex(String sex){
		this.Sex = sex;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public String getIsCustomized() {
		return IsCustomized;
	}

	public void setIsCustomized(String isCustomized) {
		IsCustomized = isCustomized;
	}

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	public String getCertType() {
		return CertType;
	}

	public void setCertType(String certType) {
		CertType = certType;
	}

	public String getCertNum() {
		return CertNum;
	}

	public void setCertNum(String certNum) {
		CertNum = certNum;
	}

	public String getCertAddr() {
		return CertAddr;
	}

	public void setCertAddr(String certAddr) {
		CertAddr = certAddr;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getCustomerType() {
		return CustomerType;
	}

	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}

	public String getCertLoseTime() {
		return CertLoseTime;
	}

	public void setCertLoseTime(String certLoseTime) {
		CertLoseTime = certLoseTime;
	}

	public String getRefereeName() {
		return RefereeName;
	}

	public void setRefereeName(String refereeName) {
		RefereeName = refereeName;
	}

	public String getRefereeNum() {
		return RefereeNum;
	}

	public void setRefereeNum(String refereeNum) {
		RefereeNum = refereeNum;
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

//	public String getPhoneNum() {
//		return PhoneNum;
//	}
//
//	public void setPhoneNum(String phoneNum) {
//		PhoneNum = phoneNum;
//	}
	public PhoneInfo getPhoneInfo() {
		return phoneInfo;
	}

	public void setPhoneInfo(PhoneInfo phoneInfo) {
		this.phoneInfo = phoneInfo;
	}

	public String getReliefPresFlag() {
		return ReliefPresFlag;
	}

	public void setReliefPresFlag(String reliefPresFlag) {
		ReliefPresFlag = reliefPresFlag;
	}

	public String getSaleMode() {
		return SaleMode;
	}

	public void setSaleMode(String saleMode) {
		SaleMode = saleMode;
	}

	public String getSimId() {
		return SimId;
	}

	public void setSimId(String simId) {
		SimId = simId;
	}

	public String getCardType() {
		return CardType;
	}

	public void setCardType(String cardType) {
		CardType = cardType;
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductNet() {
		return ProductNet;
	}

	public void setProductNet(String productNet) {
		ProductNet = productNet;
	}

	public String getProductBrand() {
		return ProductBrand;
	}

	public void setProductBrand(String productBrand) {
		ProductBrand = productBrand;
	}

	public String getFirstMonBillMode() {
		return FirstMonBillMode;
	}

	public void setFirstMonBillMode(String firstMonBillMode) {
		FirstMonBillMode = firstMonBillMode;
	}

	public String getSerType() {
		return SerType;
	}

	public void setSerType(String serType) {
		SerType = serType;
	}

	public Resources getResourcesInfo() {
		return ResourcesInfo;
	}

	public void setResourcesInfo(Resources resourcesInfo) {
		ResourcesInfo = resourcesInfo;
	}

	public List<Package> getPackage() {
		return Package;
	}

	public void setPackage(List<Package> package1) {
		Package = package1;
	}

	public List<SubProd> getSubProdInfo() {
		return SubProdInfo;
	}

	public void setSubProdInfo(List<SubProd> subProdInfo) {
		SubProdInfo = subProdInfo;
	}

	public List<Activity> getActivityInfo() {
		return ActivityInfo;
	}

	public void setActivityInfo(List<Activity> activityInfo) {
		ActivityInfo = activityInfo;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
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
	public String getRealnameType() {
		return realnameType;
	}
	public void setRealnameType(String realnameType) {
		this.realnameType = realnameType;
	}
	public String getDevelopDepId() {
		return developDepId;
	}
	public void setDevelopDepId(String developDepId) {
		this.developDepId = developDepId;
	}
	public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
}
