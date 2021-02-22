package com.ztesoft.net.mall.core.timer;

import javax.annotation.Resource;

import com.ztesoft.net.mall.core.service.IGProdCoQueueManager;

public class GProdOrderStandardizeTimer {
	
	@Resource
	private IGProdCoQueueManager gProdCoQueueManager;
	
	private static final int maxNum = 500;  //每次扫描500条
	private static final Object _KEY = new Object();
	
	@SuppressWarnings("static-access")
	public void standardize() {
		
// 		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"standardize"))
//  			return ;
////		
////		//获取订单标准化消息队列集
// 		synchronized (_KEY) {
// 			List<CoQueue> coQueueLst = gProdCoQueueManager.getForJob(Consts.SERVICE_CODE_CO_GUIJI, this.maxNum);
// 			CoQueueRuleReq coQueueRuleReq = null;
// 			for (CoQueue coQueue : coQueueLst) {
// 				
// 				coQueueRuleReq = new CoQueueRuleReq();
// 				coQueueRuleReq.setCo_id(coQueue.getCo_id());
// 				//coQueueRuleReq.setService_code(coQueue.getService_code());
// 				coQueueRuleReq.setSource_from(coQueue.getSource_from());
// 				coQueueRuleReq.setObject_id(coQueue.getObject_id());
// 				coQueueRuleReq.setCoQueue(coQueue);
// 				
// 				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueueRuleReq) {
// 					public ZteResponse execute(ZteRequest zteRequest) {
// 						try {
// 							
// 							//触发规则标准化规则
// 							GlobalThreadLocalHolder.getInstance().clear();
// 							zteRequest.setUserSessionId(GlobalThreadLocalHolder.getInstance().getUUID());
// 							RuleInvoker.coQueueGProd((CoQueueRuleReq)zteRequest);
// 						} catch (Exception e) {
// 							e.printStackTrace();
// 						}
// 						
// 						return new ZteResponse();
// 					}
// 				});
// 				
// 				gProdCoQueueManager.lock(coQueue.getCo_id());  //锁单（不要下次再被扫到）
// 				
// 				ThreadPoolFactory.orderExecute(taskThreadPool); //异步单线程执行
// 			}
//		}
	}
}
