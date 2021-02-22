package params.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.app.base.core.model.Partner;


/**
 * 分销商添加入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAddReq extends ZteRequest<ZteResponse>{

	private Partner partner;

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	@Override
	public void check() throws ApiRuleException {
		if(null == partner){
			throw new ApiRuleException("-1","分销商实体不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.addPartner";
	}
}
