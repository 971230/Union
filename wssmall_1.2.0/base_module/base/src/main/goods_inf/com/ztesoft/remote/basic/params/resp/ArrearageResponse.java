package com.ztesoft.remote.basic.params.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

/**
 * Created with IntelliJ IDEA. User: guangping Date: 2014-02-12 09:15 To change
 * this template use File | Settings | File Templates.
 */
public class ArrearageResponse extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "业务参数", type = "Map", isNecessary = "Y", desc = "调用EOP返回值")
	public Map map;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}
