package zte.params.addr.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.addr.resp.MemberAddressEditResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.MemberAddress;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

public class MemberAddressEditReq extends ZteRequest<MemberAddressEditResp> {
	
	@ZteSoftCommentAnnotationParam(name="地址信息",type="MemberAddress",isNecessary="Y",desc="地址详细信息")
	private MemberAddress address;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(address==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "memberAddress不能为空"));
		if(StringUtils.isEmpty(address.getMember_id()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "address-->member_id不能为空"));
		if(StringUtils.isEmpty(address.getAddr_id()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "address-->addr_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.address.edit";
	}

	public MemberAddress getAddress() {
		return address;
	}

	public void setAddress(MemberAddress address) {
		this.address = address;
	}

}
