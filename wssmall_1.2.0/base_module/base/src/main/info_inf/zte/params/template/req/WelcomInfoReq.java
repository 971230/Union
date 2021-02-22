package zte.params.template.req;

import params.ZteRequest;
import zte.params.template.resp.WelcomInfoResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 个人中心信息汇总
 * @author yangning
 */
@SuppressWarnings("serial")
public class WelcomInfoReq extends ZteRequest<WelcomInfoResp>{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.infoService.info.get.memberMap";
	}
}
