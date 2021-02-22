package zte.net.iservice;

import params.req.EsearchAddReq;
import params.req.EsearchGetReq;
import params.req.EsearchLogInfoDateReq;
import params.req.EsearchLogInfoIdsReq;
import params.req.EsearchSearchReq;
import params.req.EsearchUpdateClassReq;
import params.req.EsearchUpdateReq;
import params.resp.EsearchAddResp;
import params.resp.EsearchGetResp;
import params.resp.EsearchLogInfoDateResp;
import params.resp.EsearchLogInfoIdsResp;
import params.resp.EsearchSearchResp;
import params.resp.EsearchUpdateClassResp;
import params.resp.EsearchUpdateResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="esearch搜索引擎API",summary="esearch搜索引擎API")
public interface IESearchService {

	@ZteSoftCommentAnnotation(type="method",desc="插入一条报文纪录",summary="插入一条报文纪录")
	public EsearchAddResp insert(EsearchAddReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="更新单条报文纪录",summary="更新单条报文纪录")
	public EsearchUpdateResp update(EsearchUpdateReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="根据关键字信息更新归类信息",summary="根据关键字信息更新归类信息")
	public EsearchUpdateClassResp updateClassByKeyword(EsearchUpdateClassReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="根据id取出单条报文纪录",summary="根据id取出单条报文纪录")
	public EsearchGetResp get(EsearchGetReq req) throws Exception;

	@ZteSoftCommentAnnotation(type="method",desc="全量导入接口日志报文",summary="全量导入接口日志报文")
	public EsearchLogInfoDateResp logInfoIndexByDateRange(EsearchLogInfoDateReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="根据id集合插入日志报文",summary="根据id集合插入日志报文")
	public EsearchLogInfoIdsResp logInfoIndexByIds(EsearchLogInfoIdsReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type="method",desc="根据匹配规则查询报文",summary="根据匹配规则查询报文")
	public EsearchSearchResp search(EsearchSearchReq req) throws Exception;
	
	
}
