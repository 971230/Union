package com.ztesoft.net.mall.cmp;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AlarmTask;
import com.ztesoft.net.mall.core.model.AlarmTmplDate;
import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 对账工具类
 * @author hu.yi
 * @date 2013.12.05
 */
public class CheckBillUtils extends BaseSupport{

	/**
	 * 解析任务设置条件
	 * @param alarmTask
	 * @return
	 */
	public static Map<String, Object> parseCondition(AlarmTask alarmTask){
		Map<String, Object> resMap = new HashMap<String, Object>();
		boolean res = true;
		int bill_error_num_min = 0;
		int bill_error_num_max = 0;
		int bill_error_money_min = 0;
		int bill_error_money_max = 0;
		
		if(alarmTask == null){
			res = false;
		}else{
			/**
			    [{e_name:'alarm_amount',c_name:'对账异常数量',min_value:'50',max_value:'800',c_value:'',"checked":'yes'},
				{e_name:'alarm_money',c_name:'对账异常金额',min_value:'50',max_value:'1000',c_value:'',"checked":'yes'},
				{e_name:'text_pattern',c_name:'对账文本格式',min_value:'',max_value:'',c_value:'',"checked":'yes'}]
			 */
			//解析数据，得出报警金额及账单的阀值
			String condition = alarmTask.getCondition_desc();
			
			JSONArray json = JSONArray.fromObject(condition);
			Iterator it =  json.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = (Map<String, Object>) it.next();
				String e_name = (String) map.get("e_name");
				String checked = (String) map.get("checked");
				
				if(Consts.ALARM_CHECKED.equals(checked)){
					if(Consts.ALARM_AMOUNT.equals(e_name)){
						bill_error_num_min = Integer.valueOf((String) map.get("min_value"));
						bill_error_num_max = Integer.valueOf((String) map.get("max_value"));
					}else if(Consts.ALARM_MONEY.equals(e_name)){
						bill_error_money_min = Integer.valueOf((String) map.get("min_value"));
						bill_error_money_max = Integer.valueOf((String) map.get("max_value"));
					}
				}
			}
 		}
		
		resMap.put("bill_error_num_min", bill_error_num_min);
		resMap.put("bill_error_num_max", bill_error_num_max);
		resMap.put("bill_error_money_min", bill_error_money_min);
		resMap.put("bill_error_money_max", bill_error_money_max);
		resMap.put("res", res);
		
		
		return resMap;
	}
	
	
	/**
	 * 解析短信内容
	 * @param alarmTask
	 * @return
	 */
	public static AlarmTask parseMsgTemp(AlarmTask alarmTask,AlarmTmplDate alarmTmplDate, Map<String, Object> alarmMap){
		
		/**
		 * 您好的对账文本格式为{对账文本格式},异常对账金额为{异常对账金额},
		 * 异常对账数量为{异常对账数量},对账时间为{对账时间}
		 */
		
		/**
		    [{e_name:'alarm_amount',c_name:'对账异常数量'},
			{e_name:'alarm_money',c_name:'对账异常金额'},
			{e_name:'text_pattern',c_name:'对账文本格式'},
			{e_name:'reconciliation_time',c_name:'对账时间'}]
		 */
		if(alarmTask != null){
			String ele = alarmTask.getElements_object();
			JSONArray json = JSONArray.fromObject(ele);
			String content = alarmTmplDate.getTemp_date();
			
			if(!StringUtil.isEmpty(content)){
				Iterator it = json.iterator();
				while (it.hasNext()) {
					Map<String, Object> map = (Map<String, Object>) it.next();
					String c_name = (String) map.get("c_name");
					String e_name = (String) map.get("e_name");
					String e_value = String.valueOf(alarmMap.get(e_name));
					
					if(content.indexOf(e_name) > -1){
						if(!"null".equals(e_value)){
							content = content.replace("{"+e_name+"}", e_value);
						}else{
							content = content.replace("{"+e_name+"}", "（无）");
						}
					}else{
						content = content.replace("{"+e_name+"}", "（无）");
					}
				}
				alarmTask.setTask_content(content);
			}else{
				alarmTask.setTask_content("");
			}
		}
		
		return alarmTask;
	}
	
}
