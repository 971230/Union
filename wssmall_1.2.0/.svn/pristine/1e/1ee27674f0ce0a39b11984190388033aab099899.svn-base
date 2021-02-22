package com.ztesoft.rop.service;

import java.util.Map;

import com.ztesoft.rop.utils.RopUtils;

public class RopBizzService implements IBizzService{

	@Override
	public boolean validSign(Map<String, String> data) {
		String app_secret = data.get("appKey") ;
		String sign = data.get("sign") ;
		
		String sql = " select a.app_secret from pm_app a ,pm_app_enterprice i, pm_app_account c  "+
							"  where c.acct_id=a.acct_id and i.acct_id=c.acct_id  and a.app_key=?"	 ;
		
		String currentSign = RopUtils.encryptSHA( app_secret,data) ;
		return sign.equals(currentSign);
	}
	
}
