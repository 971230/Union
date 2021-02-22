package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BusiHandleCheckBSSVO implements Serializable {

	@ZteSoftCommentAnnotationParam(name="可用活动列表",type="String",isNecessary="Y",desc="scheme_list_return：多个数据以“,”分割")
	private String scheme_list_return;
	
	@ZteSoftCommentAnnotationParam(name="可用产品列表",type="String",isNecessary="Y",desc="product_list_return：多个数据以“,”分割")
	private String product_list_return;

	public String getScheme_list_return() {
		return scheme_list_return;
	}

	public void setScheme_list_return(String scheme_list_return) {
		this.scheme_list_return = scheme_list_return;
	}

	public String getProduct_list_return() {
		return product_list_return;
	}

	public void setProduct_list_return(String product_list_return) {
		this.product_list_return = product_list_return;
	}

}
