package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable; 

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ProductSubRespVO implements Serializable {

	@ZteSoftCommentAnnotationParam(name="BSS订单号",type="String",isNecessary="Y",desc="bms_accept_id")
	private String bms_accept_id;

	public String getBms_accept_id() {
		return bms_accept_id;
	}

	public void setBms_accept_id(String bms_accept_id) {
		this.bms_accept_id = bms_accept_id;
	}
	
}
