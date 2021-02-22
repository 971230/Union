package zte.net.iservice;

import zte.params.region.req.RegionsGetReq;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsGetResp;
import zte.params.region.resp.RegionsListResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="用户地区API",summary="用户地区API[查询省、市、区列表]")
public interface IRegionService {

	/**
	 * 查询省、市、区列表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询省、市、区列表",summary="查询省、市、区列表") 
	public RegionsListResp listRegions(RegionsListReq req);
	
	/**
	 * 查询区域信息
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询区域信息",summary="查询区域信息") 
	public RegionsGetResp getRegion(RegionsGetReq req);
	
}
