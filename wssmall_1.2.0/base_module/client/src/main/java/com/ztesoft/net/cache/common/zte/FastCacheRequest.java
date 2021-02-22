package com.ztesoft.net.cache.common.zte;

import java.io.Serializable;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * @author Reason.Yea 
 * @version 创建时间：2014-11-25
 */
public class FastCacheRequest extends ZteRequest {
	private int nameSpace;
	private int expireTime;
	
	public int getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(int nameSpace) {
		this.nameSpace = nameSpace;
	}

	public int getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	private Serializable value;
	private String key;

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

}
