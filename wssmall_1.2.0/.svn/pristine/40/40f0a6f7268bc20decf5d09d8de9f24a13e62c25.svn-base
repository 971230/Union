package zte.net.iservice;

import params.req.OrderExpMarkProcessedReq;
import params.req.OrderExpWriteForBusReq;
import params.req.OrderExpWriteForInfReq;
import params.req.QueryExpCatalogReq;
import params.req.QueryExpOrderStatisticsReq;
import params.resp.OrderExpMarkProcessedResp;
import params.resp.OrderExpWriteResp;
import params.resp.QueryExpCatalogResp;
import params.resp.QueryExpOrderStatisticsResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="异常能力管理API",summary="异常能力管理API[异常能力]")
public interface IOrderExpServices {
	@ZteSoftCommentAnnotation(type="method",desc="异常能力写入",summary="异常能力写入[接口异常]")
	public OrderExpWriteResp exeOrderExpWrite(OrderExpWriteForInfReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="异常能力写入",summary="异常能力写入[业务异常]")
	public OrderExpWriteResp exeOrderExpWrite(OrderExpWriteForBusReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="异常单归类",summary="查询所有异常单归类")
	public QueryExpCatalogResp queryExpCatalogs(QueryExpCatalogReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="异常单统计",summary="查询所有异常单统计信息")
	public QueryExpOrderStatisticsResp queryExpOrderStatistics(QueryExpOrderStatisticsReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="异常单标记已处理",summary="异常单标记已处理")
	public OrderExpMarkProcessedResp orderExpMarkProcessed(OrderExpMarkProcessedReq req);
}