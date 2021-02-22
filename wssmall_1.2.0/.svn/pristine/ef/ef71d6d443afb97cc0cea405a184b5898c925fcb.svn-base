package zte.params.addr.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.params.addr.resp.MemberAddressDeleteResp;

public class MemberAddressDeleteReq extends ZteRequest<MemberAddressDeleteResp> {
	@ZteSoftCommentAnnotationParam(name="地址编号",type="String",isNecessary="Y",desc="地址详细编号")
	private String address_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(address_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "address_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.address.delete";
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

}
