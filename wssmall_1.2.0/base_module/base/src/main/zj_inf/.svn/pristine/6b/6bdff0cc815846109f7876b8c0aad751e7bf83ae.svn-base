/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 账户信息
 * 
 */
public class OpenDealApplyAcctInfoVo implements Serializable {
	
	private String createOrExtendsAcct;// Y 是否继承账户标示 0：新建账户 1：继承老账户
	private String accountPayType; // Y 账户付费方式，默认：10 参考附录证件类型说明
	private String debutySn; // N 合帐号码
	
	public String getCreateOrExtendsAcct() {
		if (StringUtils.isBlank(createOrExtendsAcct)) return null;
		return createOrExtendsAcct;
	}
	public void setCreateOrExtendsAcct(String createOrExtendsAcct) {
		this.createOrExtendsAcct = createOrExtendsAcct;
	}
	public String getAccountPayType() {
		if (StringUtils.isBlank(accountPayType)) return null;
		return accountPayType;
	}
	public void setAccountPayType(String accountPayType) {
		this.accountPayType = accountPayType;
	}
	public String getDebutySn() {
		if (StringUtils.isBlank(debutySn)) return null;
		return debutySn;
	}
	public void setDebutySn(String debutySn) {
		this.debutySn = debutySn;
	}
	
}
