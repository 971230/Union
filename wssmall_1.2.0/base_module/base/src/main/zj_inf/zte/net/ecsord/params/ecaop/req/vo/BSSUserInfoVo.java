/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 用户信息
 * 
 */
public class BSSUserInfoVo implements Serializable {
	
	
	private String UserName;//Y 用户名称---同客户名
	private String UserPasswd;//N用户密码---手机号后六位
	private String PackID;//Y产品ID---主产品ID
	private String UserAddr;//Y用户地址---同证件地址
	private String GuarantorType;//N担保类型01 担保人 02 担保金 03 零信用度 04 无担保 05 社保卡担保 06 单位担保 07银行信贷模式担保 08 银行/保险公司担保
	private String GuaratorID;// N 担保信息参数 根据担保类型确定 如担保类型为01 则填担保人客户ID 如担保类型为02
	// 则填退款方式（11 按年，12 一次性） 如担保类型为03 则置空 如担保类型为04
	// 则填无担保原因 如担保类型为05 则填社保卡卡号 如担保类型为06
	// 则填“单位名称，单位编码，合同协议编码” 如担保类型为07 则填“银行名称，银行编码
	// 如担保类型为08 则填“银行或保险公司名称，银行或保险公司编码，合同协议编码”
	// 如担保类型为09
	// 则填“银行公司名称,银行公司编码,合同协议编码,冻结存款流水号,冻结金额(单位：元)”
	// 如担保类型为10 则填“担保公司名称，担保公司编码，合同协议编码”
	// 如担保类型为11
	// 则填“单位名称，单位编码，银行名称，银行编码，合同协议编码，冻结金额（单位：元）”如担保类型为12
	// 则填“担保公司名称，担保公司编码，合同协议编码”"
	private String GroupFlag; // N 集团标识 0：非集团用户 1：集团用户
	private String GroupID;// N 集团ID
	private String RecomDeparID;//N推荐部门标识
	private String RecomDeparName;//N推荐部门名称
	private String RecomPersonID;//N推荐人标识
	private String RecomPersonName;//N推荐人名称
	private String CreditVale; // N 信用等级，（3G后付费业务取值范围A、B、C）
	private String CheckCreditVale; // N 用户选择信用等级（3G后付费业务取值范围A、B、C）
	private String BrandID;//Y品牌ID--- W3G1 3G后付W3G3 3G预付W4G1 4G后付G000  2G后付OC00  2G预付
	private String ReleCountyID;//N归属县市
	private String UserOrigin;//N用户来源地
	private String Contract;//N合同号
	private String UserType; // N 用户类型 1：新用户 2：老用户
	private String CertExpireDate;//N证件有效期 格式：yyyymmdd---用户目前无需实名
	private String CertAddr;//N证件地址
	private String ContactAddr;//N通信地址
	private String ContactPhone;//N联系电话
	private String BillSendType;//N寄送账单方式—传空1-寄送，2-不寄送
	private String TerminalId;//自备机终端串号
	private String BillSendAddr;//N寄送地址
	private List<BSSUserRemarkVo> UserRemark ;//N用户备注
	
	public String getUserName() {
		if (StringUtils.isBlank(UserName)) return null;
		return UserName;
	}
	public void setUserName(String userName) {
		this.UserName = userName;
	}
	public String getUserPasswd() {
		if (StringUtils.isBlank(UserPasswd)) return null;
		return UserPasswd;
	}
	public void setUserPasswd(String userPasswd) {
		this.UserPasswd = userPasswd;
	}
	public String getPackID() {
		if (StringUtils.isBlank(PackID)) return null;
		return PackID;
	}
	public void setPackID(String packID) {
		this.PackID = packID;
	}
	public String getUserAddr() {
		if (StringUtils.isBlank(UserAddr)) return null;
		return UserAddr;
	}
	public void setUserAddr(String userAddr) {
		this.UserAddr = userAddr;
	}
	public String getGuarantorType() {
		if (StringUtils.isBlank(GuarantorType)) return null;
		return GuarantorType;
	}
	public void setGuarantorType(String guarantorType) {
		this.GuarantorType = guarantorType;
	}
	public String getGuaratorID() {
		if (StringUtils.isBlank(GuaratorID)) return null;
		return GuaratorID;
	}
	public void setGuaratorID(String guaratorID) {
		this.GuaratorID = guaratorID;
	}
	public String getGroupFlag() {
		if (StringUtils.isBlank(GroupFlag)) return null;
		return GroupFlag;
	}
	public void setGroupFlag(String groupFlag) {
		this.GroupFlag = groupFlag;
	}
	public String getGroupId() {
		if (StringUtils.isBlank(GroupID)) return null;
		return GroupID;
	}
	public void setGroupId(String groupId) {
		this.GroupID = groupId;
	}
	public String getRecomDeparID() {
		if (StringUtils.isBlank(RecomDeparID)) return null;
		return RecomDeparID;
	}
	public void setRecomDeparID(String recomDeparID) {
		this.RecomDeparID = recomDeparID;
	}
	public String getRecomDeparName() {
		if (StringUtils.isBlank(RecomDeparName)) return null;
		return RecomDeparName;
	}
	public void setRecomDeparName(String recomDeparName) {
		this.RecomDeparName = recomDeparName;
	}
	public String getRecomPersonID() {
		if (StringUtils.isBlank(RecomPersonID)) return null;
		return RecomPersonID;
	}
	public void setRecomPersonID(String recomPersonID) {
		this.RecomPersonID = recomPersonID;
	}
	public String getRecomPersonName() {
		if (StringUtils.isBlank(RecomPersonName)) return null;
		return RecomPersonName;
	}
	public void setRecomPersonName(String recomPersonName) {
		this.RecomPersonName = recomPersonName;
	}
	public String getCreditVale() {
		if (StringUtils.isBlank(CreditVale)) return null;
		return CreditVale;
	}
	public void setCreditVale(String creditVale) {
		this.CreditVale = creditVale;
	}
	public String getCheckCreditVale() {
		if (StringUtils.isBlank(CheckCreditVale)) return null;
		return CheckCreditVale;
	}
	public void setCheckCreditVale(String checkCreditVale) {
		this.CheckCreditVale = checkCreditVale;
	}
	public String getBrandID() {
		if (StringUtils.isBlank(BrandID)) return null;
		return BrandID;
	}
	public void setBrandID(String brandID) {
		this.BrandID = brandID;
	}
	public String getReleCountyID() {
		if (StringUtils.isBlank(ReleCountyID)) return null;
		return ReleCountyID;
	}
	public void setReleCountyID(String releCountyID) {
		this.ReleCountyID = releCountyID;
	}
	public String getUserOrigin() {
		if (StringUtils.isBlank(UserOrigin)) return null;
		return UserOrigin;
	}
	public void setUserOrigin(String userOrigin) {
		this.UserOrigin = userOrigin;
	}
	public String getContract() {
		if (StringUtils.isBlank(Contract)) return null;
		return Contract;
	}
	public void setContract(String contract) {
		this.Contract = contract;
	}
	public String getUserType() {
		if (StringUtils.isBlank(UserType)) return null;
		return UserType;
	}
	public void setUserType(String userType) {
		this.UserType = userType;
	}
	public String getCertExpireDate() {
		if (StringUtils.isBlank(CertExpireDate)) return null;
		return CertExpireDate;
	}
	public void setCertExpireDate(String certExpireDate) {
		this.CertExpireDate = certExpireDate;
	}
	public String getCertAddr() {
		if (StringUtils.isBlank(CertAddr)) return null;
		return CertAddr;
	}
	public void setCertAddr(String certAddr) {
		CertAddr = certAddr;
	}
	public String getContactAddr() {
		if (StringUtils.isBlank(ContactAddr)) return null;
		return ContactAddr;
	}
	public void setContactAddr(String contactAddr) {
		ContactAddr = contactAddr;
	}
	public String getContactPhone() {
		if (StringUtils.isBlank(ContactPhone)) return null;
		return ContactPhone;
	}
	public void setContactPhone(String contactPhone) {
		ContactPhone = contactPhone;
	}
	public String getBillSendType() {
		if (StringUtils.isBlank(BillSendType)) return null;
		return BillSendType;
	}
	public void setBillSendType(String billSendType) {
		BillSendType = billSendType;
	}
	public String getTerminalId() {
		if (StringUtils.isBlank(TerminalId)) return null;
		return TerminalId;
	}
	public void setTerminalId(String terminalId) {
		TerminalId = terminalId;
	}
	public String getBillSendAddr() {
		if (StringUtils.isBlank(BillSendAddr)) return null;
		return BillSendAddr;
	}
	public void setBillSendAddr(String billSendAddr) {
		BillSendAddr = billSendAddr;
	}
	public List<BSSUserRemarkVo> getUserRemark() {
		if (UserRemark==null || UserRemark.size() <= 0) return null;
		return UserRemark;
	}
	public void setUserRemark(List<BSSUserRemarkVo> userRemark) {
		this.UserRemark = userRemark;
	}
	public String getGroupID() {
		return GroupID;
	}
	public void setGroupID(String groupID) {
		GroupID = groupID;
	}

}
