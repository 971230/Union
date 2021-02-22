package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.RealTimeFeeResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA. User: guangping Date: 2014-02-12 08:52 To change
 * this template use File | Settings | File Templates.
 */
public class RealTimeFeeRequest extends ZteRequest<RealTimeFeeResponse> {
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "号码")
	private String accNbr;

	@ZteSoftCommentAnnotationParam(name = "查询类型", type = "String", isNecessary = "Y", desc = "查询可用余额  1表示可用余额  0标识拥有的余额")
	private String query_type;

	@ZteSoftCommentAnnotationParam(name = "本地网ID", type = "String", isNecessary = "Y", desc = "本地网ID")
	private String lan_id;

	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(查询实时话费)")
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

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(accNbr)) {
			throw new ApiRuleException("-1", "号码不能为空!");
		}
		
		if (ApiUtils.isBlank(query_type)) {
			throw new ApiRuleException("-1", "查询类型不能为空!");
		}
	}

	@Override
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "realTimeFee";
	}
}
