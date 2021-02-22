package com.ztesoft.net.mall.core.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import rule.params.coqueue.req.CoQueueRuleReq;
import zte.net.iservice.IOrderStandardizing;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.ICoQueueManager;

/**
 * 订单标准化定时任务
 * @author suns
 *
 */
public class NewOrderStandardizingTimer {
	private static Logger logger = Logger.getLogger(NewOrderStandardizingTimer.class);
	@Resource
	private ICoQueueManager coQueueManager;
	@Resource
	private IOrderStandardizing orderStandardizing;
	
	private static final int maxNum = 500;  //每次扫描500条
	private static final Object _KEY = new Object();
	private int flag =1;
	@SuppressWarnings("static-access")
	public void standardize() {
		
//		//addby wui废弃，走界面方式流转
//		logger.info("NewOrderStandardizingTimer处理类："+this.getClass().getName()+":"+server_ip+":"+server_port+":");
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"standardize"))
		  	return ;
 		
// 		logger.info("NewOrderStandardizingTimer处理类1111111111111："+this.getClass().getName()+":"+server_ip+":"+server_port+":");
	    //获取订单标准化消息队列集
 		
 		//查询当前server主机环境，当前server为生产主机环境，则查询当前归属主机+未归属主机的订单信息；当前server为非生产主机环境，则查询当前归属主机
 		String local_env_status="";
 		String local_env_code="";
 		String local_env_type ="";
 		Map hostEnvMap = BeanUtils.getCurrHostEnv();
 		local_env_status = (String)hostEnvMap.get("env_status");
 		local_env_code = (String)hostEnvMap.get("env_code");
 		local_env_type = (String)hostEnvMap.get("env_type");
 		synchronized (_KEY) {
 			List<CoQueue> coQueueLst;
 			if("ecsord_ceshi".equals(local_env_type)||"ecsord2.0_ceshi".equals(local_env_type)){
 				coQueueLst = coQueueManager.getForJob("CO_STANDING_NEW", NewOrderStandardizingTimer.maxNum);
 			}else{
 				coQueueLst = coQueueManager.getForABGrayJob(new String[]{"CO_STANDING_NEW"}, NewOrderStandardizingTimer.maxNum,local_env_status,local_env_code,false); //修改脚本，生产环境查询es_abgray_ord_env_rel存在的记录，或者不存在的记录，否则非生产环境，则完全匹配。
 			}
 			CoQueueRuleReq coQueueRuleReq = null;
 			for (CoQueue coQueue : coQueueLst) {
 				coQueueRuleReq = new CoQueueRuleReq();
 				coQueueRuleReq.setObject_id(coQueue.getObject_id());
 				coQueueRuleReq.setCoQueue(coQueue);
 				logger.info("订单标准化Job,订单"+coQueue.getObject_id()+" 标准化环境为："+local_env_code+"  环境状态"+local_env_status);
 				Map params = new HashMap();
 				coQueueRuleReq.setParams(params);
 				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueueRuleReq) {
 					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
 						try {
 							CoQueueRuleReq coQueueRuleReq =(CoQueueRuleReq)(zteRequest);
 							OrderAddResp resp = new OrderAddResp();
 							Order order = new Order();
 							order.setOrder_id(coQueueRuleReq.getObject_id());
 							List<Order> orderList = new ArrayList<Order>();
 							orderList.add(order);
 							resp.setOrderList(orderList);
 							orderStandardizing.startOrderStandingPlan("CO_STANDING_NEW", resp);
 							coQueueManager.del(coQueueRuleReq.getCoQueue().getCo_id());
 							
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

	public ICoQueueManager getCoQueueManager() {
		return coQueueManager;
	}

	public void setCoQueueManager(ICoQueueManager coQueueManager) {
		this.coQueueManager = coQueueManager;
	}

	public IOrderStandardizing getOrderStandardizing() {
		return orderStandardizing;
	}

	public void setOrderStandardizing(IOrderStandardizing orderStandardizing) {
		this.orderStandardizing = orderStandardizing;
	}
	
}
