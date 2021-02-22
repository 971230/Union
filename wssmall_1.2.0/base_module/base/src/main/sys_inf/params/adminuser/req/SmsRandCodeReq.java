package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.adminuser.resp.SmsRandCodeResp;

public class SmsRandCodeReq extends ZteRequest<SmsRandCodeResp>{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "smsCodeServ.createRandCode";
	}

	
}
