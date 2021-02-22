package params.org.req;

import params.ZteRequest;
import params.org.resp.OrgResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

public class OrgReq extends ZteRequest<OrgResp>{
	String staff_code;

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(staff_code)){
			throw new ApiRuleException("-1","staff_code不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.getAgentOrgInfo";
	}

	
}
