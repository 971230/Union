package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zengxianlian
 * @version BSS
 *
 */
public class BSSAccountRemarkVo implements Serializable {

	private String AccountRemarkID;
	private String AccountRemarkValue;
	
	public String getAccountRemarkId() {
		if (StringUtils.isBlank(AccountRemarkID)) return null;
		return AccountRemarkID;
	}
	public void setAccountRemarkId(String accountRemarkId) {
		this.AccountRemarkID = accountRemarkId;
	}
	public String getAccountRemarkValue() {
		if (StringUtils.isBlank(AccountRemarkValue)) return null;
		return AccountRemarkValue;
	}
	public void setAccountRemarkValue(String accountRemarkValue) {
		this.AccountRemarkValue = accountRemarkValue;
	}
	
}
