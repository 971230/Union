package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.resp.PartnerAdvLogsResp;


/**
 * 查询预存款日志入参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAdvLogsReq extends ZteRequest<PartnerAdvLogsResp>{

	private String member_id;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(member_id)){
			throw new ApiRuleException("-1","会员id不能为空");
		}
		
	}

	@Override
	public String getApiMethodName() {
		return "partnerServ.listAdvanceLogsByMemberId";
	}
}
