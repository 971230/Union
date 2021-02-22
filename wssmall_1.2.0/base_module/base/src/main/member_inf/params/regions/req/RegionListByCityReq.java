package params.regions.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class RegionListByCityReq extends ZteRequest{
	
	private long city_id;
	
	public long getCity_id() {
		return city_id;
	}

	public void setCity_id(long city_id) {
		this.city_id = city_id;
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
