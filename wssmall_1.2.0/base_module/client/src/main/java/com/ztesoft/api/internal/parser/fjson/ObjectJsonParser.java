package com.ztesoft.api.internal.parser.fjson;

import params.ZteResponse;

import com.ztesoft.api.ApiException;
import com.ztesoft.api.ZteParser;
import commons.CommonTools;

/**
 * 单个JSON对象解释器。
 * 
 * @author carver.gu
 * @since 1.0, Apr 11, 2010
 */
public class ObjectJsonParser<T extends ZteResponse> implements ZteParser<T> {

	private Class<T> clazz;

	public ObjectJsonParser(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T parse(String rsp) throws ApiException {
		return CommonTools.jsonToBean(rsp, clazz);
	}

	@Override
	public Class<T> getResponseClass() {
		return clazz;
	}

}
