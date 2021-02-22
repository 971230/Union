/**
 * 
 */
package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.params.zb.vo.PhoneInfo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-12-28
 * @see 总部号卡副卡模版
 *
 */
public class GoodsAttHK_FK extends GoodsAtt implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "号卡客户姓名", type = "String", isNecessary = "Y", desc = "CustomerName：号卡客户姓名")
	private String CustomerName;

	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "CertType：老用户非必填")
	private String CertType;

	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "CertNum：老用户非必填")
	private String CertNum;

	@ZteSoftCommentAnnotationParam(name = "客户类型", type = "String", isNecessary = "Y", desc = "CustomerType：客户类型")
	private String CustomerType;
	
	@ZteSoftCommentAnnotationParam(name = "集团ID", type = "String", isNecessary = "Y", desc = "groupId：集团ID")
	private String groupId;
	
	@ZteSoftCommentAnnotationParam(name = "应用类别", type = "String", isNecessary = "Y", desc = "appType：应用类别")
	private String appType;
	
	@ZteSoftCommentAnnotationParam(name = "应用子类", type = "String", isNecessary = "Y", desc = "subAppType：应用子类")
	private String subAppType;
	
	@ZteSoftCommentAnnotationParam(name = "证件失效时间", type = "String", isNecessary = "N", desc = "CertNum：YYYY-MM-DD HH24:MI:SS,老用户非必填")
	private String CertLoseTime;

	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "N", desc = "CertAddr：证件地址")
	private String CertAddr;

	@ZteSoftCommentAnnotationParam(name = "认证类型", type = "String", isNecessary = "Y", desc = "CheckType:认证类型")
	private String CheckType;
	
	@ZteSoftCommentAnnotationParam(name = "实名认证类型", type = "String", isNecessary = "N", desc = "realnameType：实名认证类型")
	private String RealnameType;
	
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "Y", desc = "性别，固定长度1位， M：男， F：女")
	private String Sex;
	
	@ZteSoftCommentAnnotationParam(name = "推荐人名称", type = "String", isNecessary = "N", desc = "RefereeName：推荐人名称")
	private String RefereeName;

	@ZteSoftCommentAnnotationParam(name = "推荐人号码（联系方式）", type = "String", isNecessary = "N", desc = "RefereeNum：推荐人号码（联系方式）")
	private String RefereeNum;

	@ZteSoftCommentAnnotationParam(name = "发展人编码", type = "String", isNecessary = "N", desc = "DevelopCode：发展人编码")
	private String DevelopCode;

	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "N", desc = "DevelopName：发展人名称")
	private String DevelopName;

	@ZteSoftCommentAnnotationParam(name = "发展人部门", type = "String", isNecessary = "N", desc = "developDepId：发展人部门")
	private String DevelopDepId;

	@ZteSoftCommentAnnotationParam(name = "号码信息", type = "String", isNecessary = "Y", desc = "phoneInfo：号码信息")
	private PhoneInfo phoneInfo;
	
	@ZteSoftCommentAnnotationParam(name = "减免预存标记", type = "String", isNecessary = "Y", desc = "ReliefPresFlag：减免预存标记")
	private String ReliefPresFlag;

	@ZteSoftCommentAnnotationParam(name = "销售方式", type = "String", isNecessary = "N", desc = "SaleMode：销售方式")
	private String SaleMode;

	@ZteSoftCommentAnnotationParam(name = "SIM卡号", type = "String", isNecessary = "N", desc = "SimId：SIM卡号")
	private String SimId;

	@ZteSoftCommentAnnotationParam(name = "卡类型", type = "String", isNecessary = "N", desc = "CardType：卡类型")
	private String CardType;

	@ZteSoftCommentAnnotationParam(name = "入网类型", type = "String", isNecessary = "N", desc = "userType：入网类型")
	private String UserType;
	
	@ZteSoftCommentAnnotationParam(name = "主副标识", type = "String", isNecessary = "N", desc = "numType：主副标识")
	private String NumType;
	
	@ZteSoftCommentAnnotationParam(name = "商品类型", type = "String", isNecessary = "N", desc = "goodsType：商品类型")
	private String GoodsType;
	
	@ZteSoftCommentAnnotationParam(name = "产品编码", type = "String", isNecessary = "Y", desc = "ProductCode：产品编码")
	private String ProductCode;

	@ZteSoftCommentAnnotationParam(name = "产品名称", type = "String", isNecessary = "Y", desc = "ProductName：产品名称")
	private String ProductName;

	@ZteSoftCommentAnnotationParam(name = "产品类别", type = "String", isNecessary = "N", desc = "ProductType：产品类别")
	private String ProductType;

	@ZteSoftCommentAnnotationParam(name = "产品网别", type = "String", isNecessary = "Y", desc = "ProductNet：产品网别")
	private String ProductNet;

	@ZteSoftCommentAnnotationParam(name = "产品品牌", type = "String", isNecessary = "Y", desc = "ProductBrand：产品品牌")
	private String ProductBrand;

	@ZteSoftCommentAnnotationParam(name = "可选包信息", type = "List", isNecessary = "Y", desc = "PackageInfo：可选包信息")
	private List<Package> Package;
	
	@ZteSoftCommentAnnotationParam(name = "首月资费方式 ", type = "String", isNecessary = "N", desc = "FirstMonBillMode：首月资费方式 ")
	private String FirstMonBillMode;

	@ZteSoftCommentAnnotationParam(name = "付费类型", type = "String", isNecessary = "Y", desc = "SerType：付费类型")
	private String SerType;
	
	@ZteSoftCommentAnnotationParam(name = "附加产品信息", type = "String", isNecessary = "N", desc = "SubProdInfo：附加产品信息")
	private List<SubProd> SubProdInfo;
	
	@ZteSoftCommentAnnotationParam(name = "合约信息", type = "String", isNecessary = "N", desc = "ActivityInfo：合约信息")
	private List<ActivityInfo> ActivityInfo;

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
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

	public String getCustomerType() {
		return CustomerType;
	}

	public void setCustomerType(String customerType) {
		CustomerType = customerType;
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

	public String getCertLoseTime() {
		return CertLoseTime;
	}

	public void setCertLoseTime(String certLoseTime) {
		CertLoseTime = certLoseTime;
	}

	public String getCertAddr() {
		return CertAddr;
	}

	public void setCertAddr(String certAddr) {
		CertAddr = certAddr;
	}

	public String getCheckType() {
		return CheckType;
	}

	public void setCheckType(String checkType) {
		CheckType = checkType;
	}

	public String getRealnameType() {
		return RealnameType;
	}

	public void setRealnameType(String realnameType) {
		RealnameType = realnameType;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
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

	public String getDevelopDepId() {
		return DevelopDepId;
	}

	public void setDevelopDepId(String developDepId) {
		DevelopDepId = developDepId;
	}

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

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public String getNumType() {
		return NumType;
	}

	public void setNumType(String numType) {
		NumType = numType;
	}

	public String getGoodsType() {
		return GoodsType;
	}

	public void setGoodsType(String goodsType) {
		GoodsType = goodsType;
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

	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
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

	public List<Package> getPackage() {
		return Package;
	}

	public void setPackage(List<Package> package1) {
		Package = package1;
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

	public List<SubProd> getSubProdInfo() {
		return SubProdInfo;
	}

	public void setSubProdInfo(List<SubProd> subProdInfo) {
		SubProdInfo = subProdInfo;
	}

	public List<ActivityInfo> getActivityInfo() {
		return ActivityInfo;
	}

	public void setActivityInfo(List<ActivityInfo> activityInfo) {
		ActivityInfo = activityInfo;
	}
	
}