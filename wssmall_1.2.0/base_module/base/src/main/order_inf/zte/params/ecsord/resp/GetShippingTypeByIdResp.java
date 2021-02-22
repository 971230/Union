package zte.params.ecsord.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class GetShippingTypeByIdResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="配送方式",type="List",isNecessary="Y",desc="配送方式")
	private String shipping_type;

	public String getShipping_type() {
		return shipping_type;
	}

	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
	}

}
