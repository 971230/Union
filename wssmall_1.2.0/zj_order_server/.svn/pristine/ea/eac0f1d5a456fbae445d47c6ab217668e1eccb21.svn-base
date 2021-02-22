package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.IOrdIntentManager;

public class SmsMidUpdateTimer {
	protected final Logger logger = Logger.getLogger(getClass());
	@Resource
	private IOrdIntentManager ordIntentManager;

	@SuppressWarnings("unchecked")
	public void run() throws Exception {
		
		
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
			return;
		}
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		@SuppressWarnings("rawtypes")
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

	
		//如果发送次数大于等于3，移动到历史表
		
		String querySql = "select a.* from es_order_mid_sms a where a.source_from = 'ECS'and a.send_num > 3";
		List<?> list_sms = baseDaoSupport.queryForListNoSourceFrom(querySql);
		if (list_sms != null && list_sms.size() > 0) {
			for (int i = 0; i < list_sms.size(); i++) {
				Map<String, String> map = (Map<String, String>) list_sms.get(i);
				String sms_id  =  map.get("sms_id");
				String delSql = "delete from es_order_mid_sms a where a.sms_id = '"+sms_id +"'";
				baseDaoSupport.execute(delSql);
				baseDaoSupport.insertByMap("es_order_his_sms", map);
			}
		}
		
		// 对中间表进行扫描
		String query_sql = cacheUtil.getConfigInfo("handle_sms_send_update_byTimer");
		List<?> listSms = baseDaoSupport.queryForListNoSourceFrom(query_sql);
		//logger.info("SmsMidUpdateTimer begin run：短信异步定时任务开始处理");

		if (listSms != null && listSms.size() > 0) {
			for (int i = 0; i < listSms.size(); i++) {
				Map<String, String> map = (Map<String, String>) listSms.get(i);
				ordIntentManager.smsSendTimer(map);
			}
		}
	}
}
