package zte.net.iservice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

import params.RuleParams;
import params.ZteBusiRequest;
import params.ZteResponse;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
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
import zte.net.iservice.ILogsServices;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IRequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.AttrInstRedisTools;
import com.ztesoft.net.mall.core.utils.BusiUtils;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.util.CacheUtils;
import commons.CommonTools;

import consts.ConstsCore;
/**
 * 
 * @author wu.i
 * 业务服务日志
 *
 */
public class LogsServices extends BaseSupport implements ILogsServices {

	@Resource
	private IRequestStoreManager requestStoreManager;
	@Resource
	private ICacheUtil cacheUtil;

	/**
	 * 插入实例数据
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	@SuppressWarnings({ "rawtypes"})
	public ZteInstQueryResponse getZteRequestInst(ZteInstQueryRequest instParam) {
		return requestStoreManager.getZteRequestInst(instParam);
	}

	/**
	 * 更新实例输数据
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	@SuppressWarnings({ "rawtypes"})
	public ZteInstUpdateResponse updateZteRequestInst(ZteInstUpdateRequest zteInstParamReq){
		return requestStoreManager.updateZteRequestInst(zteInstParamReq);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "执行应用平台规则", summary = "执行应用平台规则")
	public ZteResponse processrule(RuleParams ruleParams)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "baseSupport查询能力开放", summary = "baseSupport查询能力开放")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryResponse baseSupportQuery(final QueryRequest req) {
		//未查询到数据
		QueryResponse response = new QueryResponse();
		List queryRets = new ArrayList();
		String attr_inst_from_redis ="yes";//cacheUtil.getConfigInfo(EcsOrderConsts.ATTR_INST_FROM_REDIS);
		//属性数据redis改造，add by wui
		RequestBeanDefinition<ZteBusiRequest> serviceDefinition = BusiUtils.getRequestServiceDefinition((ZteBusiRequest)req.getQueryObject());
		if(ConstsCore.CONSTS_YES.equals(attr_inst_from_redis)  && req.getQueryObject() instanceof AttrInstBusiRequest){
			 AttrInstRedisTools attrInstRedisTools = new AttrInstRedisTools();
			 queryRets = AttrInstRedisTools.getAttrInstBusiRequestFromRedisByOrderId((String)req.getQueryParams().get(0),req.getQuerySql());
		}else{
			
			queryRets = this.baseDaoSupport.queryForList(req.getQuerySql(), new RowMapper(){
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					ZteBusiRequest zteRequest = null;
					try{
						zteRequest = (ZteBusiRequest) BeanUtils.instantiateClass(req.getQueryObject().getClass());
						BusiUtils.dbToBusiEntryMapper(rs,zteRequest);
					}catch(Exception e){
						e.printStackTrace();
					}
					return zteRequest;
				}
			},req.getQueryParams().get(0));
		}
		if(ListUtil.isEmpty(queryRets))
			return response;
		response.setQueryResults(queryRets);
		return response;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "baseSupport插入能力开放", summary = "baseSupport插入能力开放")
	public ZteVoInsertResponse voInsert(ZteVoInsertRequest req) {
		ZteVoInsertResponse response = new ZteVoInsertResponse();
		this.baseDaoSupport.insert(req.getTable_name(), req.getSerObject());
		return response;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "orderTree设置缓存", summary = "orderTree设置缓存")
	public ZteResponse cacheOrderTree(OrderTreeBusiRequest orderTreeBusiRequest) throws ApiBusiException{
		requestStoreManager.cacheOrderTree(orderTreeBusiRequest);
		return new ZteResponse();
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "设置属性实例数据写入redis", summary = "设置属性实例数据写入redis")
	public ZteResponse cacheAttrInstToRedis(List<AttrInstBusiRequest> instList) throws ApiBusiException{
		AttrInstRedisTools.setAttrInstBusiRequestsToRedis(instList);
		return new ZteResponse();
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "内存中获取orderTree", summary = "内存中获取orderTree")
	public OrderTreeBusiRequest getOrderTreeFromCache(String order_id){
		String key = BusiUtils.genCachePrimikey("order_id", order_id);
		Object object =  CacheUtils.getCache(EcsOrderConsts.ORDER_TREE_NAMESPACE,RequestStoreManager.DC_TREE_CACHE_NAME_KEY+key);
		if(object instanceof String) //获取缓存，值为String则转对象
			return CommonTools.jsonToBean((String)object,OrderTreeBusiRequest.class);
		return (OrderTreeBusiRequest)object;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "更新ZteReques实例数据", summary = "更新ZteReques实例数据")
	public ZteInstUpdateResponse updatePofZteRequestInst(ZteInstUpdateRequest zteInstParamReq) {
		return requestStoreManager.updatePofZteRequestInst(zteInstParamReq);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取缓存实例实例数据", summary = "获取缓存实例实例数据")
	public  List<AttrInstBusiRequest> getAttrInstBusiRequestFromRedisByOrderId(String order_id) {
		return AttrInstRedisTools.getAttrInstBusiRequestFromRedisByOrderId(order_id,"");
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取缓存实例实例数据", summary = "获取缓存实例实例数据")
	public  AttrInstBusiRequest getAttrInstBusiRequestByOrderId(String order_id,String field_name) {
		return AttrInstRedisTools.getAttrInstBusiRequestByOrderId(order_id, field_name);
	}
	
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "设置属性标志", summary = "设置属性标志")
	public  void setAttrFlag(String order_id,String flag_value) {
		AttrInstRedisTools.setAttrFlag(order_id,flag_value);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "设置缓存实例实例数据", summary = "设置缓存实例实例数据")
	public  void updateAttrInstBusiRequestFromRedisByOrderId(List<AttrInstBusiRequest> instList) {
		AttrInstRedisTools.setAttrInstBusiRequestsToRedis(instList);
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "插入实例数据,不更新缓存", summary = "插入实例数据,不更新缓存")
	public ZteBusiResponse insertZteRequestInst(ZteInstInsertRequest request){
		return this.requestStoreManager.insertZteRequestInst(request);
	}
}
