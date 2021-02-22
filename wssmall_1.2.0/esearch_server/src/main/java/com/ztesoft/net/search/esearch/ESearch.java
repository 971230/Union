package com.ztesoft.net.search.esearch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;

import params.ZteError;

import com.alibaba.fastjson.JSONObject;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.search.common.IESearch;
import com.ztesoft.net.search.conf.EsearchValues;
import commons.BeanUtils;
import commons.CommonTools;

public class ESearch implements IESearch {
	private static List<String> ipAddress = new ArrayList<String>();
	private static final ESearch inst = new ESearch();

	private static Client client = null ;
	
	public ESearch(){
		String ips = EsearchValues.CORE_SEARCH_IP;
		System.out.println("****************esearch服务IP地址配置:"+ips+"****************");
		String[] ss = ips.split(",");
		for(int i = 0 ; i<ss.length;i++){
			String ip = ss[i];
			if(StringUtils.isEmpty(ip))continue;
			ipAddress.add(ip);
		}
		
		Settings settings = Settings.settingsBuilder()
	                .put("cluster.name", EsearchValues.ESEARCH_CLUSTER_NAME).build();
	    TransportClient tclient = TransportClient.builder().settings(settings).build();
	    if(ipAddress.size()==0)CommonTools.addError(new ZteError("-1", "没有配置elasticsearch服务器IP地址"));
		try {
			for(String esip : ipAddress){
				tclient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esip), 9300));
			}
		} catch (UnknownHostException e) {
			System.out.println("初始化esearch client失败!");
			e.printStackTrace();
		}
		
	    client = tclient;
	}
	
	static{
		String ips = EsearchValues.CORE_SEARCH_IP;
		System.out.println("****************esearch服务IP地址配置:"+ips+"****************");
		String[] ss = ips.split(",");
		for(int i = 0 ; i<ss.length;i++){
			String ip = ss[i];
			if(StringUtils.isEmpty(ip))continue;
			ipAddress.add(ip);
		}
		
		Settings settings = Settings.settingsBuilder()
	                .put("cluster.name", EsearchValues.ESEARCH_CLUSTER_NAME).build();
	    TransportClient tclient = TransportClient.builder().settings(settings).build();
	    if(ipAddress.size()==0)CommonTools.addError(new ZteError("-1", "没有配置elasticsearch服务器IP地址"));
		try {
			for(String esip : ipAddress){
				tclient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esip), 9300));
			}
		} catch (UnknownHostException e) {
			System.out.println("初始化esearch client失败!");
			e.printStackTrace();
		}
		
	    client = tclient;
	}

	public static ESearch getInst() throws Exception{
		return inst;
	}
	
	
	@Override
	public List<ESearchData> search(String index,String type,Map<String,String> qsParams,Map<String,String> boolParams) throws Exception {
		List<ESearchData> esList = new ArrayList<ESearchData>();
		
		SearchRequestBuilder sqb = null;
		if(StringUtils.isNotEmpty(index)&&StringUtils.isNotEmpty(type)){
			client.prepareSearch(index,type);
		}else{
			sqb = client.prepareSearch();
		}
		sqb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
		if(qsParams==null||qsParams.size()==0)return null;
		if(qsParams!=null&&qsParams.size()>0){
			String conditions = "";
			for(String key : qsParams.keySet()){
				String value = qsParams.get(key);
//				key = key.replace(":", "\\:");
//				key = "\""+key+"\"";
				if(StringUtils.isNotEmpty(value)){
					conditions = conditions+value;
				}
				conditions = conditions+key+" ";
			}
			conditions = conditions.substring(0,conditions.length()-1);
			QueryStringQueryBuilder qb = QueryBuilders.queryStringQuery(conditions);
			
			sqb.setQuery(qb);
		}
		
//		if(boolParams!=null&&boolParams.size()>0){
//			BoolQueryBuilder bb = QueryBuilders.boolQuery();
//			for(String key : boolParams.keySet()){
//				String value = boolParams.get(key);
//				bb.must(QueryBuilders.matchQuery(key, value));
//				//TODO 这里需要增加多种查询组合方式的逻辑
//				
//			}
//			sqb.setQuery(bb);
//		}
		
		SearchResponse rsp = sqb.setFrom(0).setSize(100).execute().actionGet();
		for (SearchHit hit : rsp.getHits().getHits()) {
			Map<String,Object> values = hit.getSource();
			String indexName = hit.getIndex();
			String typeName = hit.getType();
			if(indexName.startsWith(".kibana")||indexName.startsWith(".marvel"))
				continue;
			values.put("index", indexName); 
			values.put("type", typeName);
			values.put("log_id", hit.getId());
			
			ESearchData esearch = new ESearchData();
			BeanUtils.mapToBean(values,esearch);
			esList.add(esearch);
		}
//		这里代码有问题
//		while(true){
//			rsp = client.prepareSearchScroll(rsp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
//			if (rsp.getHits().getHits().length == 0) {
//				break;
//			}
//		}
//		client.close();
		
		System.out.println("Esearch.search 查询成功!index:"+index+";type:"+type+";qsParams:"+qsParams);
		return esList;
	}

	@Override
	public String insertMap(String indexName, String typeName, String id,
			Map<String, Object> map) throws Exception {
		IndexResponse response = client.prepareIndex(indexName, typeName,id).setSource(map).get();
//		client.close();
		System.out.println("Esearch.insertMap 报文插入成功 :index:"+indexName+" type:"+typeName+" id:"+id+";map:"+map);
		return response.getId();
	}

	@Override
	public String insertJson(String indexName, String typeName, String id,
			String json) throws Exception {
		IndexResponse response = client.prepareIndex(indexName, typeName,id).setSource(json).get();
//		client.close();
		return response.getId();
	}

	@Override
	public String insertBatchMap(String indexName, String typeName,
			List<Map> list, String idFieldName) throws Exception {
		if(list==null||list.size()==0)return null;
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(Map esMap : list){
			String id = (String) esMap.get(idFieldName);
			bulkRequest.add(new IndexRequest(indexName,typeName,id).source(esMap));
		}
		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
		   CommonTools.addError("－1", "esearch插入报错:详细信息"+bulkResponse.buildFailureMessage());
		}
//		client.close();
		System.out.println("Esearch.insertBatchMap 报文插入成功 :index:"+indexName+" type:"+typeName+" lsit:"+list);
		return null;
	}

	@Override
	public String insertBatchJson(String indexName, String typeName,
			List<String> list, String idFieldName) throws Exception {
		if(list==null||list.size()==0)return null;
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(String json : list){
			JSONObject jsoObj = (JSONObject) JSONObject.parseObject(json.toString());
			bulkRequest.add(new IndexRequest(indexName,typeName,jsoObj.getString(idFieldName)).source(json));
		}
		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
		   CommonTools.addError("－1", "esearch插入报错:详细信息"+bulkResponse.buildFailureMessage());
		}
//		client.close();
		return null;
	}

	@Override
	public String updateMap(String indexName, String typeName,
			Map<String, Object> map, String id) throws Exception {
		
		if(map==null||map.size()==0)return null;
		
		UpdateRequest updateRequest = new UpdateRequest();
    	updateRequest.index(indexName).type(typeName).id(id);
    	updateRequest.timeout(TimeValue.timeValueMillis(20000));
    	updateRequest.doc(map);
    	
    	UpdateResponse uresp = client.update(updateRequest).get();
    	
//    	client.close();
    	System.out.println("Esearch.updateMap 报文更新成功:--index:"+indexName+" type:"+typeName+" id:"+id+";map:"+map);
		return uresp.getId();
	}

	@Override
	public String updateJson(String indexName, String typeName, String json,
			String id) throws Exception {
		if(StringUtils.isEmpty(json))return null;
		
		UpdateRequest updateRequest = new UpdateRequest();
    	updateRequest.index(indexName).type(typeName).id(id);
    	updateRequest.timeout(TimeValue.timeValueMillis(20000));
    	updateRequest.doc(json);
    	
    	UpdateResponse uresp = client.update(updateRequest).get();
    	
//    	client.close();
    	
		return uresp.getId();
	}

	@Override
	public String updateBatchMap(String indexName, String typeName,
			List<Map> list, String idFieldName) throws Exception {
		if(list==null||list.size()==0)CommonTools.addError(new ZteError("-1", "未能找到纪录更新!"));
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(Map esMap : list){
			String id = (String) esMap.get(idFieldName);
			UpdateRequest updateRequest = new UpdateRequest();
	    	updateRequest.index(indexName).type(typeName).id(id);
	    	updateRequest.timeout(TimeValue.timeValueMillis(20000));
	    	updateRequest.doc(esMap);
			bulkRequest.add(updateRequest);
		}
		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
		   CommonTools.addError("－1", "esearch插入报错:详细信息"+bulkResponse.buildFailureMessage());
		}
//		client.close();
		System.out.println("Esearch.updateBatchMap 报文更新成功--index:"+indexName+" type:"+typeName+"list:"+list);
		return "success";
	}

	@Override
	public String updateBatchJson(String indexName, String typeName,
			List<String> list, String idFieldName) throws Exception {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(String json : list){
			JSONObject jsoObj = (JSONObject) JSONObject.parseObject(json.toString());
			UpdateRequest updateRequest = new UpdateRequest();
	    	updateRequest.index(indexName).type(typeName).id(jsoObj.getString(idFieldName));
	    	updateRequest.timeout(TimeValue.timeValueMillis(20000));
	    	updateRequest.doc(json);
			bulkRequest.add(updateRequest);
		}
		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
		   CommonTools.addError("－1", "esearch插入报错:详细信息"+bulkResponse.buildFailureMessage());
		}
//		client.close();
		return null;
	}

	@Override
	public ESearchData get(String indexName, String typeName, String id)
			throws Exception {
		GetResponse response = client.prepareGet(indexName, typeName, id)
						        .setOperationThreaded(false)
						        .get();
		Map<String,Object> values = response.getSource();
    	String index = response.getIndex();
    	String type = response.getType();
        values.put("log_id", id);
        values.put("index", index);
        values.put("type", type);
        
        ESearchData esearch = new ESearchData();
        BeanUtils.mapToBean(values,esearch);
//        client.close();
        System.out.println("Esearch.get 报文查询完成 :index:"+indexName+" type:"+typeName+" id:"+id+";Esdata:"+esearch);
        
		return esearch;
	}

	@Override
	public String delete(String indexName, String typeName, String id)
			throws Exception {
		
		return null;
	}

	@Override
	public void clientClose() {
		client.close();
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		try {
//	        ESearchData esData = new ESearchData();
//	        esData.setIndex("inf_comm_client_calllog");
//	        esData.setType("inf_comm_client_calllog1");
//	        esData.setLog_id("log20151224");
//	        esData.setIn_param("in_param");
//	        esData.setOut_param("out_param");
//	        esData.setKeyword_id("keywordid");
//	        esData.setKeyword_value("keywordvalue");
//	        esData.setClass_id("class_id");
//	        esData.setClass_name("class_name");
//			Map map = new HashMap();
//			BeanUtils.copyProperties(map, esData);
//	        
//			IndexResponse response = client.prepareIndex("inf_comm_client_calllog_id", "inf_comm_client_calllog1","log20151224").setSource(map).get();
//	        Map qsParams = new HashMap();
//	        qsParams.put("\"ecaop.trades.query.resi.term.autochg\"","operation_code:");
//	        qsParams.put("\\\"\"rscStateCode\"\\:\"0000\"\\\"", "-out_param:");
//	        qsParams.put("\"终端的状态不是预占状态不能进行释放\"", "-out_param:");
//	        qsParams.put("\"无此资源信息\"", "-out_param:");
//			List<ESearchData> esList = getInst().search("", "", qsParams, null);
//			System.out.println(esList.toString());
			
//			ESearchData esData = esList.get(0);
//			String indexName = esData.getIndex();
//			String typeName = esData.getType();
//			List<Map> mapList = new ArrayList<Map>();
//			for(ESearchData es : esList){
//				es.setClass_id("class_id");
//				es.setClass_name("class_name");
//				
//				Map map = new HashMap();
//				BeanUtils.copyProperties(map, es);
//				mapList.add(map);
//			}
			
//			esData.setKeyword_id("152401105380000012");
//			esData.setKeyword_value("贷款买房凉快舒服凉快地方");
//			Map map = new HashMap();
//			BeanUtils.copyProperties(map, esData);
//			String id = getInst().updateMap(esData.getIndex(), esData.getType(), map, esData.getLog_id());
			
//			String id = getInst().updateBatchMap(indexName, typeName, mapList, "log_id");
//			
//			List<ESearchData> es1List = getInst().search("", "", qsParams, null);
//			System.out.println(es1List.toString());
//	        client.close();
			System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
