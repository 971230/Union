package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class QueryExpCatalogReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.orderexp.queryExpCatalog";
	}

}
