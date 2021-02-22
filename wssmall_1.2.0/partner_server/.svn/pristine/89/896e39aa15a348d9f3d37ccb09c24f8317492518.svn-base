package com.ztesoft.net.mall.core.timer;

import org.apache.log4j.Logger;

import com.ztesoft.net.mall.core.service.IPartnerManager;

public class PartnerExpScheduler {

	protected final Logger logger = Logger.getLogger(getClass());
	
	private IPartnerManager partnerManager;

	/**
	 * 分销商合约过期：短信提醒&冻结 定时任务一天执行一次
	 */
	public void partnerExpAndFreeze() {
		//修改=== mochunrun 2014-4-21
		/*IJobTaskService jobTaskService = SpringContextHolder.getBean("jobTaskService");
		JobTaskCheckedReq req = new JobTaskCheckedReq();
		req.setClassName(PartnerExpScheduler.class.getName());
		req.setMethod("partnerExpAndFreeze");
		JobTaskCheckedResp resp = jobTaskService.checkedJobTask(req);
		if(!resp.isCanRun())
			return ;*/
		
//		if (!CheckSchedulerServer.isMatchServer()) {
//			return;
//		}
		logger.info("分销商过期检查begin");
		partnerManager.partnerExpAndFreeze();
		logger.info("分销商过期检查end");
	}
	
	
	public IPartnerManager getPartnerManager() {
		return partnerManager;
	}

	public void setPartnerManager(IPartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}
	
	
	
	
	
}
