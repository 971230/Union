package zte.net.ecsord.params.ems.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class EmsSender {
	@ZteSoftCommentAnnotationParam(name = "寄件人", type = "String", isNecessary = "Y", desc = "寄件人")
	private String name;
	@ZteSoftCommentAnnotationParam(name = "寄件人邮编", type = "String", isNecessary = "Y", desc = "寄件人邮编")
	private String postCode;
	@ZteSoftCommentAnnotationParam(name = "寄件人电话", type = "String", isNecessary = "N", desc = "寄件人电话")
	private String phone;
	@ZteSoftCommentAnnotationParam(name = "寄件人手机", type = "String", isNecessary = "N", desc = "寄件人手机")
	private String mobile;
	@ZteSoftCommentAnnotationParam(name = "寄件省", type = "String", isNecessary = "Y", desc = "寄件省，使用国标全称")
	private String prov;
	@ZteSoftCommentAnnotationParam(name = "寄件市", type = "String", isNecessary = "Y", desc = "寄件市，使用国标全称")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "寄件区县", type = "String", isNecessary = "N", desc = "寄件区县，使用国标全称")
	private String county;
	@ZteSoftCommentAnnotationParam(name = "寄件地址", type = "String", isNecessary = "Y", desc = "寄件地址")
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}