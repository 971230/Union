/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author song.qi
 */
public class CustInfoModVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name = "客户标识 ", type = "String", isNecessary = "N", desc = "String(16)	客户标识")
	private String custId;
	@ZteSoftCommentAnnotationParam(name = "客户名称 ", type = "String", isNecessary = "Y", desc = "String(100)	客户名称")
	private String custName;
	@ZteSoftCommentAnnotationParam(name = "证件地址 ", type = "String", isNecessary = "Y", desc = "String(256)	证件地址")
	private String certAddr;
	@ZteSoftCommentAnnotationParam(name = "证件类型 ", type = "String", isNecessary = "N", desc = "String(2)	证件类型，参考附录证件类型")
	private String certType;
	@ZteSoftCommentAnnotationParam(name = "证件号码 ", type = "String", isNecessary = "N", desc = "String(50)	证件号码")
	private String certNum;
	@ZteSoftCommentAnnotationParam(name = "是否长期有效 ", type = "String", isNecessary = "N", desc = "String(2)	是否长期有效1：长期有效（不填默认有有效期）")
	private String cerValidFlag;
	@ZteSoftCommentAnnotationParam(name = "证件有效期", type = "String", isNecessary = "N", desc = "String(8)	证件有效期,格式YYYYMMDD")
	private String certExpireDate;
	@ZteSoftCommentAnnotationParam(name = "证件有效期", type = "String", isNecessary = "N", desc = "String(40)	联系电话")
	private String custPhone;
	@ZteSoftCommentAnnotationParam(name = "通信地址 ", type = "String", isNecessary = "N", desc = "String(256)	通信地址")
	private String postAddress;
	@ZteSoftCommentAnnotationParam(name = "联系人姓名 ", type = "String", isNecessary = "N", desc = "String(60)	联系人姓名")
	private String contactName;
	@ZteSoftCommentAnnotationParam(name = "联系人电话 ", type = "String", isNecessary = "N", desc = "String(40)	联系人电话")
	private String contactPhone;
	@ZteSoftCommentAnnotationParam(name = "客户证件电子图片链接 ", type = "String", isNecessary = "N", desc = "String(512)	客户证件电子图片链接(绝对地址)")
	private String custCertPictureUrl;
	@ZteSoftCommentAnnotationParam(name = "客户证件电子图片名称 ", type = "String", isNecessary = "N", desc = "String(256)	客户证件电子图片名称  :证件类型(证件类别编码，见附录)_证件号码_服务号码_YYYYMMDDHHMISS.XXX（后缀名）")
	private String custCertPictureName;
	@ZteSoftCommentAnnotationParam(name = "认证类型", type = "String", isNecessary = "N", desc = "String(2)	认证类型：01：本地认证02：公安认证03：二代证读卡器")
	private String checkType;

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCertAddr(String certAddr) {
		this.certAddr = certAddr;
	}

	public String getCertAddr() {
		return certAddr;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setCustCertPictureUrl(String custCertPictureUrl) {
		this.custCertPictureUrl = custCertPictureUrl;
	}

	public String getCustCertPictureUrl() {
		return custCertPictureUrl;
	}

	public void setCustCertPictureName(String custCertPictureName) {
		this.custCertPictureName = custCertPictureName;
	}

	public String getCustCertPictureName() {
		return custCertPictureName;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCheckType() {
		return checkType;
	}

	public String getCerValidFlag() {
		return cerValidFlag;
	}

	public void setCerValidFlag(String cerValidFlag) {
		this.cerValidFlag = cerValidFlag;
	}

	public String getCertExpireDate() {
		return certExpireDate;
	}

	public void setCertExpireDate(String certExpireDate) {
		this.certExpireDate = certExpireDate;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	
}
