package params.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;


/**
 * 编辑分销商关联用户入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerUserEditReq extends ZteRequest<ZteResponse>{

	private String partner_id;
	private String user_id;
	
	
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(partner_id)){
			throw new ApiRuleException("-1","分销商id不能为空");
		}
		if(StringUtil.isEmpty(user_id)){
			throw new ApiRuleException("-1","用户id不能为空");
		}
	}
	@Override
	public String getApiMethodName() {
		return "partnerServ.updateUserid";
	}
	
}
