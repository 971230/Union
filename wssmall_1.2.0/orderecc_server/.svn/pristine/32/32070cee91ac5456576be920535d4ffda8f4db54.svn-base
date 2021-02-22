package com.ztesoft.check.action.backend;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 *  
 *  
 * 
 *  
 */
public class MsgBox implements Serializable{
	/**
	 * 是否执行成功
	 */
	private boolean success = true;

	private Integer result;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 执行后所携带的数据
	 */
	private Map params = null;
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public void addResult(Object key, Object value) {
		if (params == null) {
			params = new HashMap();
		}
		params.put(key, value);
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}
	
	

}
