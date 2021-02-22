package zte.params.ecsord.req;

import params.ZteRequest;
import zte.params.ecsord.resp.GetOrderByOutTidResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class GetOrderByBssIdReq extends ZteRequest<GetOrderByOutTidResp> {
	
	@ZteSoftCommentAnnotationParam(name="bss订单",type="String",isNecessary="Y",desc="bss订单")
	private String bss_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.attr.req.getOrdByBssId";
	}

	public String getBss_id() {
		return bss_id;
	}

	public void setBss_id(String bss_id) {
		this.bss_id = bss_id;
	}

	
}
