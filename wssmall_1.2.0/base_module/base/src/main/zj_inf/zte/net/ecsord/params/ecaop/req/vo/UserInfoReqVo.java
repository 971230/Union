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
public class UserInfoReqVo implements Serializable {

	private String userType; // Y 用户类型 1：新用户 2：老用户
	private String bipType; // Y 业务类型： 1：号卡类业务 2：合约类业务 3：上网卡 4：上网本 5：其它配件类
							// 6：自备机合约类业务
	private String is3G; // Y 0-2G 1-3G 2-4G
	private String serType; // Y 受理类型 1：后付费 2：预付费 3：准预付费
	private String packageTag; // Y 套包销售标记 0：非套包销售 1：套包销售
	private String firstMonBillMode; // N 首月付费模式 01：标准资费（免首月月租） 02：全月套餐 03：套餐减半
	private String checkType; // N 认证类型：01：本地认证 02：公安认证 03：二代证读卡器
	private String creditVale; // N 信用等级，（3G后付费业务取值范围A、B、C）
	private String checkCreditVale; // N 用户选择信用等级（3G后付费业务取值范围A、B、C）
	private String userPwd; // N 用户密码
	private List<ProductInfoReqVo> product; // + 开户时选择的产品信息
	private String groupFlag; // N 集团标识 0：非集团用户 1：集团用户
	private String groupId;// N 集团ID
	private String appType;// N 应用类别，当加入具体集团时为必填 0：行业应用 1：非行业应用
	private String subAppType;// N 应用子类别 参考附录应用子类别说明
	private String guarantorType;// N 担保方式 参见附录
	private String guaratorID;// N 担保信息参数 根据担保类型确定 如担保类型为01 则填担保人客户ID 如担保类型为02
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
	private String guCertType;// N 被担保人证件类型
	private String guCertNum;// N 被担保人证件号码
	private PayInfoReqVo payInfo;// Y 支付信息
	private List<ActivityInfoReqVo> activityInfo; // 开户时活动信息
	
	public String getGroupFlag() {
		if (StringUtils.isBlank(groupFlag)) return null;
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getGroupId() {
		if (StringUtils.isBlank(groupId)) return null;
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAppType() {
		if (StringUtils.isBlank(appType)) return null;
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getSubAppType() {
		if (StringUtils.isBlank(subAppType)) return null;
		return subAppType;
	}

	public void setSubAppType(String subAppType) {
		this.subAppType = subAppType;
	}

	public String getGuarantorType() {
		if (StringUtils.isBlank(guarantorType)) return null;
		return guarantorType;
	}

	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}

	public String getGuaratorID() {
		if (StringUtils.isBlank(guaratorID)) return null;
		return guaratorID;
	}

	public void setGuaratorID(String guaratorID) {
		this.guaratorID = guaratorID;
	}

	public String getGuCertType() {
		if (StringUtils.isBlank(guCertType)) return null;
		return guCertType;
	}

	public void setGuCertType(String guCertType) {
		this.guCertType = guCertType;
	}

	public String getGuCertNum() {
		if (StringUtils.isBlank(guCertNum)) return null;
		return guCertNum;
	}

	public void setGuCertNum(String guCertNum) {
		this.guCertNum = guCertNum;
	}

	public PayInfoReqVo getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(PayInfoReqVo payInfo) {
		this.payInfo = payInfo;
	}

	public List<ActivityInfoReqVo> getActivityInfo() {
		if (activityInfo==null || activityInfo.size() <= 0) return null;
		return activityInfo;
	}

	public void setActivityInfo(List<ActivityInfoReqVo> activityInfo) {
		this.activityInfo = activityInfo;
	}

	public String getUserType() {
		if (StringUtils.isBlank(userType)) return null;
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getBipType() {
		if (StringUtils.isBlank(bipType)) return null;
		return bipType;
	}

	public void setBipType(String bipType) {
		this.bipType = bipType;
	}

	public String getIs3G() {
		if (StringUtils.isBlank(is3G)) return null;
		return is3G;
	}

	public void setIs3G(String is3g) {
		is3G = is3g;
	}

	public String getSerType() {
		if (StringUtils.isBlank(serType)) return null;
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getPackageTag() {
		if (StringUtils.isBlank(packageTag)) return null;
		return packageTag;
	}

	public void setPackageTag(String packageTag) {
		this.packageTag = packageTag;
	}

	public String getFirstMonBillMode() {
		if (StringUtils.isBlank(firstMonBillMode)) return null;
		return firstMonBillMode;
	}

	public void setFirstMonBillMode(String firstMonBillMode) {
		this.firstMonBillMode = firstMonBillMode;
	}

	public String getCheckType() {
		if (StringUtils.isBlank(checkType)) return null;
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCreditVale() {
		if (StringUtils.isBlank(creditVale)) return null;
		return creditVale;
	}

	public void setCreditVale(String creditVale) {
		this.creditVale = creditVale;
	}

	public String getCheckCreditVale() {
		if (StringUtils.isBlank(checkCreditVale)) return null;
		return checkCreditVale;
	}

	public void setCheckCreditVale(String checkCreditVale) {
		this.checkCreditVale = checkCreditVale;
	}

	public String getUserPwd() {
		if (StringUtils.isBlank(userPwd)) return null;
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public List<ProductInfoReqVo> getProduct() {
		if (product == null || product.size() <= 0) return null;
		return product;
	}

	public void setProduct(List<ProductInfoReqVo> product) {
		this.product = product;
	}

}
