package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.PackageUseResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA. User: guangping Date: 2014-02-12 09:29 To change
 * this template use File | Settings | File Templates.
 */
public class PackageUseRequest extends ZteRequest<PackageUseResponse> {

	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "移动用户： 11 位手机号     固网 /宽带 /小灵通： 0+ 区号 +号码 （“ 0+ 区号”例如： 010 北京， 北京， 021 上海，0531 济南。）")
	private String accNbr;

	@ZteSoftCommentAnnotationParam(name = "用户属性", type = "String", isNecessary = "Y", desc = "用户属性( 0固话 1小灵通 2移动用户  3ADSL)")
	private String destinationAttr;

	@ZteSoftCommentAnnotationParam(name = "帐期格式 ", type = "String", isNecessary = "Y", desc = "帐期 格式 YYYYMM")
	private String billingCycle;

	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(套餐使用情况)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	@Override
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(accNbr)){
			throw new ApiRuleException("-1", "查询号码不能为空!");
		}
		if(ApiUtils.isBlank(destinationAttr)){
			throw new ApiRuleException("-1", "用户属性不能为空!");
		}
	}

	@Override
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "qryPackageUse";
	}

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getDestinationAttr() {
		return destinationAttr;
	}

	public void setDestinationAttr(String destinationAttr) {
		this.destinationAttr = destinationAttr;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

}
