package zte.params.order.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.AdjustListResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

public class AdjustListReq extends ZteRequest<AdjustListResp> {
	
	@ZteSoftCommentAnnotationParam(name="调整类型",type="String",isNecessary="Y",desc="调整类型")
	private String adjust_type;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
		if(StringUtils.isEmpty(adjust_type))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "adjust_type不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.getAdjustList";
	}

	public String getAdjust_type() {
		return adjust_type;
	}

	public void setAdjust_type(String adjust_type) {
		this.adjust_type = adjust_type;
	}
	
}
