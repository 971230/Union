package com.ztesoft.remote.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import services.ServiceBase;
import zte.net.ecsord.params.spec.req.ActivitySpecReq;
import zte.net.ecsord.params.spec.resp.ActivitySpecResp;

import com.ztesoft.net.consts.GoodsConsts;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.service.IPromotionActivityManager;
import com.ztesoft.remote.inf.IActivityService;
import com.ztesoft.remote.params.activity.req.ActByIdReq;
import com.ztesoft.remote.params.activity.req.ActByTagIdReq;
import com.ztesoft.remote.params.activity.req.PromotionMapByIdReq;
import com.ztesoft.remote.params.activity.resp.ActPageResp;
import com.ztesoft.remote.params.activity.resp.ActResp;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;

import commons.CommonTools;

/**
* 活动service
* @作者 wu.i 
* @创建日期 2013-9-23 
* @版本 V 1.0
* 
 */
@SuppressWarnings("unchecked")
public class ActivityService extends ServiceBase implements IActivityService {

	@Resource
	private IPromotionActivityManager promotionActivityManager;

	@Override
	public ActResp queryActById(ActByIdReq actByIdReq) {
		
		ActResp actResp = new ActResp();
		List activityList = new ArrayList();
		String id = actByIdReq.getId();
		id = id == null ? "0" : id;
		
		try {
			
			PromotionActivity activity = this.promotionActivityManager.get(id);
			
			//文件（图片）路径转换
			activity.setAtturl(UploadUtil.replacePath(activity.getAtturl()));
			
			activityList.add(activity);
			actResp.setActivity(activity);
			actResp.setActivityList(activityList);
				
			this.addReturn(actByIdReq, actResp);
			
		}catch(RuntimeException e){
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}
		return actResp;
	}

	@Override
	public ActPageResp queryActByTagId(ActByTagIdReq actByTagIdReq) {
		
		ActPageResp actResp = new ActPageResp();
		String tagId = actByTagIdReq.getTagId();
		String userId = actByTagIdReq.getUserId();
		tagId = tagId == null ?  "-999" : tagId;
		
		try {
			
			Page actPage = this.promotionActivityManager.pageList(tagId, userId, 
					actByTagIdReq.getPageNo(), actByTagIdReq.getPageSize());
			
			//文件（图片）路径转换
			UploadUtil.convertFilePath(GoodsConsts.PROMOTION_ACTIVITY_ATTURL, actPage.getResult());
			
			actResp.setActPage(actPage);
				
			this.addReturn(actByTagIdReq, actResp);
			
		} catch (RuntimeException e) {
			
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}
		
		return actResp;
	}

	@Override
	public PromotionMapByIdResp getPromotionMap(PromotionMapByIdReq promotionMapByIdReq) {
		
		PromotionMapByIdResp resp = new PromotionMapByIdResp();
		String activity_id = promotionMapByIdReq.getActivity_id();
		
		try {
			
			Map pmt_map = this.promotionActivityManager.getPromotionMap(activity_id);
			List goodsLst = (List)pmt_map.get("goods_list");
			List giftsLst = (List)pmt_map.get("gifts_list");
			resp.setPmt_map(pmt_map);  //活动基本信息
			resp.setGoodsLst(goodsLst);  //适用该活动的商品列表
			resp.setGiftsLst(giftsLst);  //此次活动的赠品(商品)
			this.addReturn(promotionMapByIdReq, resp);
			
		} catch (RuntimeException e) {
			
			e.printStackTrace();
			CommonTools.addFailError(e.getMessage());
		}
		
		return resp;
	}

	@Override
	public ActivitySpecResp getActivitySpec(ActivitySpecReq activitySpecReq) {
		String activity_code = activitySpecReq.getActivity_code();
		Map specMap = this.promotionActivityManager.getActivitySpecs(activity_code);
		
		ActivitySpecResp resp = new ActivitySpecResp();
		resp.setSpecs(specMap);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(activitySpecReq,resp);
		return resp;
	}
	
}