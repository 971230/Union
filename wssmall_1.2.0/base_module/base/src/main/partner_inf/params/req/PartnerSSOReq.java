package params.req;

import params.ZteRequest;
import params.adminuser.resp.UserWdLoginResp;

import com.ztesoft.api.ApiRuleException;


/**
 * 单点登录入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerSSOReq extends ZteRequest<UserWdLoginResp>{

	private String user_id;


	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.service.partnersso";
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
