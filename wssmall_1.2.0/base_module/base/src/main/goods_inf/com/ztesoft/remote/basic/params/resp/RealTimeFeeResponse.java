package com.ztesoft.remote.basic.params.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * Created with IntelliJ IDEA. User: guangping Date: 2014-02-12 08:53 To change
 * this template use File | Settings | File Templates.
 */
public class RealTimeFeeResponse extends ZteResponse {
	private String accNbr;
	@ZteSoftCommentAnnotationParam(name = "当前时间", type = "String", isNecessary = "Y", desc = "currDate：当前日期。")
	private String currDate;// 当前日期 2013-7-16
	@ZteSoftCommentAnnotationParam(name = "余额", type = "long", isNecessary = "Y", desc = "overmoney：余额。")
	private long overmoney = 0;// 余额
	@ZteSoftCommentAnnotationParam(name = "欠费余额", type = "long", isNecessary = "Y", desc = "overmoney：欠费金额。")
	private long overdraftMoney = 0;// 欠费金额

	@ZteSoftCommentAnnotationParam(name = "EOP调用返回参数", type = "map", isNecessary = "Y", desc = "EOP调用返回参数")
	private Map balanceMap;

	@ZteSoftCommentAnnotationParam(name = "当前时间", type = "String", isNecessary = "Y", desc = "currDate：当前日期。")
	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public long getOvermoney() {
		return overmoney;
	}

	public void setOvermoney(long overmoney) {
		this.overmoney = overmoney;
	}

	public long getOverdraftMoney() {
		return overdraftMoney;
	}

	public void setOverdraftMoney(long overdraftMoney) {
		this.overdraftMoney = overdraftMoney;
	}

	public Map getBalanceMap() {
		return balanceMap;
	}

	public void setBalanceMap(Map balanceMap) {
		this.balanceMap = balanceMap;
	}


}
