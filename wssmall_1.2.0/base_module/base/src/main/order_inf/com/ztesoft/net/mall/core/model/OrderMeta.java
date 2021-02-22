package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单扩展信息
 * 
 * @author kingapex
 * 
 */
public class OrderMeta {
	@ZteSoftCommentAnnotationParam(name="附言ID",type="String",isNecessary="Y",desc="附言ID")
	private String metaid;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String orderid;
	@ZteSoftCommentAnnotationParam(name="附言Key",type="String",isNecessary="Y",desc="附言Key")
	private String meta_key;
	@ZteSoftCommentAnnotationParam(name="附言VALUE",type="String",isNecessary="Y",desc="附言VALUE")
	private String meta_value;

	public String getMetaid() {
		return metaid;
	}

	public void setMetaid(String metaid) {
		this.metaid = metaid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMeta_key() {
		return meta_key;
	}

	public void setMeta_key(String meta_key) {
		this.meta_key = meta_key;
	}

	public String getMeta_value() {
		return meta_value;
	}

	public void setMeta_value(String meta_value) {
		this.meta_value = meta_value;
	}

}
