package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class QueryRunThreadStatusReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.queryRunThreadStatus";
	}

}
