package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AuthListResp;

import com.ztesoft.api.ApiRuleException;


/**
 * 权限列表入参实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class AuthListReq extends ZteRequest<AuthListResp>{

	
	private String[] str;

	public String[] getStr() {
		return str;
	}

	public void setStr(String[] str) {
		this.str = str;
	}

	@Override
	public void check() throws ApiRuleException {
		if(null == str){
			throw new ApiRuleException("-1","缺失参数");
		}
	}

	@Override
	public String getApiMethodName() {
		return "authServ.list";
	}
}
