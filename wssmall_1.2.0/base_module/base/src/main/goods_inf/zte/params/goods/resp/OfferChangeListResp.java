package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

public class OfferChangeListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="offerChanges",type="List",isNecessary="N",desc="offerChanges：商品。")
	private Page offerChanges;

	public Page getOfferChanges() {
		return offerChanges;
	}

	public void setOfferChanges(Page offerChanges) {
		this.offerChanges = offerChanges;
	}


}
