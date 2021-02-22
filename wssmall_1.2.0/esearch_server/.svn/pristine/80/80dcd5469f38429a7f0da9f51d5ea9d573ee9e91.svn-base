package com.ztesoft.net.search.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.model.ESearchData;

public interface IESearchManager {
	
	public String insert(ESearchData esearch) throws Exception;
	
	public String update(ESearchData esearch) throws Exception;
	
	public String update(ESearchData esearch,boolean updateNull,boolean updateDelay) throws Exception;
	
	public String updateClassByKeyword(ESearchData esearch,boolean updateDelay) throws Exception;
	
	public List qryDelayUpdateInfo() throws Exception;
	
	public ESearchData get(ESearchData esearch) throws Exception;

	public void logInfoIndexByDateRange(Map<String,Object> values) throws Exception;
	
	public void logInfoIndexByIds(List<String> idList) throws Exception;
	
	public List<ESearchData> search(String indexName,String typeName,Map<String,String> qsParams,Map<String,String> boolParams) throws Exception;
	
	public void completeDelayUpdate(Map fields,Map where) throws Exception;
	
}
