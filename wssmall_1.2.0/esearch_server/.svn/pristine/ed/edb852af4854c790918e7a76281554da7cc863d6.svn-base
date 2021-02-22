package com.ztesoft.net.search.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.timer.CheckTimerServer;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.search.service.IESearchManager;

public class EsearchUpdateTimer {

	private IESearchManager esearchManager;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(){
		
		//addby wui废弃，走界面方式流转
 		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"update"))
  			return ;
 		
		System.out.println("esearch定时更新任务执行------------------");
		try {
			esearchManager = SpringContextHolder.getBean("esearchManager");
			List<Map> list = esearchManager.qryDelayUpdateInfo();
			if(list!=null&&list.size()>0){
				for(Map map : list){
					System.out.println("定时任务更新纪录id:"+map.get("LOG_ID"));
					
					String id = (String) map.get("ID");
					Map where = new HashMap();
					where.put("ID", id);
					Map field = new HashMap();
					field.put("IS_UPDATED", "1");
					
					try{
						String updateNull = (String) map.get("UPDATE_NULL");
						ESearchData esData = mapToMap(map);
						String isUpdated = (String) map.get("IS_UPDATED");
						if("0".equals(isUpdated)){
							esearchManager.update(esData,Boolean.valueOf(updateNull),false);
						}else if("-1".equals(isUpdated)){
							esearchManager.updateClassByKeyword(esData, false);
						}
						esearchManager.completeDelayUpdate(field,where);
					}catch(Exception e){
						field.put("IS_UPDATED", "2");
						esearchManager.completeDelayUpdate(field,where);
						System.out.println("定时任务更新纪录id:"+map.get("LOG_ID")+" 出错!");
						e.printStackTrace();
					}
				}
			}
			System.out.println("esearch定时更新任务结束----------------更新总数为:"+(list==null?"0":list.size()));
		} catch (Exception e) {
			System.out.println("eserch定时更新任务出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private ESearchData mapToMap(Map map) {
		ESearchData esData = new ESearchData();
		esData.setIndex((String) map.get("INDEXNAME"));
		esData.setType((String) map.get("TYPENAME"));
		esData.setLog_id((String) map.get("LOG_ID"));
		esData.setOperation_code((String) map.get("OPERATION_CODE"));
		esData.setOperation_code_id((String) map.get("OPERATION_CODE_ID"));
		esData.setObj_id((String) map.get("OBJ_ID"));
		esData.setBusi_time((String) map.get("BUSI_TIME"));
		esData.setCreate_time((String) map.get("CREATE_TIME"));
		
		esData.setKeyword_id((String) map.get("KEYWORD_ID"));
		esData.setKeyword_value((String) map.get("KEYWORD_VALUE"));
		esData.setClass_id((String) map.get("CLASS_ID"));
		esData.setClass_name((String) map.get("CLASS_NAME"));
		
		return esData;
	}
	
}
