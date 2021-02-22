package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.UserActResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * 用户权限参数
 * @author hu.yi
 * @date 2013.12.23
 */
public class UserActReq extends ZteRequest<UserActResp>{

	private String userid;
	private String acttype;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getActtype() {
		return acttype;
	}
	public void setActtype(String acttype) {
		this.acttype = acttype;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(userid) || StringUtil.isEmpty(acttype)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "permissionServ.getUesrAct";
	}
}
