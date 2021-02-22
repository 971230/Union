/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version
 * @see 新客户信息
 * 
 */
public class NewCustInfoReqVo implements Serializable {

	private String certType; // Y 证件类型 参考附录证件类型说明
	private String certNum; // Y 证件号码
	private String certAdress; // Y 证件地址
	private String customerName; // Y 客户名称（不能全数字）
	private String certExpireDate; // Y 格式：yyyymmdd 证件失效日期(考虑默认)
	private String contactPerson; // N 联系人（不能全数字）
	private String contactPhone; // Y 联系人电话>6位
	private String contactAddress; // N 通讯地址
	private String custType; // N 客户类型： 01：个人客户 02：集团客户
	private List<CustRemarkReqVo> customerRemark; // N 客户备注
	
	public String getCertType() {
		if (StringUtils.isBlank(certType)) return null;
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNum() {
		if (StringUtils.isBlank(certNum)) return null;
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getCertAdress() {
		if (StringUtils.isBlank(certAdress)) return null;
		return certAdress;
	}
	public void setCertAdress(String certAdress) {
		this.certAdress = certAdress;
	}
	public String getCustomerName() {
		if (StringUtils.isBlank(customerName)) return null;
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCertExpireDate() {
		if (StringUtils.isBlank(certExpireDate)) return null;
		return certExpireDate;
	}
	public void setCertExpireDate(String certExpireDate) {
		this.certExpireDate = certExpireDate;
	}
	public String getContactPerson() {
		if (StringUtils.isBlank(contactPerson)) return null;
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		if (StringUtils.isBlank(contactPhone)) return null;
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactAddress() {
		if (StringUtils.isBlank(contactAddress)) return null;
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getCustType() {
		if (StringUtils.isBlank(custType)) return null;
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public List<CustRemarkReqVo> getCustomerRemark() {
		if (customerRemark==null || customerRemark.size() <= 0) return null;
		return customerRemark;
	}
	public void setCustomerRemark(List<CustRemarkReqVo> customerRemark) {
		this.customerRemark = customerRemark;
	}
	
}
