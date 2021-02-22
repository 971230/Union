package com.ztesoft.net.mall.cmp;

import params.checkacctconfig.req.CheckAcctConfigReq;
import params.checkacctconfig.resp.CheckAcctConfigResp;

public interface ICheckBill {
	
	public int bestCheckBill(CheckAcctConfigReq checkAcctConfigReq,CheckAcctConfigResp checkAcctConfigResp);
}
