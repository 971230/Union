package zte.net.ecsord.params.busiopen.separteflow.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.busiopen.separteflow.resp.PushOrderID2MemcacheResp;

public class PushOrderID2MemcacheReq extends ZteRequest<PushOrderID2MemcacheResp>  {
	@ZteSoftCommentAnnotationParam(name="外部订单",type="String",isNecessary="Y",desc="外部订单")
	private String zb_inf_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.params.busiopen.separteflow.req.pushOrderID2Memcache";
	}

	public String getZb_inf_id() {
		return zb_inf_id;
	}

	public void setZb_inf_id(String zb_inf_id) {
		this.zb_inf_id = zb_inf_id;
	}

	
}
