package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import params.ZteRequest;
import params.ZteResponse;
import rule.RuleInvoker;
import rule.params.coqueue.req.CoQueueRuleReq;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.service.ICoQueueManager;

/**
 * 品牌同步定时任务
 * @author suns
 *
 */
public class CoFenLeiTimer {

	@Resource
	private ICoQueueManager coQueueManager;
	
	private static final int maxNum = 500;  //每次扫描500条
	
	public void run() {
		
		//定时任务IP地址限制
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
		    return ;
		}
		
		//多线程开启
		ThreadPoolExecutor executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_CACHE, 500);
		
		try {
			
			//获取消息队列集
			CoQueueRuleReq coQueueRuleReq = null;
			List<CoQueue> coQueueLst = coQueueManager.getForJob(Consts.SERVICE_CODE_CO_FENLEI, CoFenLeiTimer.maxNum);
			
			for (CoQueue coQueue : coQueueLst) {
				
				final String co_id = coQueue.getCo_id();
				
				coQueueRuleReq = new CoQueueRuleReq();
				coQueueRuleReq.setCo_id(coQueue.getCo_id());
				coQueueRuleReq.setBatch_id(coQueue.getBatch_id());
				coQueueRuleReq.setService_code(coQueue.getService_code());
				coQueueRuleReq.setAction_code(coQueue.getAction_code());
				coQueueRuleReq.setSource_from(coQueue.getSource_from());
				coQueueRuleReq.setObject_id(coQueue.getObject_id());
				coQueueRuleReq.setCoQueue(coQueue);
				
				//并发控制
				if (!coQueueManager.haveLocked(co_id)) {
					
					coQueueManager.lock(co_id);  //上锁
					
					TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueueRuleReq) {
						
						@Override
						public ZteResponse execute(ZteRequest zteRequest) {
							
							try {
								//调用规则
								RuleInvoker.coQueue((CoQueueRuleReq)zteRequest);
								
							} catch (Exception e) {
								
								System.out.print("Exception  co_id = " + co_id);
								coQueueManager.unLock(co_id);  //出异常解锁
								e.printStackTrace();
							}
							
							return new ZteResponse();
							
						}
					});
					
					//放入任务池
					ThreadPoolFactory.submit(taskThreadPool, executor);  
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			//关闭
			ThreadPoolFactory.closeThreadPool(executor);
		}
	}
}
