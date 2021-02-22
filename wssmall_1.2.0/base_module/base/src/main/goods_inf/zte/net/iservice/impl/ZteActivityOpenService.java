package zte.net.iservice.impl;

import org.springframework.stereotype.Service;

import zte.net.ecsord.params.spec.req.ActivitySpecReq;
import zte.net.ecsord.params.spec.resp.ActivitySpecResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.remote.inf.IActivityService;
import com.ztesoft.remote.params.activity.req.ActByIdReq;
import com.ztesoft.remote.params.activity.req.ActByTagIdReq;
import com.ztesoft.remote.params.activity.req.PromotionMapByIdReq;
import com.ztesoft.remote.params.activity.resp.ActPageResp;
import com.ztesoft.remote.params.activity.resp.ActResp;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-22 15:54
 * To change this template use File | Settings | File Templates.
 */
@ServiceMethodBean(version="1.0")
@Service
public class ZteActivityOpenService {

//    @Resource
    private IActivityService activityService;
    
    private void init() {
    	if (null == activityService) activityService = ApiContextHolder.getBean("activityService");
    }
    
	@ServiceMethod(method="com.ztesoft.remote.activity.queryActById",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public ActResp queryActById(ActByIdReq actByIdReq) {
		this.init();
		return activityService.queryActById(actByIdReq);
	}
	
	@ServiceMethod(method="com.ztesoft.remote.activity.queryActByTagId",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public ActPageResp queryActByTagId(ActByTagIdReq actByTagIdReq) {
		this.init();
		return activityService.queryActByTagId(actByTagIdReq);
	}
	
	@ServiceMethod(method="com.ztesoft.remote.activity.getPromotionMap",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public PromotionMapByIdResp getPromotionMap(PromotionMapByIdReq promotionMapByIdReq) {
		this.init();
		return activityService.getPromotionMap(promotionMapByIdReq);
	}

	@ServiceMethod(method="com.goodsService.activitySpec.get",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
	public ActivitySpecResp getPromotionMap(ActivitySpecReq activitySpecReq) {
		this.init();
		return activityService.getActivitySpec(activitySpecReq);
	}
}
