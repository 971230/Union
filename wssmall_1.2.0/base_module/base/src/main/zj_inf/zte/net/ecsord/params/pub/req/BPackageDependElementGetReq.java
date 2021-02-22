package zte.net.ecsord.params.pub.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class BPackageDependElementGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="产品编码",type="String",isNecessary="Y",desc="product_id：产品编码")
	private String product_id;
	
	@ZteSoftCommentAnnotationParam(name="首月资费方式",type="String",isNecessary="Y",desc="first_payment：首月资费方式")
	private String first_payment;

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getFirst_payment() {
		return first_payment;
	}

	public void setFirst_payment(String first_payment) {
		this.first_payment = first_payment;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(product_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "product_id产品编码不能为空"));
		if(StringUtils.isEmpty(first_payment)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "first_payment首月资费方式不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.dcPublicService.bPackageDependElement.list";
	}

}
