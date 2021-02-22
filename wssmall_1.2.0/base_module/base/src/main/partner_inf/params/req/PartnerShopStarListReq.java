package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.resp.PartnerShopStarListResp;

public class PartnerShopStarListReq extends ZteRequest<PartnerShopStarListResp>{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.searchPartneShopStarList";
	}

}
