package zte.net.iservice;


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
import zte.params.goods.req.DcPublicInfoCacheProxyReq;
import zte.params.goods.resp.DcPublicInfoCacheProxyResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class", desc="缓存类API", summary="缓存类API")
public interface IDcPublicInfoCacheService {
	
	@ZteSoftCommentAnnotation(type="method",desc="取字典映射数据",summary="取字典映射数据")
	public DcPublicInfoCacheProxyResp getDictRelationValue(DcPublicInfoCacheProxyReq req);

	@ZteSoftCommentAnnotation(type="method",desc="取扩展静态数据",summary="取扩展静态数据",isOpen=false)
    public DcPublicExtListResp listDcPublicData(DcPublicExtListReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="取物流公司人员静态数据",summary="取物流公司人员静态数据",isOpen=false)
	public LogiCompPersonGetResp getLogiCompPersonData(LogiCompPersonGetReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="取订单外部状态静态数据",summary="取订单外部状态静态数据",isOpen=false)
   	public DictRelationListResp getDictRelationListData(DictRelationListReq req);

    @ZteSoftCommentAnnotation(type="method",desc="取es_dc_sql静态数据",summary="取es_dc_sql静态数据",isOpen=false)
   	public DropdownDataResp getDropdownData(DropdownDataReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="取es_post_region寄件方信息",summary="取es_post_region寄件方信息",isOpen=false)
    public PostRegionGetResp getPostRegionData(PostRegionGetReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="取es_regions列表",summary="取es_regions列表",isOpen=false)
    public RegionListResp listRegions(RegionListReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="取总部映射字典值",summary="取总部映射字典值",isOpen=false)
    public ZbDictCodeValueGetResp getZbDictCodeValue(ZbDictCodeValueGetReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="取物流公司编码",summary="取物流公司编码",isOpen=false)
    public LogiCompCodeGetResp getLogiCompanyCode(LogiCompCodeGetReq req);
    
    @ZteSoftCommentAnnotation(type="method",desc="取必选包依赖元素",summary="取必选包依赖元素",isOpen=false)
    public BPackageDependElementListResp getBPackageDependElementList(BPackageDependElementGetReq req);

	@ZteSoftCommentAnnotation(type="method",desc="取可选包依赖元素",summary="取可选包依赖元素",isOpen=false)
    public KPackageDependElementListResp getKPackageDependElementList(KPackageDependElementGetReq req);
	
   @ZteSoftCommentAnnotation(type="method",desc="取自由组合依赖元素",summary="取自由组合依赖元素",isOpen=false)
    public void cacheFreedomGruopDependElement();

}