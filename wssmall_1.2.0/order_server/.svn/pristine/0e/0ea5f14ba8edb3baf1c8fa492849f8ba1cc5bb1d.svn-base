package com.ztesoft.net.mall.core.timer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import services.CardInf;

/**
 * 无效卡释放
 * @author wui
 *
 */
public class CardReleaseScheduler {

	protected final Logger logger = Logger.getLogger(getClass());
	

    @Resource
    private CardInf cardServ;

	//无效卡释放
	public void cardReleaseScheduler() {
		//修改=== mochunrun 2014-4-21
		/*IJobTaskService jobTaskService = SpringContextHolder.getBean("jobTaskService");
		JobTaskCheckedReq req = new JobTaskCheckedReq();
		req.setClassName(CardReleaseScheduler.class.getName());
		req.setMethod("cardReleaseScheduler");
		JobTaskCheckedResp resp = jobTaskService.checkedJobTask(req);
		if(!resp.isCanRun())
			return ;*/
		
		/*if (!CheckSchedulerServer.isMatchServer()) {
			return;
		}*/
        cardServ.resetFailCardByOrderId();
	}


	
	
}
