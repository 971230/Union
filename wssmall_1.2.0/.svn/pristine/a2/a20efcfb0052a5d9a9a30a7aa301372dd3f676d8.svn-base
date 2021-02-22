package zte.net.iservice;

import java.util.List;

import params.RuleParams;
import params.ZteResponse;
import zte.net.common.params.req.QueryRequest;
import zte.net.common.params.req.ZteInstInsertRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.req.ZteVoInsertRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.common.params.resp.ZteInstQueryResponse;
import zte.net.common.params.resp.ZteInstUpdateResponse;
import zte.net.common.params.resp.ZteVoInsertResponse;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="业务日志API能力封装",summary="业务日志API能力封装")
public interface ILogsServices {


	/**
	 * 获取订单树
	 * @param order_id
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "获取ZteReques实例数据", summary = "获取ZteReques实例数据")
	public ZteInstQueryResponse getZteRequestInst(ZteInstQueryRequest instParam);
	
	/**
	 * 更新订单树
	 * @param order_id
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "更新ZteReques实例数据", summary = "更新ZteReques实例数据")
	public ZteInstUpdateResponse updateZteRequestInst(ZteInstUpdateRequest req);
	
	
	
	/**
	 * 查询操作
	 * @param order_id
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "baseSupport查询能力开放", summary = "baseSupport查询能力开放")
	public QueryResponse baseSupportQuery(QueryRequest req);
	
	
	/**
	 * 插入操作
	 * @param order_id
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "baseSupport插入能力开放", summary = "baseSupport插入能力开放")
	public ZteVoInsertResponse voInsert(ZteVoInsertRequest req);
	
	
	/**
	 * 执行规则
	 * @作者 wu.i
	 * @创建日期 2014-3-4 
	 * @param RuleParams
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="执行应用平台规则",summary="执行应用平台规则")
	public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type="method",desc="更新订单树",summary="更新订单树")
	public ZteResponse cacheOrderTree(OrderTreeBusiRequest orderTreeBusiRequest) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type="method",desc="内存获取订单树",summary="内存获取订单树")
	public OrderTreeBusiRequest getOrderTreeFromCache(String order_id);
	@ZteSoftCommentAnnotation(type = "method", desc = "设置属性实例数据写入redis", summary = "设置属性实例数据写入redis")
	public ZteResponse cacheAttrInstToRedis(List<AttrInstBusiRequest> instList) throws ApiBusiException;
	
	/**
	 * 更新业务对象
	 * @param order_id
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "更新ZteReques实例数据", summary = "更新ZteReques实例数据")
	public ZteInstUpdateResponse updatePofZteRequestInst(ZteInstUpdateRequest req);
	
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取缓存实例实例数据", summary = "获取缓存实例实例数据")
	public  List<AttrInstBusiRequest> getAttrInstBusiRequestFromRedisByOrderId(String order_id);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "设置缓存实例实例数据", summary = "设置缓存实例实例数据")
	public  void updateAttrInstBusiRequestFromRedisByOrderId(List<AttrInstBusiRequest> instList);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取缓存实例实例数据", summary = "获取缓存实例实例数据")
	public  AttrInstBusiRequest getAttrInstBusiRequestByOrderId(String order_id,String field_name);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "设置属性标志", summary = "设置属性标志")
	public  void setAttrFlag(String order_id,String flag_value);
	@ZteSoftCommentAnnotation(type = "method", desc = "插入实例数据,不更新缓存", summary = "插入实例数据,不更新缓存")
	public ZteBusiResponse insertZteRequestInst(ZteInstInsertRequest request);
	
}
