package zte.net.iservice;


import zte.net.ecsord.params.spec.req.NumberSpecReq;
import zte.net.ecsord.params.spec.resp.NumberSpecResp;
import zte.params.number.req.NoCoDeleteReq;
import zte.params.number.req.NoInfoByNoReq;
import zte.params.number.req.NumberChangeStatusReq;
import zte.params.number.req.NumberSynInfoReq;
import zte.params.number.resp.NoCoDeleteResp;
import zte.params.number.resp.NoInfoByNoResp;
import zte.params.number.resp.NumberChangeStatusResp;
import zte.params.number.resp.NumberSynInfoResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class", desc="号码类API", summary="号码类API")
public interface INumberService {
	
	@ZteSoftCommentAnnotation(type="method",desc="获取号码基本信息",summary="根据号码获取号码基本信息")
	public NoInfoByNoResp queryNoInfoByNo(NoInfoByNoReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取号码同步信息",summary="获取号码同步信息")
	public NumberSynInfoResp queryNumSynInfo(NumberSynInfoReq numberReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="号码状态信息修改",summary="号码状态信息修改")
	public NumberChangeStatusResp changeNumStatus(NumberChangeStatusReq numeroReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="号码回收，号码信息删除",summary="号码回收，号码同步信息删除")
	public NoCoDeleteResp deleteNoCo(NoCoDeleteReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取号码规格信息",summary="获取号码规格信息")
	public NumberSpecResp getNumberSpec(NumberSpecReq req);
}