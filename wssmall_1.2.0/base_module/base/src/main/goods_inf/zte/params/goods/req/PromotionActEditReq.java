package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.PromotionActivity;

import params.ZteRequest;

public class PromotionActEditReq extends ZteRequest {
@ZteSoftCommentAnnotationParam(name="活动表字段",type="String",isNecessary="Y",desc="promotion_activity：产品列表。")
	
	private PromotionActivity activity;
	private String[] tagids ;
	public PromotionActivity getActivity() {
		return activity;
	}

	public void setActivity(PromotionActivity activity) {
		this.activity = activity;
	}

	public String[] getTagids() {
		return tagids;
	}

	public void setTagids(String[] tagids) {
		this.tagids = tagids;
	}
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.promotionActivity.list";
	}

}
