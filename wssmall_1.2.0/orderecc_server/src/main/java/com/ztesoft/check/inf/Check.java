package com.ztesoft.check.inf;

import java.io.Serializable;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
/**
 * 
 * 编码实现业务校验
 *
 */
public interface Check extends Serializable{
	public IdentifyResp validByCode(IdentifyReq identification) throws ApiBusiException;
}
