package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.IOrdIntentManager;

public class IntentMidUpdateTimer {
	 protected final Logger logger = Logger.getLogger(getClass());
	@Resource
	private IOrdIntentManager ordIntentManager;
	
	public void run() throws Exception {
		
		if (!CheckTimerServer
				.isMatchServer(this.getClass().getName(), "run")) {
			return;
		}
		String order_id="";
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		@SuppressWarnings("rawtypes")
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
 		String query_sql=cacheUtil.getConfigInfo("handle_intent_min_update_byTimer");
		List list = baseDaoSupport.queryForListNoSourceFrom(query_sql);
		
		logger.info("IntentMidUpdateTimer begin run：总部蜂行动定时任务开始处理");
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				@SuppressWarnings("unchecked")
				Map<String,String> map= (Map<String, String>) list.get(i);
				order_id= map.get("order_id");
		        ordIntentManager.beeActionHandle(order_id);
			}
		}
	}
}
