package com.ztesoft.api.internal.utils;

import java.util.HashMap;
import java.util.Map;

public class RequestParametersHolder {

	private ZteHashMap protocalMustParams;
	private ZteHashMap protocalOptParams;
	private ZteHashMap applicationParams;

	public ZteHashMap getProtocalMustParams() {
		return protocalMustParams;
	}

	public void setProtocalMustParams(ZteHashMap protocalMustParams) {
		this.protocalMustParams = protocalMustParams;
	}

	public ZteHashMap getProtocalOptParams() {
		return protocalOptParams;
	}

	public void setProtocalOptParams(ZteHashMap protocalOptParams) {
		this.protocalOptParams = protocalOptParams;
	}

	public ZteHashMap getApplicationParams() {
		return applicationParams;
	}

	public void setApplicationParams(ZteHashMap applicationParams) {
		this.applicationParams = applicationParams;
	}

	public Map<String, String> getAllParams() {
		Map<String, String> params = new HashMap<String, String>();
		if (protocalMustParams != null && !protocalMustParams.isEmpty()) {
			params.putAll(protocalMustParams);
		}
		if (protocalOptParams != null && !protocalOptParams.isEmpty()) {
			params.putAll(protocalOptParams);
		}
		if (applicationParams != null && !applicationParams.isEmpty()) {
			params.putAll(applicationParams);
		}
		return params;
	}

}
