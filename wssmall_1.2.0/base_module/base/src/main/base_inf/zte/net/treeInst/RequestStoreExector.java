package zte.net.treeInst;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteBusiRequest;
import params.ZteRequest;
import zte.net.common.params.ZtePofBusiRequest;
import zte.net.common.params.req.QueryRequest;
import zte.net.common.params.req.ZteInstInsertRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.req.ZtePofInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.common.params.resp.ZteInstUpdateResponse;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.iservice.ILogsServices;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.mall.core.utils.IBusiManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 
 * @author wu.i
 * 
 *
 */
public class RequestStoreExector{
	
	ILogsServices logsServices;
	IBusiManager busiManager;

	private void init() {
		if(logsServices==null)
		logsServices = ApiContextHolder.getBean("logsServices");
		if(busiManager==null)
		busiManager=  ApiContextHolder.getBean("busiManager");
	}
	
	public static RequestStoreExector orderTreeCacheExector = new RequestStoreExector();
	
	public  static  RequestStoreExector getInstance(){
		return  orderTreeCacheExector;
	}

	/**
	 * 获取实体数据
	 * @param <T>
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  <T> T getZteRequestInst(ZteInstQueryRequest<? extends ZteBusiRequest> req){
		this.init();
		return (T) logsServices.getZteRequestInst(req).getQueryObject();
	}
	
	/**
	 * 更新订单树
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  <T> T  updateZteRequestInst(ZteInstUpdateRequest<? extends ZteBusiRequest> req){
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteInstUpdateResponse resp = client.execute(req, ZteInstUpdateResponse.class);
		if(!ConstsCore.ERROR_SUCC.equals(resp.getError_code())){
			CommonTools.addFailError(resp.getError_msg());
		}
		return (T)resp.getQueryObject();
	}
	
	
	//TODO 构成查询条件，待优化
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T orderProdBusiLoadAssemble(ZteInstQueryRequest instQuery,QueryResponse<? extends Object> resp,Object retObject) { //返回对象类型
		QueryRequest req = new QueryRequest<ZteRequest>();
		Map params = instQuery.getQueryParmas();
		StringBuffer querySqlBuffer =new StringBuffer((String) params.get(ConstsCore.QUERY_SQL_CONSTS));
		String query_sql = querySqlBuffer.append("  and item_id = ? ").toString();
		List<String> sqlParams = new ArrayList();
		sqlParams.add((String)params.get("item_id"));
		return (T)orderBusiLoadAssemble(instQuery, resp, retObject, query_sql, sqlParams);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T orderProdExtBusiLoadAssemble(ZteInstQueryRequest instQuery,QueryResponse<? extends Object> resp,Object retObject) { //返回对象类型
		QueryRequest req = new QueryRequest<ZteRequest>();
		Map params = instQuery.getQueryParmas();
		StringBuffer querySqlBuffer =new StringBuffer((String) params.get(ConstsCore.QUERY_SQL_CONSTS));
		String query_sql = querySqlBuffer.append(" and item_prod_inst_id =? ").toString();
		List<String> sqlParams = new ArrayList();
		sqlParams.add((String)params.get("item_prod_inst_id"));
		return (T)orderBusiLoadAssemble(instQuery, resp, retObject, query_sql, sqlParams);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T giftParamBusiLoadAssemble(ZteInstQueryRequest instQuery,QueryResponse<? extends Object> resp,Object retObject) { //返回对象类型
		QueryRequest req = new QueryRequest<ZteRequest>();
		Map params = instQuery.getQueryParmas();
		StringBuffer querySqlBuffer =new StringBuffer((String) params.get(ConstsCore.QUERY_SQL_CONSTS));
		String query_sql = querySqlBuffer.append(" and gift_inst_id =? ").toString();
		List<String> sqlParams = new ArrayList();
		sqlParams.add((String)params.get("gift_inst_id"));
		return (T)orderBusiLoadAssemble(instQuery, resp, retObject, query_sql, sqlParams);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T orderBusiLoadAssemble(ZteInstQueryRequest instQuery,QueryResponse<? extends Object> resp,Object retObject) { //返回对象类型
		QueryRequest req = new QueryRequest<ZteRequest>();
		Map params = instQuery.getQueryParmas();
		StringBuffer querySqlBuffer =new StringBuffer((String) params.get(ConstsCore.QUERY_SQL_CONSTS));
		String query_sql = querySqlBuffer.append(" and order_id = ? ").toString();
		List<String> sqlParams = new ArrayList();
		sqlParams.add((String)params.get("order_id"));
		return (T)orderBusiLoadAssemble(instQuery, resp, retObject, query_sql, sqlParams);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T orderBusiLoadAssemble(ZteInstQueryRequest instQuery,QueryResponse<? extends Object> resp,Object retObject,String query_sql,List sqlParams) { //返回对象类型
		QueryRequest req = new QueryRequest<ZteRequest>();
		Map params = instQuery.getQueryParmas();
		req.setQuerySql(query_sql);
		req.setQueryParams(sqlParams);
		req.setQueryObject(instQuery.getQueryObject());
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		resp = client.execute(req, QueryResponse.class);
		Object  object = resp.getQueryResults();
		if(object ==null)
			return null;
		if(!(retObject instanceof List)){ //返回列表
			return (T)((List)resp.getQueryResults()).get(0);
		}
		return (T)object;
	}
	
	@SuppressWarnings({ "rawtypes"})
	public static <T> T orderBusiStoreAssemble(ZteInstUpdateRequest updateRequest,ZteBusiRequest zteRequest) {
		updateRequest.setUpdateRequest(zteRequest);
		T t =(T)RequestStoreExector.getInstance().updateZteRequestInst(updateRequest);
		
		return t;
		
	}
	
	
	
	@SuppressWarnings({ "rawtypes"})
	public static <T> T orderDeleteStoreAssemble(ZteInstUpdateRequest updateRequest,ZteBusiRequest zteRequest) {
		updateRequest.setUpdateRequest(zteRequest);
		T t =(T)RequestStoreExector.getInstance().updateZteRequestInst(updateRequest);
		return t;
	}
	
	/**
	 * 更新pof序列化对象
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  <T> T  updateZtePofRequestInst(ZtePofInstUpdateRequest<? extends ZtePofBusiRequest> req){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteInstUpdateResponse resp = client.execute(req, ZteInstUpdateResponse.class);
		return (T)resp.getQueryObject();
	}
	
	@SuppressWarnings({ "rawtypes"})
	public static <T> T orderPofBusiStoreAssemble(ZtePofInstUpdateRequest updateRequest,ZtePofBusiRequest zteRequest) {
		updateRequest.setUpdateRequest(zteRequest);
		T t =(T)RequestStoreExector.getInstance().updateZtePofRequestInst(updateRequest);
		
		return t;
		
	}
	public ZteBusiResponse insertZteRequestInst(ZteBusiRequest request){
		ZteInstInsertRequest insertRequest = new ZteInstInsertRequest();
		insertRequest.setZteBusiRequest(request);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteBusiResponse resp = client.execute(insertRequest, ZteBusiResponse.class);
		return resp;
	}
	public  QueryResponse loadZteBusiRequestByDb(ZteBusiRequest request,Boolean query_his_table,String queryParam){
		init();
		return  busiManager.loadZteBusiRequestByDb( request, query_his_table, queryParam);
	}
}

