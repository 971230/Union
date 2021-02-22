package zte.params.store.req;

import params.ZteRequest;
import zte.params.store.resp.WareHouseDeleteResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WareHouseDeleteReq extends ZteRequest<WareHouseDeleteResp> {
	
	@ZteSoftCommentAnnotationParam(name="仓库标识", type="String", isNecessary="Y", desc="物理仓标识")
	private String house_id;
	
	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.service.warehouse.delete";
	}

}
