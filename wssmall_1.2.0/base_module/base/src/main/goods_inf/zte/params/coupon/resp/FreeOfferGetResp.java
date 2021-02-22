package zte.params.coupon.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.FreeOffer;

import params.ZteResponse;

public class FreeOfferGetResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="赠品",type="String",isNecessary="N",desc="fo：赠品")	
	private FreeOffer fo;

	public FreeOffer getFo() {
		return fo;
	}

	public void setFo(FreeOffer fo) {
		this.fo = fo;
	}
}
