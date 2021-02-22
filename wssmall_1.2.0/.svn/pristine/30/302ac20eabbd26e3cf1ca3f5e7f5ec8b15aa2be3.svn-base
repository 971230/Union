package zte.params.ecsord.req;

import params.ZteRequest;
import zte.params.ecsord.resp.GetOrderByOutTidResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class GetOrderByLogiNoReq extends ZteRequest<GetOrderByOutTidResp> {
	
	@ZteSoftCommentAnnotationParam(name="物流单",type="String",isNecessary="Y",desc="物流单")
	private String logi_no;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.service.order.getOrderByLogiNo";
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	

	
}
