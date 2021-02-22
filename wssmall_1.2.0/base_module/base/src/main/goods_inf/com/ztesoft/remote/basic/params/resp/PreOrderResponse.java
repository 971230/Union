package com.ztesoft.remote.basic.params.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

/**
 * 
 * @author chenlijun
 *
 */
public class PreOrderResponse extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "订单列表", type = "Map", isNecessary = "Y", desc = "订单列表")
	private List preOrderList;

	public List getPreOrderList() {
		return preOrderList;
	}

	public void setPreOrderList(List preOrderList) {
		this.preOrderList = preOrderList;
	}

}
