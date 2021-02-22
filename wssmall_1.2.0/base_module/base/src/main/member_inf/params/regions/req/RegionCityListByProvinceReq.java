package params.regions.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class RegionCityListByProvinceReq extends ZteRequest{
	
	private long province_id;
	
	public long getProvince_id() {
		return province_id;
	}

	public void setProvince_id(long province_id) {
		this.province_id = province_id;
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
