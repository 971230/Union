package zte.params.order.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.model.DlyTypeAddress;

import params.ZteResponse;

public class DlyTypeAddressListResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="配送方式地址列表",type="String",isNecessary="Y",desc="配送方式地址列表",hasChild=true)
	private List<DlyTypeAddress> dlyTypeAddressList;

	public List<DlyTypeAddress> getDlyTypeAddressList() {
		return dlyTypeAddressList;
	}

	public void setDlyTypeAddressList(List<DlyTypeAddress> dlyTypeAddressList) {
		this.dlyTypeAddressList = dlyTypeAddressList;
	}
	
}
