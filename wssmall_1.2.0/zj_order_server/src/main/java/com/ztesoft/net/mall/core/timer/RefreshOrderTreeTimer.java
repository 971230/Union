package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.common.util.ListUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.service.IOrdArchiveManager;

public class RefreshOrderTreeTimer {
	private static Logger logger = Logger.getLogger(RefreshOrderTreeTimer.class);
	@Resource
	private IOrdArchiveManager ordArchiveManager;
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		try{
			List<Map<String,String>> list = ordArchiveManager.getRefreshOrders();
			if(!ListUtil.isEmpty(list)){
				for(Map<String,String> order_ids : list){
					String order_id = order_ids.get("order_id");
					logger.info("-----刷新订单："+order_id+"-------");
					CommonDataFactory.getInstance().updateOrderTree(order_id);
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
							new String[]{AttrConsts.IS_REFRESH}, new String[]{EcsOrderConsts.NO_DEFAULT_VALUE});
				}
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
