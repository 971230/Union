package zte.net.iservice.impl;


import params.ZteRequest;
import zte.net.ecsord.params.pub.req.BPackageDependElementGetReq;
import zte.net.ecsord.params.pub.req.DcPublicExtListReq;
import zte.net.ecsord.params.pub.req.DictRelationListReq;
import zte.net.ecsord.params.pub.req.DropdownDataReq;
import zte.net.ecsord.params.pub.req.KPackageDependElementGetReq;
import zte.net.ecsord.params.pub.req.LogiCompCodeGetReq;
import zte.net.ecsord.params.pub.req.LogiCompPersonGetReq;
import zte.net.ecsord.params.pub.req.PostRegionGetReq;
import zte.net.ecsord.params.pub.req.RegionListReq;
import zte.net.ecsord.params.pub.req.ZbDictCodeValueGetReq;
import zte.net.ecsord.params.pub.resp.BPackageDependElementListResp;
import zte.net.ecsord.params.pub.resp.DcPublicExtListResp;
import zte.net.ecsord.params.pub.resp.DictRelationListResp;
import zte.net.ecsord.params.pub.resp.DropdownDataResp;
import zte.net.ecsord.params.pub.resp.KPackageDependElementListResp;
import zte.net.ecsord.params.pub.resp.LogiCompCodeGetResp;
import zte.net.ecsord.params.pub.resp.LogiCompPersonGetResp;
import zte.net.ecsord.params.pub.resp.PostRegionGetResp;
import zte.net.ecsord.params.pub.resp.RegionListResp;
import zte.net.ecsord.params.pub.resp.ZbDictCodeValueGetResp;
import zte.net.iservice.IDcPublicInfoCacheService;
import zte.params.goods.req.DcPublicInfoCacheProxyReq;
import zte.params.goods.resp.DcPublicInfoCacheProxyResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;

@ZteSoftCommentAnnotation(type="class", desc="缓存类API", summary="缓存类API")
public class ZteDcPublicInfoCacheOpenService {
	
//	@Resource
	private IDcPublicInfoCacheService dcPublicInfoCacheService;
	
	private void init(ZteRequest request){
		this.dcPublicInfoCacheService = ApiContextHolder.getBean("dcPublicInfoCacheService");
	}
	
	@ServiceMethod(method="com.dcPublicService.DcPublicInfoCache.DictRelation",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DcPublicInfoCacheProxyResp getDictRelationValue(DcPublicInfoCacheProxyReq req) {
		this.dcPublicInfoCacheService = ApiContextHolder.getBean("dcPublicInfoCacheService");
		return dcPublicInfoCacheService.getDictRelationValue(req);
	}
	
	@ServiceMethod(method="com.dcPublicService.dcPublicList.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DcPublicExtListResp listDcPublicData(DcPublicExtListReq req){
		this.init(req);
		req.setRopRequestContext(null); 
		return dcPublicInfoCacheService.listDcPublicData(req);
	}
	@ServiceMethod(method="com.dcPublicService.logiCompPerson.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public LogiCompPersonGetResp getLogiCompPersonData(LogiCompPersonGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getLogiCompPersonData(req);
	}
	@ServiceMethod(method="com.dcPublicService.platFormStatus.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DictRelationListResp getDictRelationListData(DictRelationListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getDictRelationListData(req);
	}
	@ServiceMethod(method="com.dcPublicService.dropDown.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DropdownDataResp getDropdownData(DropdownDataReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getDropdownData(req);
	}
	
	@ServiceMethod(method="com.dcPublicService.postRegion.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PostRegionGetResp getPostRegionData(PostRegionGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getPostRegionData(req);
	}
	
	@ServiceMethod(method="com.dcPublicService.region.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RegionListResp ListRegions(RegionListReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.listRegions(req);
	}
	
	@ServiceMethod(method="com.dcPublicService.ZbDictCodeValue.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbDictCodeValueGetResp getZbDictCodeValue(ZbDictCodeValueGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getZbDictCodeValue(req);
	}
	
	@ServiceMethod(method="com.dcPublicService.logiCompCode.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public LogiCompCodeGetResp getLogiCompanyCode(LogiCompCodeGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getLogiCompanyCode(req);
	}
	
	@ServiceMethod(method="com.dcPublicService.bPackageDependElement.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BPackageDependElementListResp getBPackageDependElementList(BPackageDependElementGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getBPackageDependElementList(req);
	}
	
	@ServiceMethod(method="com.dcPublicService.kPackageDependElement.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public KPackageDependElementListResp getKPackageDependElementList(KPackageDependElementGetReq req){
		this.init(req);
		req.setRopRequestContext(null);
		return dcPublicInfoCacheService.getKPackageDependElementList(req);
	}
	
}