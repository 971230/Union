package zte.params.ecsord.req;

import params.ZteRequest;
import zte.params.ecsord.resp.GetOrderByInfIdResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class GetOrderByInfIdReq extends ZteRequest<GetOrderByInfIdResp> {
	
	@ZteSoftCommentAnnotationParam(name="外部订单",type="String",isNecessary="Y",desc="外部订单")
	private String zb_inf_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.attr.req.getOrderByInfId";
	}

	public String getZb_inf_id() {
		return zb_inf_id;
	}

	public void setZb_inf_id(String zb_inf_id) {
		this.zb_inf_id = zb_inf_id;
	}

}
