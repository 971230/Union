package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.net.mall.core.service.IGoodsUsageManager;

/**
 * 库存告警定时任务
 * @author chen.zewen
 *
 */
public class CardStockScheduler {
	
	protected final Logger logger = Logger.getLogger(getClass());
  
	private IGoodsUsageManager goodsUsageManager;

	@SuppressWarnings("unchecked")
	public void stockCheckScheduler() {
		//修改=== mochunrun 2014-4-21
		/*IJobTaskService jobTaskService = SpringContextHolder.getBean("jobTaskService");
		JobTaskCheckedReq req = new JobTaskCheckedReq();
		req.setClassName(CardStockScheduler.class.getName());
		req.setMethod("stockCheckScheduler");
		JobTaskCheckedResp resp = jobTaskService.checkedJobTask(req);
		if(!resp.isCanRun())
			return ;*/
				
		/*if (!CheckSchedulerServer.isMatchServer()) {
			return;
		}*/
		logger.info("====检查库存begin====");
		List<Map> cardStockList = goodsUsageManager.getCardStock();
		goodsUsageManager.stockWarning(cardStockList);
		logger.info("====检查库存end====");
	}
	
	
	public IGoodsUsageManager getGoodsUsageManager() {
		return goodsUsageManager;
	}

	public void setGoodsUsageManager(IGoodsUsageManager goodsUsageManager) {
		this.goodsUsageManager = goodsUsageManager;
	}
	
	
	
}
