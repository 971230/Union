package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.resp.PartnerInfoResp;


/**
 * 查询分销商对象 对应getPartnerByUserId方法
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerInfoOneReq extends ZteRequest<PartnerInfoResp>{

	private String userid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(userid)){
			throw new ApiRuleException("-1","用户id不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.getPartnerByUserId";
	}
	
}
