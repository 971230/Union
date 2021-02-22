package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.params.zb.vo.Para;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CustomerInfoResponseVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "证件类型，参考附录证件类型", type = "String", isNecessary = "Y", desc = "certType：证件类型，参考附录证件类型")
	private String certType;
	
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "certNum:证件号码")
	private String certNum;
	
	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "Y", desc = "certAdress：证件地址")
	private String certAdress;
	
	@ZteSoftCommentAnnotationParam(name = "客户类型", type = "String", isNecessary = "Y", desc = "customerType: 01－个人客户 02－集团客户")
	private String customerType;
	
	@ZteSoftCommentAnnotationParam(name = "客户级别", type = "String", isNecessary = "Y", desc = "customerLevel:客户级别")
	private String customerLevel;
	
	@ZteSoftCommentAnnotationParam(name = "客户地域", type = "String", isNecessary = "Y", desc = "customerLoc：客户地域")
	private String customerLoc;
	
	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "Y", desc = "customerName：客户名称")
	private String customerName;
	
	@ZteSoftCommentAnnotationParam(name = "客户标识", type = "String", isNecessary = "Y", desc = "custId：客户标识")
	private String custId;
	
	@ZteSoftCommentAnnotationParam(name = "证件失效日期", type = "String", isNecessary = "Y", desc = "certExpireDate：证件失效日期:格式：yyyymmdd")
	private String certExpireDate;
	
	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "contactPerson：联系人")
	private String contactPerson;
	
	@ZteSoftCommentAnnotationParam(name = "联系人电话", type = "String", isNecessary = "Y", desc = "contactPhone：联系人电话")
	private String contactPhone;
	
	@ZteSoftCommentAnnotationParam(name = "是否欠费标识 ", type = "String", isNecessary = "Y", desc = "arrearageFlag：是否欠费标识 0：不欠费1：欠费")
	private String arrearageFlag;
	
//	@ZteSoftCommentAnnotationParam(name = "欠费信息", type = "List", isNecessary = "N", desc = "arrearageMess：欠费信息")
//	private List<ArrearageMess> arrearageMess;
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="List",isNecessary="N",desc="para：保留字段")
	private List<Para> para;
	
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

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getCustomerLoc() {
		return customerLoc;
	}

	public void setCustomerLoc(String customerLoc) {
		this.customerLoc = customerLoc;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getArrearageFlag() {
		return arrearageFlag;
	}

	public void setArrearageFlag(String arrearageFlag) {
		this.arrearageFlag = arrearageFlag;
	}


//	public List<ArrearageMess> getArrearageMess() {
//		return arrearageMess;
//	}
//
//	public void setArrearageMess(List<ArrearageMess> arrearageMess) {
//		this.arrearageMess = arrearageMess;
//	}

	public List<Para> getPara() {
		return para;
	}

	public void setPara(List<Para> para) {
		this.para = para;
	}
	
	
	

}
