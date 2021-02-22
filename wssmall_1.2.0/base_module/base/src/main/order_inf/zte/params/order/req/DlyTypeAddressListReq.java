package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.DlyTypeAddressListResp;

public class DlyTypeAddressListReq extends ZteRequest<DlyTypeAddressListResp> {
	@ZteSoftCommentAnnotationParam(name="地区ID",type="String",isNecessary="Y",desc="地区ID")
	private String region_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.dlyTypeAddressService.list";
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

}
