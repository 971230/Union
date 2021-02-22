package zte.params.coupon.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.FreeOfferCategory;

import params.ZteResponse;

public class FreeOfferCategoryGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="赠品分类",type="String",isNecessary="N",desc="cat：赠品分类")	
	private FreeOfferCategory cat;

	public FreeOfferCategory getCat() {
		return cat;
	}

	public void setCat(FreeOfferCategory cat) {
		this.cat = cat;
	}
}
