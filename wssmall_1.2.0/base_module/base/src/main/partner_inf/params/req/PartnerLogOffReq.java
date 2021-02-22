package params.req;

import params.ZteRequest;
import com.ztesoft.api.ApiRuleException;

/**
 * 注销
 * 
 * @author 黄记新
 * 
 */
public class PartnerLogOffReq extends ZteRequest {

	private String user_id;

//	private String username;
//
//	private String password;

	private String state="0";
	
	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.service.partnerLogoff";
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id
	 *            the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
