package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.SmsActNumResp;

import com.ztesoft.api.ApiRuleException;

public class SmsActNumReq extends ZteRequest<SmsActNumResp>{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "smsCodeServ.querySmsById";
	}

	
}
