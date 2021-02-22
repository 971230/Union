package params.adminuser.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

/**
 * 查找用户记录实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AdminUserCountReq extends ZteRequest<ZteResponse>{

	private String username;
	private String relCode;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRelCode() {
		return relCode;
	}

	public void setRelCode(String relCode) {
		this.relCode = relCode;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(username) && StringUtil.isEmpty(relCode)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}

	@Override
	public String getApiMethodName() {
		if(!StringUtil.isEmpty(username) && StringUtil.isEmpty(relCode)){
			return "adminUserServ.getUserCountByUserName";
		}else if(!StringUtil.isEmpty(relCode) && StringUtil.isEmpty(username)){
			return "adminUserServ.getUserCount";
		}else{
			return "adminUserServ.getUserCountByRelcode";
		}
	}
	
}
