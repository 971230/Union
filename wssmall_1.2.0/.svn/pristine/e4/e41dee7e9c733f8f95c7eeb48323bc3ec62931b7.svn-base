package zte.params.addr.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.params.addr.resp.MemberAddressListResp;

public class MemberAddressListReq extends ZteRequest<MemberAddressListResp> {
	
	@ZteSoftCommentAnnotationParam(name="会员编号",type="String",isNecessary="Y",desc="会员编号")
	private String member_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(member_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "member_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.address.list";
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

}
