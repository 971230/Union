package params.regions.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class RegionDeleteRegionsReq extends ZteRequest{
	
	private String region_id;
	
	
	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

}