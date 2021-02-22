package com.ztesoft.net.param.inner;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
/**
 * 未匹配关键字异常更新入参
 * @author MCC
 *
 */
public class UnCheckSpecvalueInner implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//搜索规格id
	private String search_id;
	//搜索编码
	private String search_code;
	//关键字id
	private String key_id;
	//当前请求
	private HttpServletRequest request;

	public String getSearch_id() {
		return search_id;
	}

	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}

	public String getKey_id() {
		return key_id;
	}

	public String getSearch_code() {
		return search_code;
	}

	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
