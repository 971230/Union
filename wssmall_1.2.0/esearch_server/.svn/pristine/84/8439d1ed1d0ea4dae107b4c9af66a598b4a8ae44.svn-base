package com.ztesoft.net.search.common;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.model.ESearchData;

public interface IESearch {
	
	public String insertMap(String indexName, String typeName,String id, Map<String, Object> map) throws Exception;
	
	public String insertJson(String indexName, String typeName,String id, String json) throws Exception;
	
	public String insertBatchMap(String indexName, String typeName,List<Map> list,String idFieldName) throws Exception;
	
	public String insertBatchJson(String indexName,String typeName,List<String> list,String idFieldName) throws Exception;
	
	public String delete(String indexName, String typeName,String id) throws Exception;
	
	public String updateMap(String indexName,String typeName,Map<String,Object> map,String id) throws Exception;
	
	public String updateJson(String indexName,String typeName,String json,String id) throws Exception;
	
	public String updateBatchMap(String indexName, String typeName,List<Map> list,String idFieldName) throws Exception;
	
	public String updateBatchJson(String indexName,String typeName,List<String> list,String idFieldName) throws Exception;
	
	public ESearchData get(String indexName,String typeName,String id) throws Exception;
	
	public List<ESearchData> search(String index,String type,Map<String,String> qsParams,Map<String,String> boolParams) throws Exception;
	
	public void clientClose();
	
}
