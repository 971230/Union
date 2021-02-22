package zte.params.addr.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.MemberAddress;
import params.ZteResponse;

import java.util.List;

public class MemberAddressListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="地址信息",type="List",isNecessary="N",desc="地址详细信息列表")
	private List<MemberAddress> addressList;

	public List<MemberAddress> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<MemberAddress> addressList) {
		this.addressList = addressList;
	}
	
}
