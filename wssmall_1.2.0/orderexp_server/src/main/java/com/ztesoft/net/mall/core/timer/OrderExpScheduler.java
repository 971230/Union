package com.ztesoft.net.mall.core.timer;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.net.model.EsearchExpInst;
import com.ztesoft.net.param.inner.ExpInstProcessedInner;
import com.ztesoft.net.service.IOrderExpManager;

public class OrderExpScheduler {
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private IOrderExpManager orderExpManager;
	
	private static final Object _KEY = new Object();
	
	public void orderExpMarkProcessed() {
		//ip验证处理 add by wui所有定时任务都需要加上限制条件
  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"execute"))
  			return ;
		//每次最多多少条
		int maxRecords = 1000;
		long start = System.currentTimeMillis();
		logger.info("[timer]OrderExpScheduler.orderExpMarkProcessed    executing......");
		logger.info("[timer]OrderExpScheduler.orderExpMarkProcessed    executing......");
		synchronized (_KEY) {
			orderExpManager.orderExpMarkProcessedTimerExc(maxRecords);
		}
		long end = System.currentTimeMillis();
		logger.info("[timer]OrderExpScheduler.orderExpMarkProcessed executed!   Consume time "+(end-start)+" ms");
		logger.info("[timer]OrderExpScheduler.orderExpMarkProcessed executed!   Consume time "+(end-start)+" ms");
	}
	
	public void orderExpProcess() {
		//ip验证处理 add by wui所有定时任务都需要加上限制条件
  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"execute"))
  			return ;
		logger.info("[timer]OrderExpScheduler.orderExpProcess    executing......");
		synchronized (_KEY) {
			List<EsearchExpInst> instList = orderExpManager.getExpInstByProcessModeAList();
			for(EsearchExpInst inst : instList) {
				ExpInstProcessedInner processInner = new ExpInstProcessedInner();
				processInner.setExp_inst_id(inst.getExcp_inst_id());
				processInner.setDeal_result("已处理 OrderExpScheduler.orderExpProcess");
				processInner.setStaff_no("-1");
				orderExpManager.expInstProcess(processInner);
				
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(processInner) {
 					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
 						try {
 							ExpInstProcessedInner inner =(ExpInstProcessedInner)(zteRequest);
 							orderExpManager.expInstProcess(inner);
 						} catch (Exception e) {
 							e.printStackTrace();
 						}
 						return new ZteResponse();
 					}
 				});
 				ThreadPoolFactory.orderExecute(taskThreadPool); //异步单线程执行
			}
		}
	}

	public IOrderExpManager getOrderExpManager() {
		return orderExpManager;
	}

	public void setOrderExpManager(IOrderExpManager orderExpManager) {
		this.orderExpManager = orderExpManager;
	}
}
