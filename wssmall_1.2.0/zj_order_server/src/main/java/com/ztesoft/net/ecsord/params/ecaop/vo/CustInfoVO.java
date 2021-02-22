package com.ztesoft.net.ecsord.params.ecaop.vo;

public class CustInfoVO {

	private String customer_name="";//客户名称
//	private String custType="";//客户标识;0,新客户,1,老客户
	private String custId="";//客户ID,老用户必传
	private String cert_type="";//证件类型,新用户必传
	private String cert_num="";//证件号码,新用户必传
	private String cert_addr="";//证件地址
	private String cert_nation="";//民族
	private String cert_issuedat="";//证件机关
	private String cert_expire_date="";//证件生效时间
	private String cert_effected_date="";//证件失效时间
	private String contact_name="";//联系人名称
	private String contact_phone="";//联系电话
	private String customer_adder="";//客户地址
	private String cert_verified="";//实名标识
	private String cert_sex="";//性别
	private String cert_birthday="";//生日
	private String req_swift_num="";//拍照流水号
	private String cert_num2="";//证件号码,新用户必传
	
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
//	public String getCustType() {
//		return custType;
//	}
//	public void setCustType(String custType) {
//		this.custType = custType;
//	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCert_type() {
		return cert_type;
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}
	public String getCert_num() {
		return cert_num;
	}
	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}
	public String getCert_addr() {
		return cert_addr;
	}
	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}
	public String getCert_nation() {
		return cert_nation;
	}
	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}
	public String getCert_issuedat() {
		return cert_issuedat;
	}
	public void setCert_issuedat(String cert_issuedat) {
		this.cert_issuedat = cert_issuedat;
	}
	public String getCert_expire_date() {
		return cert_expire_date;
	}
	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}
	public String getCert_effected_date() {
		return cert_effected_date;
	}
	public void setCert_effected_date(String cert_effected_date) {
		this.cert_effected_date = cert_effected_date;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getCustomer_adder() {
		return customer_adder;
	}
	public void setCustomer_adder(String customer_adder) {
		this.customer_adder = customer_adder;
	}
	public String getCert_verified() {
		return cert_verified;
	}
	public void setCert_verified(String cert_verified) {
		this.cert_verified = cert_verified;
	}
	public String getCert_sex() {
		return cert_sex;
	}
	public void setCert_sex(String cert_sex) {
		this.cert_sex = cert_sex;
	}
	public String getCert_birthday() {
		return cert_birthday;
	}
	public void setCert_birthday(String cert_birthday) {
		this.cert_birthday = cert_birthday;
	}
	public String getReq_swift_num() {
		return req_swift_num;
	}
	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}
    public String getCert_num2() {
        return cert_num2;
    }
    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }
}
