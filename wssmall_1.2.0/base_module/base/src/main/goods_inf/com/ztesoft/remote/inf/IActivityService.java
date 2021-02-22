package com.ztesoft.remote.inf;

import zte.net.ecsord.params.spec.req.ActivitySpecReq;
import zte.net.ecsord.params.spec.resp.ActivitySpecResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.params.activity.req.ActByIdReq;
import com.ztesoft.remote.params.activity.req.ActByTagIdReq;
import com.ztesoft.remote.params.activity.req.PromotionMapByIdReq;
import com.ztesoft.remote.params.activity.resp.ActPageResp;
import com.ztesoft.remote.params.activity.resp.ActResp;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;


@ZteSoftCommentAnnotation(type="class", desc="活动类API", summary="活动类API")
public interface IActivityService {
	
	@ZteSoftCommentAnnotation(type="method",desc="根据活动标识获取活动信息",summary="根据活动标识获取活动信息")
	public ActResp queryActById(ActByIdReq actByIdReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据标签标识获取活动信息",summary="根据标签标识获取活动信息")
	public ActPageResp queryActByTagId(ActByTagIdReq actByTagIdReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据活动标识获取活动基本信息及规则和适用该活动的商品列表",summary="根据活动标识获取活动基本信息及规则和适用该活动的商品列表")
	public PromotionMapByIdResp getPromotionMap(PromotionMapByIdReq promotionMapByIdReq);
     
	@ZteSoftCommentAnnotation(type="method",desc="根据活动标识获取活动基本信息及规则和适用该活动的商品列表",summary="根据活动标识获取活动基本信息及规则和适用该活动的商品列表")
	public ActivitySpecResp getActivitySpec(ActivitySpecReq activitySpecReq);
     
}