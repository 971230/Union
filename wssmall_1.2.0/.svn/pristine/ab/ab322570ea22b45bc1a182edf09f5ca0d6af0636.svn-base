package com.ztesoft.remote.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.params.adv.req.AdColumnReq;
import com.ztesoft.remote.params.adv.req.AdvAddReq;
import com.ztesoft.remote.params.adv.req.AdvCountReq;
import com.ztesoft.remote.params.adv.req.AdvDeleteReq;
import com.ztesoft.remote.params.adv.req.AdvHomeReq;
import com.ztesoft.remote.params.adv.req.AdvListReq;
import com.ztesoft.remote.params.adv.req.AdvPageReq;
import com.ztesoft.remote.params.adv.req.AdvPosReq;
import com.ztesoft.remote.params.adv.req.AdvReq;
import com.ztesoft.remote.params.adv.req.DelAdvReq;
import com.ztesoft.remote.params.adv.resp.AdColumnResp;
import com.ztesoft.remote.params.adv.resp.AdvAddResp;
import com.ztesoft.remote.params.adv.resp.AdvCountResp;
import com.ztesoft.remote.params.adv.resp.AdvDeleteResp;
import com.ztesoft.remote.params.adv.resp.AdvHomeResp;
import com.ztesoft.remote.params.adv.resp.AdvListResp;
import com.ztesoft.remote.params.adv.resp.AdvPageResp;
import com.ztesoft.remote.params.adv.resp.AdvResp;
import com.ztesoft.remote.params.adv.resp.EmptyParamOutResp;


@ZteSoftCommentAnnotation(type="class",desc="广告类API",summary="广告、广告位相关管理API")
public interface IAdvService {
	
	@ZteSoftCommentAnnotation(type="method",desc="根据广告位标识查询广告位及广告信息",summary="根据广告位标识查询广告位及广告信息")
	public AdvResp queryAdByAcId(AdvReq advReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据工号删除广告信息",summary="根据工号删除广告信息并归档到历史表")
    public EmptyParamOutResp delAdvbyStaffNo(DelAdvReq delAdvReq);
    
	@ZteSoftCommentAnnotation(type="method",desc="获取广告数量",summary="获取广告数量(有未审核的和全部的)")
    public AdvCountResp getAdvCount(AdvCountReq advCountReq);
    
	@ZteSoftCommentAnnotation(type="method",desc="获取广告位信息",summary="获取广告位信息(根据广告位标识或广告位类型和位置获取)")
    public AdColumnResp getAdColumnDetail(AdColumnReq adColumnReq);
    
	@ZteSoftCommentAnnotation(type="method",desc="获取所有广告位信息",summary="获取所有广告位信息(一种是正常的广告位，另一种是产品类别对应的广告位)")
    public AdColumnResp listAllAdvPos(AdvPosReq advPosReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取首页轮播图",summary="根据id获取首页广告轮播图")
	public AdvHomeResp getHomeAdv(AdvHomeReq advHomeReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取广告分页对象",summary="根据类型或名称获取广告分页对象")
	public AdvPageResp listAdvPage(AdvPageReq advPageReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="添加广告",summary="添加广告")
	public AdvAddResp addAdv(AdvAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="删除广告",summary="删除广告")
	public AdvDeleteResp deleteAdv(AdvDeleteReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取广告列表",summary="根据staff_no获取广告列表")
	public AdvListResp listAdv(AdvListReq req);
}