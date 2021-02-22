package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.DlyTypeListResp;

public class DlyTypeListReq extends ZteRequest<DlyTypeListResp> {

	@ZteSoftCommentAnnotationParam(name="区、县ID",type="String",isNecessary="N",desc="区、县ID")
	private String region_id;

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.dlyTypeAddressService.dlyType.list";
	}
	
}
