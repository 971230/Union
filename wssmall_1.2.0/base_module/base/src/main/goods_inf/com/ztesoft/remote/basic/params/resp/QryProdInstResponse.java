package com.ztesoft.remote.basic.params.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @author chenlijun
 *
 */
public class QryProdInstResponse extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "业务参数", type = "Map", isNecessary = "Y", desc = "调用EOP返回值")
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
