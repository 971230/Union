package com.ztesoft.remote.params.activity.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.PromotionActivity;
/**
 * 
 * @author wui
 * 广告返回实体
 *
 */
public class ActResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="活动信息", type="PromotionActivity", isNecessary="N", desc="活动信息")
	private PromotionActivity activity;
	
	@ZteSoftCommentAnnotationParam(name="活动信息列表", type="List", isNecessary="N", desc="活动信息列表", hasChild=true)
	private List<PromotionActivity>  activityList;
	
	
	public PromotionActivity getActivity() {
		return activity;
	}
	public void setActivity(PromotionActivity activity) {
		this.activity = activity;
	}
	public List<PromotionActivity> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<PromotionActivity> activityList) {
		this.activityList = activityList;
	}
	
}
