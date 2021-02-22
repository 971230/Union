/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-06-25
 *
 */
public class AcctInfoRspVo implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name = "账户标识", type = "String", isNecessary = "N", desc = "acctId")
	private String acctId;
	@ZteSoftCommentAnnotationParam(name = "账户名称", type = "String", isNecessary = "N", desc = "payName")
	private String payName;
	@ZteSoftCommentAnnotationParam(name = "付费方式编码", type = "String", isNecessary = "N", desc = "payModeCode")
	private String payModeCode;
	@ZteSoftCommentAnnotationParam(name = "预付费标识：0 后付费 1 准预付费 2 OCS 3 PPS", type = "String", isNecessary = "N", desc = "prepayTag")
	private String prepayTag;
	@ZteSoftCommentAnnotationParam(name = "账务周期", type = "String", isNecessary = "N", desc = "accountCycle")
	private String accountCycle;
	@ZteSoftCommentAnnotationParam(name = "是否集团账户", type = "String", isNecessary = "N", desc = "isGroupAcct")
	private String isGroupAcct;
	@ZteSoftCommentAnnotationParam(name = "托收信息", type = "ConsignRspVo", isNecessary = "N", desc = "consign")
	private ConsignRspVo consign;
	
	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getPayModeCode() {
		return payModeCode;
	}
	public void setPayModeCode(String payModeCode) {
		this.payModeCode = payModeCode;
	}
	public String getPrepayTag() {
		return prepayTag;
	}
	public void setPrepayTag(String prepayTag) {
		this.prepayTag = prepayTag;
	}
	public String getAccountCycle() {
		return accountCycle;
	}
	public void setAccountCycle(String accountCycle) {
		this.accountCycle = accountCycle;
	}
	public String getIsGroupAcct() {
		return isGroupAcct;
	}
	public void setIsGroupAcct(String isGroupAcct) {
		this.isGroupAcct = isGroupAcct;
	}
	public ConsignRspVo getConsign() {
		return consign;
	}
	public void setConsign(ConsignRspVo consign) {
		this.consign = consign;
	}
	
}
