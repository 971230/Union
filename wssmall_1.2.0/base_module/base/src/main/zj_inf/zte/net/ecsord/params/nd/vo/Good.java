package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Good implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5632694736087104111L;

	@ZteSoftCommentAnnotationParam(name="商品包ID",type="String",isNecessary="Y",desc="packageId：商品包ID")
	private String packageId;

	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="productName：商品名称")
	private String productName;	

	@ZteSoftCommentAnnotationParam(name="套餐名称",type="String",isNecessary="N",desc="packageName：套餐名称")
	private String packageName;

	@ZteSoftCommentAnnotationParam(name="商品包类型",type="String",isNecessary="Y",desc="packType：商品包类型")
	private String packType;

	@ZteSoftCommentAnnotationParam(name="号码预存款",type="String",isNecessary="N",desc="gradeDeposits：号码预存款")
	private String gradeDeposits;

	@ZteSoftCommentAnnotationParam(name="活动类型（取实物记录）",type="String",isNecessary="N",desc="buyMode：活动类型（取实物记录）")
	private String buyMode;

	@ZteSoftCommentAnnotationParam(name="机型（取实物记录）",type="String",isNecessary="N",desc="handsetType：机型（取实物记录）")
	private String handsetType;

	@ZteSoftCommentAnnotationParam(name="颜色",type="String",isNecessary="N",desc="handsetColor：颜色")
	private String handsetColor;

	@ZteSoftCommentAnnotationParam(name="合约期",type="String",isNecessary="N",desc="agreementDate：合约期")
	private String agreementDate;

	@ZteSoftCommentAnnotationParam(name="首月资费方式",type="String",isNecessary="N",desc="firstFeeType：首月资费方式")
	private String firstFeeType;

	@ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="N",desc="mobileTel：号码")
	private String mobileTel;

	@ZteSoftCommentAnnotationParam(name="开户人姓名",type="String",isNecessary="N",desc="buyerName：开户人姓名")
	private String buyerName;

	@ZteSoftCommentAnnotationParam(name="开户人证件类型",type="String",isNecessary="N",desc="buyerCardType：开户人证件类型")
	private String buyerCardType;

	@ZteSoftCommentAnnotationParam(name="开户人证件号码",type="String",isNecessary="N",desc="buyerCardNo：开户人证件号码")
	private String buyerCardNo;

	@ZteSoftCommentAnnotationParam(name="开户人证件有效期",type="String",isNecessary="N",desc="buyerCardExp：开户人证件有效期")
	private String buyerCardExp;

	@ZteSoftCommentAnnotationParam(name="开户人证件地址",type="String",isNecessary="N",desc="buyerCardAddress：开户人证件地址")
	private String buyerCardAddress;

	@ZteSoftCommentAnnotationParam(name="大小卡",type="String",isNecessary="N",desc="bscard：大小卡")
	private String bscard;

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackType() {
		return packType;
	}

	public void setPackType(String packType) {
		this.packType = packType;
	}

	public String getGradeDeposits() {
		return gradeDeposits;
	}

	public void setGradeDeposits(String gradeDeposits) {
		this.gradeDeposits = gradeDeposits;
	}

	public String getBuyMode() {
		return buyMode;
	}

	public void setBuyMode(String buyMode) {
		this.buyMode = buyMode;
	}

	public String getHandsetType() {
		return handsetType;
	}

	public void setHandsetType(String handsetType) {
		this.handsetType = handsetType;
	}

	public String getHandsetColor() {
		return handsetColor;
	}

	public void setHandsetColor(String handsetColor) {
		this.handsetColor = handsetColor;
	}

	public String getAgreementDate() {
		return agreementDate;
	}

	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}

	public String getFirstFeeType() {
		return firstFeeType;
	}

	public void setFirstFeeType(String firstFeeType) {
		this.firstFeeType = firstFeeType;
	}

	public String getMobileTel() {
		return mobileTel;
	}

	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerCardType() {
		return buyerCardType;
	}

	public void setBuyerCardType(String buyerCardType) {
		this.buyerCardType = buyerCardType;
	}

	public String getBuyerCardNo() {
		return buyerCardNo;
	}

	public void setBuyerCardNo(String buyerCardNo) {
		this.buyerCardNo = buyerCardNo;
	}

	public String getBuyerCardExp() {
		return buyerCardExp;
	}

	public void setBuyerCardExp(String buyerCardExp) {
		this.buyerCardExp = buyerCardExp;
	}

	public String getBuyerCardAddress() {
		return buyerCardAddress;
	}

	public void setBuyerCardAddress(String buyerCardAddress) {
		this.buyerCardAddress = buyerCardAddress;
	}

	public String getBscard() {
		return bscard;
	}

	public void setBscard(String bscard) {
		this.bscard = bscard;
	}
	
	
	
}
