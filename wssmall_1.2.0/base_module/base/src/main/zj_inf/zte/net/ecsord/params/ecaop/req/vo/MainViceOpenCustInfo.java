package zte.net.ecsord.params.ecaop.req.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainViceOpenCustInfo {
	
	@ZteSoftCommentAnnotationParam(name = "老客户ID（副卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String custId;
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "")
	private String certType;
	@ZteSoftCommentAnnotationParam(name = "证件号码（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String certNum;
	@ZteSoftCommentAnnotationParam(name = "证件地址（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String certAdress;
	@ZteSoftCommentAnnotationParam(name = "客户名称（不能全数字）（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String customerName;
	@ZteSoftCommentAnnotationParam(name = "性别（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String sex;
	@ZteSoftCommentAnnotationParam(name = "格式：yyyymmdd证件失效日期(考虑默认)（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String certExpireDate;
	@ZteSoftCommentAnnotationParam(name = "联系人（不能全数字）（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String contactPerson;
	@ZteSoftCommentAnnotationParam(name = "联系人电话>6位（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String contactPhone;
	@ZteSoftCommentAnnotationParam(name = "通讯地址（主卡时必传）", type = "String", isNecessary = "Y", desc = "")
	private String contactAddress;
	@ZteSoftCommentAnnotationParam(name = "认证类型：01：本地认证02：公安认证03：二代证读卡器", type = "String", isNecessary = "Y", desc = "")
	private String checkType;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getCertAdress() {
		return certAdress;
	}

	public void setCertAdress(String certAdress) {
		this.certAdress = certAdress;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCertExpireDate() {
		return certExpireDate;
	}

	public void setCertExpireDate(String certExpireDate) {
		this.certExpireDate = certExpireDate;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

}
