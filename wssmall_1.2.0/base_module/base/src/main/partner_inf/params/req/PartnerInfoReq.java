package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.resp.PartnerInfoResp;

/**
 * 分销商信息查询入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerInfoReq extends ZteRequest<PartnerInfoResp>{

	private String userid;
	private Partner partner;
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(userid)){
			throw new ApiRuleException("-1","用户id不能为空");
		}
		
	}
	@Override
	public String getApiMethodName() {
		return "partnerServ.getPartnerByCurrentUserId";
	}
}
