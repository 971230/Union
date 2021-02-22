/**
 * 
 */
package zte.net.ecsord.params.bss.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.bss.resp.MobileCBssOutResp;


/**
 * @author ZX
 * CBssLoginCertReq.java
 * 2015-1-28
 */
public class MobileCBssOutReq extends ZteRequest<MobileCBssOutResp>{
	
	private String loginUser; // 登录用户
	private String loginPsw; // 登录密码（加密后的密文，加密方式问cBss系统）
	private String ProvinceCode="36"; // 省份ID（默认"36"浙江）
	private String psptId; // 身份证号
	private String customName; // 客户姓名（对应身份证号）
	private String contact; // 开户人姓名（机主姓名）
	private String contactPhone; // 开户号码
	private String psptEndDate; // 身份证有效期
	private String idTypeCode="1"; // 证件类型，（默认1-身份证）
	private String phone; // 收货人电话
	private String postAddress; // 通信地址必须大于八个字符
	private String serialNumber; // 需要办理业务的号码
	
	private String packFlag; // 移网类型（a:5元1GB闲时省内流量包; b:10元3GB闲时省内流量包 ;）
	private String order_time; // 订单时间
	
	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getPackFlag() {
		return packFlag;
	}

	public void setPackFlag(String packFlag) {
		this.packFlag = packFlag;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getLoginPsw() {
		return loginPsw;
	}

	public void setLoginPsw(String loginPsw) {
		this.loginPsw = loginPsw;
	}

	public String getProvinceCode() {
		return ProvinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		ProvinceCode = provinceCode;
	}

	public String getPsptId() {
		return psptId;
	}

	public void setPsptId(String psptId) {
		this.psptId = psptId;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getPsptEndDate() {
		return psptEndDate;
	}

	public void setPsptEndDate(String psptEndDate) {
		this.psptEndDate = psptEndDate;
	}

	public String getIdTypeCode() {
		return idTypeCode;
	}

	public void setIdTypeCode(String idTypeCode) {
		this.idTypeCode = idTypeCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.params.bss.req.mobilecbsslogincert";
	}
	
}
