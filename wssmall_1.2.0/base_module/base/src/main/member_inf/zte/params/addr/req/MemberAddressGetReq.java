package zte.params.addr.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import params.ZteRequest;
import zte.params.addr.resp.MemberAddressGetResp;

public class MemberAddressGetReq extends ZteRequest<MemberAddressGetResp> {

	@ZteSoftCommentAnnotationParam(name="地址编号",type="String",isNecessary="Y",desc="地址详细编号")
	private String address_id;
	@ZteSoftCommentAnnotationParam(name="是否获取最新更新地址",type="String",isNecessary="N",desc="是否获取最新更新地址")
	private boolean isGetLastAddress;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		//if(StringUtils.isEmpty(address_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "address_id不能为空"));
	}

	public boolean isGetLastAddress() {
		return isGetLastAddress;
	}

	public void setGetLastAddress(boolean isGetLastAddress) {
		this.isGetLastAddress = isGetLastAddress;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.address.get";
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	
}
