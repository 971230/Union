package zte.net.ecsord.params.ems.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import params.ZteRequest;

public class LogisticsNumberGetReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6716893314209147254L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "账号（大客户号）", type = "String", isNecessary = "Y", desc = "账号（大客户号）")
	private String sysAccount;
	@ZteSoftCommentAnnotationParam(name = "密码（对接密码）", type = "String", isNecessary = "Y", desc = "密码（对接密码）")
	private String passWord;
	@ZteSoftCommentAnnotationParam(name = "单号量", type = "String", isNecessary = "Y", desc = "单号量：1-100之间数字")
	private String billNoAmount;
	@ZteSoftCommentAnnotationParam(name = "对接授权码", type = "String", isNecessary = "Y", desc = "对接授权码")
	private String appKeySub;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型：1-标准快递，8-代收到付，9-快递包裹")
	private String businessType;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.ems.getLogisticsNumber";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSysAccount() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		sysAccount = cacheUtil.getConfigInfo("ems_get_sysAccount");
		// sysAccount="A1234567890Z";
		return sysAccount;
	}

	public void setSysAccount(String sysAccount) {
		this.sysAccount = sysAccount;
	}

	public String getPassWord() {
		// passWord = "e10adc3949ba59abbe56e057f20f883e";
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		passWord = cacheUtil.getConfigInfo("ems_get_passWord");
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getBillNoAmount() {
		billNoAmount = "1";
		return billNoAmount;
	}

	public void setBillNoAmount(String billNoAmount) {
		this.billNoAmount = billNoAmount;
	}

	public String getAppKeySub() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		appKeySub = cacheUtil.getConfigInfo("ems_get_appkey");
		return appKeySub;
	}

	public void setAppKeySub(String appKeySub) {
		this.appKeySub = appKeySub;
	}

	public String getBusinessType() {
		// 代收貨款
		businessType = "8";
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}
