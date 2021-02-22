/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

/**
 * @author ZX
 * @version 2015-06-25
 *
 */
public class ArrearageFeeInfoRspVo implements Serializable {
	
	private String depositMoney;
	private String balanceBeforeLast;
	private String lastBalance;
	private String fee;
	
	public String getDepositMoney() {
		return depositMoney;
	}
	public void setDepositMoney(String depositMoney) {
		this.depositMoney = depositMoney;
	}
	public String getBalanceBeforeLast() {
		return balanceBeforeLast;
	}
	public void setBalanceBeforeLast(String balanceBeforeLast) {
		this.balanceBeforeLast = balanceBeforeLast;
	}
	public String getLastBalance() {
		return lastBalance;
	}
	public void setLastBalance(String lastBalance) {
		this.lastBalance = lastBalance;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	
}

