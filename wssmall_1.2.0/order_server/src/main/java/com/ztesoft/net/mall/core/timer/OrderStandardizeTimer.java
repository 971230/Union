package com.ztesoft.net.mall.core.timer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import params.ZteRequest;
import params.ZteResponse;
import rule.RuleInvoker;
import rule.params.coqueue.req.CoQueueRuleReq;
import utils.GlobalThreadLocalHolder;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.sqls.SF;

/**
 * 订单标准化定时任务
 * @author suns
 *
 */
public class OrderStandardizeTimer {

	@Resource
	private ICoQueueManager coQueueManager;
	
	private static final int maxNum = 500;  //每次扫描500条
	private static final Object _KEY = new Object();
	
	@SuppressWarnings("static-access")
	public void standardize() {
		
		//addby wui废弃，走界面方式流转
 		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"standardize"))
  			return ;
//////		
 		
	    //获取订单标准化消息队列集
 		synchronized (_KEY) {
 			
 			List<CoQueue> coQueueLst = coQueueManager.getForJob(Consts.SERVICE_CODE_CO_GUIJI, OrderStandardizeTimer.maxNum);
 			
 			//获取未发送的【WFS】和响应失败【XYSB】的（失败次数4次以下的）
 			try{
	 			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
	 			String sql = SF.baseSql("QUERY_CO_QUEUE_ALL") 
	 					+ " and service_code = ?"
	 					+ " and (status = ? and deal_num < 4)";
	 			sql += " order by co_id";
	 			List paramLst = new ArrayList();
	 			paramLst.add(Consts.SERVICE_CODE_CO_GUIJI_NEW);
	 			paramLst.add(Consts.CO_QUEUE_STATUS_XYSB);
	 			Page coPage = support.queryForPage(sql, 1, OrderStandardizeTimer.maxNum, CoQueue.class, paramLst.toArray());
	 			if(coPage !=null)
	 				coQueueLst.addAll(coPage.getResult());
 			}catch(Exception e){e.printStackTrace();}
 			
 			CoQueueRuleReq coQueueRuleReq = null;
 			for (CoQueue coQueue : coQueueLst) {
 				
 				coQueueRuleReq = new CoQueueRuleReq();
 				coQueueRuleReq.setCo_id(coQueue.getCo_id());
 				coQueueRuleReq.setService_code(coQueue.getService_code());
 				coQueueRuleReq.setSource_from(coQueue.getSource_from());
 				coQueueRuleReq.setObject_id(coQueue.getObject_id());
 				coQueueRuleReq.setCoQueue(coQueue);
 				if(Consts.CO_QUEUE_STATUS_WFS.equals(coQueue.getStatus()))
 					continue;//订单归集后直接标准化,过滤未发送订单
 				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueueRuleReq) {
 					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
 						try {
 							
 							//触发规则标准化规则
 							GlobalThreadLocalHolder.getInstance().clear();
 							zteRequest.setUserSessionId(GlobalThreadLocalHolder.getInstance().getUUID());
 							RuleInvoker.coQueue((CoQueueRuleReq)zteRequest);
 						} catch (Exception e) {
 							e.printStackTrace();
 						}
 						
 						return new ZteResponse();
 					}
 				});
 				
 				coQueueManager.lock(coQueue.getCo_id());  //锁单（不要下次再被扫到）
 				
 				ThreadPoolFactory.orderExecute(taskThreadPool); //异步单线程执行
 			}
		}
		
	}
}
