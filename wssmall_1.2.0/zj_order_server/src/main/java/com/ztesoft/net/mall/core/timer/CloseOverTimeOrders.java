package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class CloseOverTimeOrders {
	private static Logger logger = Logger.getLogger(CloseOverTimeOrders.class);
	
	public void run(){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        String sql=cacheUtil.getConfigInfo("CloseOverTimeOrders");
        String select_sql=cacheUtil.getConfigInfo("NeedCloseOverTimeOrders");
        List orders_list = baseDaoSupport.queryForListNoSourceFrom(select_sql);
		baseDaoSupport.execute(sql, null);
		for (int i = 0; i < orders_list.size(); i++) {
			Map order_ids= (Map) orders_list.get(i);
			String order_id=(String) order_ids.get("order_id");
			CommonDataFactory.getInstance().updateOrderTree(order_id);
		}
	}
	   public static void main(String[] args) {
	       CloseOverTimeOrders a = new CloseOverTimeOrders();
	       a.run();
	   }      
}
