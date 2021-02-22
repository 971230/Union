package com.ztesoft.net.mall.cmp;

import params.checkacctconfig.req.CheckAcctConfigReq;
import params.checkacctconfig.resp.CheckAcctConfigResp;

public interface ICMPBill {
	public void perform(CheckAcctConfigReq checkAcctConfigReq,CheckAcctConfigResp checkAcctConfigResp);
}
