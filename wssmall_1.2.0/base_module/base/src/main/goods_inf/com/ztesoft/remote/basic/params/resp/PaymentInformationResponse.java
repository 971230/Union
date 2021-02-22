package com.ztesoft.remote.basic.params.resp;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class PaymentInformationResponse extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "充值话费列表", type = "Map", isNecessary = "Y", desc = "充值话费列表")
	private Map retMap;

	@ZteSoftCommentAnnotationParam(name = "充值钱包列表", type = "List", isNecessary = "Y", desc = "充值钱包列表")
	private List bagChargeList;
	
	@ZteSoftCommentAnnotationParam(name = "充值钱包总记录条数", type = "Map", isNecessary = "Y", desc = "充值钱包总记录条数")
	private int countno;

	public Map getRetMap() {
		return retMap;
	}

	public void setRetMap(Map retMap) {
		this.retMap = retMap;
	}

	public List getBagChargeList() {
		return bagChargeList;
	}

	public void setBagChargeList(List bagChargeList) {
		this.bagChargeList = bagChargeList;
	}

	public int getCountno() {
		return countno;
	}

	public void setCountno(int countno) {
		this.countno = countno;
	}

	
}
