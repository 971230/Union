package zte.params.addr.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.addr.resp.MemberAddressAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.MemberAddress;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

public class MemberAddressAddReq extends ZteRequest<MemberAddressAddResp> {

	@ZteSoftCommentAnnotationParam(name="地址信息",type="MemberAddress",isNecessary="Y",desc="地址详细信息")
	private MemberAddress memberAddress;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(memberAddress==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "memberAddress不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.address.add";
	}

	public MemberAddress getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(MemberAddress memberAddress) {
		this.memberAddress = memberAddress;
	}

}
