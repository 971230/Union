package zte.params.order.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.AdjustOrderPriceResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

public class AdjustOrderPriceReq extends ZteRequest<AdjustOrderPriceResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String adjust_key;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String adjust_type;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(adjust_key))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "adjust_key不能为空"));
		
		if(StringUtils.isEmpty(adjust_type))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "adjust_type不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.adjustOrderPrice";
	}

	public String getAdjust_key() {
		return adjust_key;
	}

	public void setAdjust_key(String adjust_key) {
		this.adjust_key = adjust_key;
	}

	public String getAdjust_type() {
		return adjust_type;
	}

	public void setAdjust_type(String adjust_type) {
		this.adjust_type = adjust_type;
	}
	
}
