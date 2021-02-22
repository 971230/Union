package com.ztesoft.net.search.service.imp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;
import params.req.EsearchBatchReq;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.search.common.ESearchFactory;
import com.ztesoft.net.search.conf.EsearchValues;
import com.ztesoft.net.search.service.IESearchManager;
import com.ztesoft.net.sqls.SF;
import commons.BeanUtils;
import commons.CommonTools;

public class EsearchManager extends BaseSupport implements IESearchManager {
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String insert(ESearchData esearch) throws Exception {
		if(esearch==null)CommonTools.addError(new ZteError("-1", "esearchData不能为空!"));
		String index = esearch.getIndex();
		String type = esearch.getType();
		String log_id = esearch.getLog_id();
		System.out.println("调用EsearchManager.insert方法：esData:"+esearch);
		
		validate(esearch);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		String dateTime = sdf.format(new Date());
		
		esearch.setCreate_time(dateTime);
		esearch.setBusi_time(dateTime);
		
		esearch.setCreateTimeStr(dateTime);
		esearch.setBusiTimeStr(dateTime);
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_esearch_write = cacheUtil.getConfigInfo(EsearchValues.IS_ESEARCH_WRITE);
		
		if(is_esearch_write.equals("0")){
			System.out.println("esearch写入开关为N，报文未能写入!log_id:"+log_id);
		}else{
			String id = ESearchFactory.getSearchInst().insertMap(index,type,log_id,BeanUtils.bean2Map(new HashMap(), esearch));
		}
		return log_id;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String updateClassByKeyword(ESearchData esearch,boolean updateDelay) throws Exception {
		if(esearch==null)CommonTools.addError(new ZteError("-1", "esearchData不能为空!"));
		String indexName = esearch.getIndex();
		String typeName = esearch.getType();
		String keywordId = esearch.getKeyword_id();
//		String keyword = esearch.getKeyword_value();
		String keywordName = esearch.getKeyword_name();
		String classId = esearch.getClass_id();
//		String classValue = esearch.getClass_name();
		String classValue = esearch.getClass_value();
		
		System.out.println("调用EsearchManager.updateClassByKeyword方法：esData:"+esearch);
		
		if(Boolean.TRUE == updateDelay){
			Map esMap = new HashMap();
//			BeanUtils.bean2Map(esMap, esearch);
//			esMap.put("LOG_ID", esearch.getLog_id());
			esMap.put("ID", "ES"+System.currentTimeMillis());
			esMap.put("INDEXNAME", esearch.getIndex());
			esMap.put("TYPENAME", esearch.getType());
			esMap.put("OPERATION_CODE", esearch.getOperation_code());
			esMap.put("OPERATION_CODE_ID", esearch.getOperation_code_id());
			esMap.put("OBJ_ID", esearch.getObj_id());
			esMap.put("BUSI_TIME", esearch.getBusi_time());
			esMap.put("CREATE_TIME", esearch.getCreate_time());
			esMap.put("KEYWORD_ID", esearch.getKeyword_id());
			esMap.put("KEYWORD_NAME", esearch.getKeyword_name());
			esMap.put("CLASS_ID", esearch.getClass_id());
			esMap.put("CLASS_VALUE", esearch.getClass_value());
//			esMap.put("UPDATE_NULL", "false");
			esMap.put("IS_UPDATED", "-1");
			this.baseDaoSupport.insert("ES_ESEARCH_DELAYUPDATE_INFO", esMap);
			
			System.out.println("插入延迟更新纪录："+esMap);
			return "delay";
		}
		
		if((StringUtils.isEmpty(keywordId)&&StringUtils.isEmpty(keywordName))
				||(StringUtils.isEmpty(classId)&&StringUtils.isEmpty(classValue))){
			CommonTools.addError(new ZteError("-1", "关键字信息、归类id、名称不能为空!"));
		}
		
		Map qsParams = new HashMap();
		if(!StringUtils.isEmpty(keywordId)){
			qsParams.put("+keyword_id:\""+keywordId+"\"", "");
		}else if(!StringUtils.isEmpty(keywordName)){
			qsParams.put("+keyword_value:\""+keywordName+"\"", "");
		}
		List<ESearchData> list = ESearchFactory.getSearchInst().search(indexName,typeName,qsParams, new HashMap());
		
		Map<String,List> indexMap = new HashMap<String,List>();
		for(ESearchData es : list){
			es.setClass_id(classId);
			es.setClass_value(classValue);
			String index = es.getIndex();
			String type = es.getType();
			
			List mapList = indexMap.get(index+"~"+type);
			if(mapList==null){
				mapList = new ArrayList();
				indexMap.put(index+"~"+type, mapList);
			}
			Map esMap = new HashMap();
			BeanUtils.bean2Map(esMap, es);
			mapList.add(esMap);
		}
		for(String key : indexMap.keySet()){
			String[] ss = key.split("~");
			ESearchFactory.getSearchInst().updateBatchMap(ss[0], ss[1], indexMap.get(key), "log_id");
		}
		
		return "success";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void logInfoIndexByDateRange(Map<String,Object> values) throws Exception {
		String fromDate = (String) values.get("fromDate");
		String toDate = (String) values.get("toDate");
		String dateSql = "";
		
//		inf_comm_client_calllog_1_a  (0-35W)
//		inf_comm_client_calllog_1_b  (35W-70W)
//
//		inf_comm_client_calllog_2_a  (70W-105W)
//		inf_comm_client_calllog_2_b  (105W-140W)
//
//		inf_comm_client_calllog_3_a  (140W-175W)
//		inf_comm_client_calllog_3_b  (175W-210W)
//
//		inf_comm_client_calllog_4_a  (210W-245W)
//		inf_comm_client_calllog_4_b  (245W-280W)
//
//		inf_comm_client_calllog_6_a  (280W-324W)
//		inf_comm_client_calllog_5_b  (324W-368W)
		
		String sqlcount = "select count(1) from inf_comm_client_calllog where rsp_xml is not null ";
		if(StringUtils.isNotEmpty(fromDate)){
			dateSql = "and req_time >= to_date('"+fromDate+"','yyyy-MM-dd hh:mi:ss')";
		}
		if(StringUtils.isNotEmpty(toDate)){
			dateSql = "and req_time <= to_date('"+toDate+"','yyyy-MM-dd hh:mi:ss')";
		}
		if(StringUtils.isNotEmpty(dateSql)) sqlcount = sqlcount+dateSql;
		
		int totalNum = this.baseDaoSupport.queryForInt(sqlcount);
		
		int totalPage = 1;
		int pageSize = 1000;
		
		if(totalNum/1000==0){
			totalPage = totalNum/pageSize;
		}else{
			totalPage = totalNum/pageSize+1;
		}
		
		String sql = "select REQ_TIME,LOG_ID,OP_CODE,REQ_XML,RSP_XML from inf_comm_client_calllog where rsp_xml is not null ";
		sql = sql + dateSql;
		for(int i= 1;i<=totalPage;i++){
			EsearchBatchReq esReq = new EsearchBatchReq();
			esReq.setPageNum(i);
			esReq.setPageSize(pageSize);
			esReq.setSql(sql);
			
			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(esReq) {
				
				@SuppressWarnings({"rawtypes" })
				public ZteResponse execute(ZteRequest zteRequest) {
					EsearchBatchReq req = (EsearchBatchReq) zteRequest;
					int pageNum = req.getPageNum();
					int pageSize = req.getPageSize();
					String sql = req.getSql();
					
					Map<String,Object> values = new HashMap<String,Object>();
					values.put("pageNum", pageNum);
					values.put("pageSize", pageSize);
					values.put("sql", sql);
					List<ESearchData> list = EsearchQuerySupport.getInstance().qryForLogInfoPage(values);
					
					Map<String,List<Map>> dataMap = new HashMap<String,List<Map>>();
					
					try {
						//根据年月作为index分组
						for(ESearchData es : list){
							String index = es.getIndex();
							
							List<Map> loglist = dataMap.get(index);
							if(loglist==null){
								loglist = new ArrayList<Map>();
								dataMap.put(index, loglist);
							}
							loglist.add(BeanUtils.bean2Map(new HashMap(), es));
							
						}
						
						for(String key : dataMap.keySet()){
							List<Map> logList = dataMap.get(key); 
							ESearchFactory.getSearchInst().insertBatchMap(key,"inf_comm_client_calllog",logList,"log_id");
						}
						System.out.println(zteRequest.toString()+" thread done!");
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return new ZteResponse();
				}
			});
			System.out.println("Thread"+i+":"+taskThreadPool.toString()+"start!");
			ThreadPoolFactory.orderExecute(taskThreadPool);
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void logInfoIndexByIds(List<String> idList) throws Exception {
		if(idList==null||idList.size()==0)CommonTools.addBusiError(new ZteError("argserror", "日志id集合不能为空!"));
		String sql = SF.esearchSql("LOGS_LIST");
		String conditionStr = " where (t.SOURCE_FROM is null OR t.SOURCE_FROM is not null) and t.LOG_ID in (	";
		for(String s : idList){
			conditionStr += "'"+s+"',";
		}
		conditionStr = conditionStr.substring(0,conditionStr.length()-1);
		sql = sql+conditionStr+")";
		
		List<ESearchData> esList = this.baseDaoSupport.queryForList(sql,new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ESearchData esData = new ESearchData();
				
				Date reqTime =  rs.getTimestamp("REQ_TIME");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				String ds = sdf.format(reqTime);//根据日期作为索引
				String index = "inf_comm_client_calllog_idx"+ds+"";
				esData.setIndex(index);
				esData.setType("inf_comm_client_calllog");
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSSZ");
				String busiTime = sdf1.format(reqTime);
				esData.setBusi_time(busiTime);
				
				String creatTime = sdf1.format(new Date());
				esData.setCreate_time(creatTime);
				
				esData.setLog_id((String) rs.getString("LOG_ID"));

				esData.setOperation_code(rs.getString("OP_CODE"));
				
				String inBlobStr ="";
				Object inBlob = rs.getBlob("REQ_XML");
				if(inBlob !=null){
				    Class clazz = inBlob.getClass();
				    Method method;
					try {
						  method = clazz.getMethod("getBinaryStream", new Class[]{});
						  InputStream is = (InputStream)method.invoke(inBlob, new Object[]{});
						  InputStreamReader reader = new InputStreamReader(is,"GBK");
						  BufferedReader br = new BufferedReader(reader);
						  String s = br.readLine();
						  StringBuffer sb = new StringBuffer();
						  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
							sb.append(s);
						    s = br.readLine();
						   }
						  inBlobStr = sb.toString();
							
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				
				String outBlobStr ="";
				Object outBlob = rs.getBlob("RSP_XML");
				if(outBlob !=null){
				    Class clazz = outBlob.getClass();
				    Method method;
					try {
						  method = clazz.getMethod("getBinaryStream", new Class[]{});
						  InputStream is = (InputStream)method.invoke(outBlob, new Object[]{});
						  InputStreamReader reader = new InputStreamReader(is,"GBK");
						  BufferedReader br = new BufferedReader(reader);
						  String s = br.readLine();
						  StringBuffer sb = new StringBuffer();
						  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
							sb.append(s);
						    s = br.readLine();
						   }
						  outBlobStr = sb.toString();
							
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				esData.setIn_param(inBlobStr);
				esData.setOut_param(outBlobStr);
				
				return esData;
			}
		});
		
		Map<String,List> dataMaps = new HashMap<String,List>();
		for(ESearchData es : esList){
			String index = es.getIndex();
			
			List<Map> logList = dataMaps.get(index);
			if(logList==null){
				logList = new ArrayList<Map>();
				dataMaps.put(index, logList);
			}
			logList.add(BeanUtils.bean2Map(new HashMap(), es));
		}
		
		for(String key : dataMaps.keySet() ){
			List<Map> logList = dataMaps.get(key);
			ESearchFactory.getSearchInst().insertBatchMap(key, "inf_comm_client_calllog", logList, "log_id");
		}
		
	}

	@Override
	public List<ESearchData> search(String indexName,String typeName,Map<String, String> qsParams,
			Map<String, String> boolParams) throws Exception {
		System.out.println("调用EsearchManager.search方法：index:"+indexName+";type:"+typeName+";qsParams:"+qsParams);
		return ESearchFactory.getSearchInst().search(indexName,typeName,qsParams, boolParams);
	}

	@Override
	public String update(ESearchData esearch) throws Exception {
		if(esearch==null)CommonTools.addError(new ZteError("-1", "esearchData不能为空!"));
		return update(esearch,false,false);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String update(ESearchData esearch, boolean updateNull,boolean updateDelay)
			throws Exception {
		if(esearch==null)CommonTools.addError(new ZteError("-1", "esearchData不能为空!"));
		String indexName = esearch.getIndex();
		String typeName = esearch.getType();
		String id = esearch.getLog_id();
		
		System.out.println("调用EsearchManager.update方法：esData:"+esearch+
				";updateNull:"+updateNull+";updateDelay:"+updateDelay);
		
		if(Boolean.TRUE == updateDelay){
			Map esMap = new HashMap();
//			BeanUtils.bean2Map(esMap, esearch);
			esMap.put("ID", "ES"+System.currentTimeMillis());
			esMap.put("LOG_ID", esearch.getLog_id());
			esMap.put("INDEXNAME", esearch.getIndex());
			esMap.put("TYPENAME", esearch.getType());
			esMap.put("OPERATION_CODE", esearch.getOperation_code());
			esMap.put("OPERATION_CODE_ID", esearch.getOperation_code_id());
			esMap.put("OBJ_ID", esearch.getObj_id());
			esMap.put("BUSI_TIME", esearch.getBusi_time());
			esMap.put("CREATE_TIME", esearch.getCreate_time());
			esMap.put("KEYWORD_ID", esearch.getKeyword_id());
//			esMap.put("KEYWORD_VALUE", esearch.getKeyword_value());
//			esMap.put("CLASS_ID", esearch.getClass_id());
//			esMap.put("CLASS_NAME", esearch.getClass_name());
			esMap.put("KEYWORD_NAME", esearch.getKeyword_name());
			esMap.put("CLASS_ID", esearch.getClass_id());
			esMap.put("CLASS_VALUE", esearch.getClass_value());
			esMap.put("UPDATE_NULL", String.valueOf(updateNull));
			esMap.put("IS_UPDATED", "0");
			this.baseDaoSupport.insert("ES_ESEARCH_DELAYUPDATE_INFO", esMap);
			
			System.out.println("插入延迟更新纪录："+esMap);
			return id;
		}
		
		if((StringUtils.isEmpty(indexName)||StringUtils.isEmpty(typeName))&&StringUtils.isNotEmpty(id)){
			Map<String,String> qsParams = new HashMap<String,String>();
			qsParams.put("+log_id:\""+id+"\"", "");
			List<ESearchData> esList = search("", "", qsParams, null);
			if(esList==null||esList.size()==0)CommonTools.addError(new ZteError("-1", "没找到对应的报文纪录!log_id:"+id));
			ESearchData esData = esList.get(0);
			indexName = esData.getIndex();
			typeName = esData.getType();
			esearch.setIndex(indexName);
			esearch.setType(typeName);
		}else{
			CommonTools.addError(new ZteError("-1", "主键log_id不能为空!"));
		}
		
		validate(esearch);
		
		Map map = esToMap(esearch,updateNull);
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_esearch_write = cacheUtil.getConfigInfo(EsearchValues.IS_ESEARCH_WRITE);
		
		if(is_esearch_write.equals("0")){
			System.out.println("esearch写入开关为N，报文未能更新!log_id:"+id);
		}else{
			ESearchFactory.getSearchInst().updateMap(indexName, typeName, map, id);
		}
		return id;
	}

	@Override
	public ESearchData get(ESearchData esearch) throws Exception {
		if(esearch==null)CommonTools.addError(new ZteError("-1", "esearchData不能为空!"));
		String indexName = esearch.getIndex();
		String typeName = esearch.getType();
		String id = esearch.getLog_id();
		
		System.out.println("调用EsearchManager.get方法：esData:"+esearch);
		
		if((StringUtils.isEmpty(indexName)||StringUtils.isEmpty(typeName))&&StringUtils.isNotEmpty(id)){
			Map<String,String> qsParams = new HashMap<String,String>();
			qsParams.put("+log_id:\""+id+"\"", "");
			List<ESearchData> esList = search("", "", qsParams, null);
			if(esList==null||esList.size()==0)CommonTools.addError(new ZteError("-1", "没找到对应的报文纪录!"));
			ESearchData esData = esList.get(0);
			indexName = esData.getIndex();
			typeName = esData.getType();
			esearch.setIndex(indexName);
			esearch.setType(typeName);
		}else{
			CommonTools.addError(new ZteError("-1", "主键id不能为空!"));
		}
		
		validate(esearch);
		
		return ESearchFactory.getSearchInst().get(indexName, typeName, id);
	}

	private void validate(ESearchData esearch){
		if(StringUtils.isEmpty(esearch.getIndex())||StringUtils.isEmpty(esearch.getType())||StringUtils.isEmpty(esearch.getLog_id()))
			CommonTools.addError(new ZteError("-1", "索引名称、类型、id不能为空!"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map esToMap(ESearchData esearch, boolean updateNull) throws Exception{
		Map paramMap = new HashMap();
		BeanUtils.copyProperties(paramMap, esearch);
		
		Map map = new HashMap();
		if(Boolean.FALSE == updateNull){
			Set<String> keyset = paramMap.keySet();
			for(String key : keyset){
				Object value = paramMap.get(key);
				if(value==null||(value instanceof String && StringUtils.isEmpty((String)value)))continue;
				map.put(key, value);
			}
		}
		
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List qryDelayUpdateInfo() throws Exception {
		String sql = SF.esearchSql("DELAY_UPDATE_LIST");
		List<Map> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	@Override
	public void completeDelayUpdate(Map fields,Map where) throws Exception {
		this.baseDaoSupport.update("ES_ESEARCH_DELAYUPDATE_INFO", fields, where);
	}
	
	

}
