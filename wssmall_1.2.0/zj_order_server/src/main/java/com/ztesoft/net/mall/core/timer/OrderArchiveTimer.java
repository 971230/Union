package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IRuleService;
import zte.net.params.req.RuleExeLogDelReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.common.util.ListUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.service.IOrdArchiveManager;
import com.ztesoft.net.service.IOrdArchiveTacheManager;

import consts.ConstsCore;

public class OrderArchiveTimer {
	private static Logger logger = Logger.getLogger(OrderArchiveTimer.class);
	@Resource
	private IOrdArchiveManager ordArchiveManager;	
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;
	@Resource
	private IRuleService ruleService;
	
	/**
	 * 两个线程池，分别执行逻辑归档、数据归档
	 * 先有逻辑归档，再有数据归档
	 */
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		logger.info("OrderArchiveTimer===============start");
		try {
			CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String thread_num_logis = cacheUtil.getConfigInfo("ORDER_ARCHIVE_TIMER_THREAD_NUM");
			String thread_num_data = cacheUtil.getConfigInfo("ORDER_DATA_ARCHIVE_TIMER_THREAD_NUM");
			if (StringUtils.isEmpty(thread_num_logis)) thread_num_logis = "5"; // 默认线程池大小为5
			if (StringUtils.isEmpty(thread_num_data)) thread_num_data = "5"; // 默认线程池大小为5
			Integer num_logis = Integer.valueOf(thread_num_logis);
			Integer num_data = Integer.valueOf(thread_num_data);
			if (new Integer("50").compareTo(num_logis) < 0) num_logis = 50; // 最大控制在50
			if (new Integer("15").compareTo(num_data) < 0) num_data = 15; // 最大控制在15
			
	        ExecutorService service = Executors.newFixedThreadPool(2);

			// 逻辑归档
			if (new Integer("0").compareTo(num_logis) < 0) {
				final List<Map<String,String>> list_logis = ordArchiveManager.ordArchiveList();
				final Integer thread_num_l = num_logis;
		        Runnable runLogis = new Runnable() {
					@Override
					public void run() {
						logisticArchive(list_logis, thread_num_l);
					}
		        };
				service.execute(runLogis);
			}
			
			// 数据归档
			if (new Integer("0").compareTo(num_data) < 0) {
				final List<Map<String,String>> list_data = ordArchiveManager.ordDataArchiveList();
				final Integer thread_num_d = num_data;
		        Runnable runData = new Runnable() {
					@Override
					public void run() {
						dataArchive(list_data, thread_num_d);
					}
		        };
				service.execute(runData);
			}
			
	        // 关闭启动线程
	        service.shutdown();
	        // 等待子线程结束，再继续执行下面的代码
	        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch(Exception e) {
			logger.info("OrderArchiveTimer===============exception");
			throw new RuntimeException(e.getMessage());
		}
		logger.info("OrderArchiveTimer===============end");
	}
	
	/**
	 * 逻辑归档
	 * @param list
	 */
	private void logisticArchive(List<Map<String,String>> list, Integer thread_num) {
		logger.info("logisticArchive===============start");
		try {
			// 创建一个固定大小的线程池
	        ExecutorService service = Executors.newFixedThreadPool(thread_num);
			if(!ListUtil.isEmpty(list)){
				for(Map<String,String> order_ids : list){
					final Map<String,String> orders = order_ids;
					logger.info("logisticArchive====queue==add===order_id:" + orders.get("order_id") + ";archive_type:" + orders.get("archive_type"));
					Runnable run = new Runnable() {
						@Override
						public void run() {
							try {
								logger.info("logisticArchive====start===order_id:" + orders.get("order_id") + ";archive_type:" + orders.get("archive_type"));
								execLogisticArchive(orders);
								logger.info("logisticArchive====end===order_id:" + orders.get("order_id") + ";archive_type:" + orders.get("archive_type"));
							} catch (Exception e) {
								logger.info("logisticArchive====exception===order_id:" + orders.get("order_id") + ";archive_type:" + orders.get("archive_type"));
								e.printStackTrace();
							}
						}
					};
					service.execute(run);
				}
			}
			
	        // 关闭启动线程
	        service.shutdown();
	        // 等待子线程结束，再继续执行下面的代码
	        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.info("logisticArchive===============exception");
			e.printStackTrace();
		}
		logger.info("logisticArchive===============end");
	}
	
	/**
	 * 数据归档
	 * @param list
	 */
	private void dataArchive(List<Map<String,String>> list, Integer thread_num) {
		logger.info("dataArchive===============start");
		try {
			// 创建一个固定大小的线程池
	        ExecutorService service = Executors.newFixedThreadPool(thread_num);
			if(!ListUtil.isEmpty(list)){
				for(Map<String,String> order_ids : list){
					final Map<String,String> orders = order_ids;
					logger.info("dataArchive====queue==add===order_id:" + orders.get("order_id"));
					Runnable run = new Runnable() {
						@Override
						public void run() {
							try {
								logger.info("dataArchive====start===order_id:" + orders.get("order_id"));
								execDataArchive(orders);
								logger.info("dataArchive====end===order_id:" + orders.get("order_id"));
							} catch (Exception e) {
								logger.info("dataArchive====exception===order_id:" + orders.get("order_id"));
								e.printStackTrace();
							}
						}
					};
					service.execute(run);
				}
			}
			
	        // 关闭启动线程
	        service.shutdown();
	        // 等待子线程结束，再继续执行下面的代码
	        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.info("dataArchive===============exception");
			e.printStackTrace();
		}
		logger.info("dataArchive===============end");
	}
	
	private void execLogisticArchive(Map<String,String> order_ids) {
		String order_id = order_ids.get("order_id");
		String order_if_cancel = order_ids.get("order_if_cancel");
		//归档类型：0:待归档、1:回单归档、2:未回单归档(没有回单环节)、3:作废单归档、4:积压归档
		String archive_type = order_ids.get("archive_type");
		
		//保存订单归档类型
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (orderTree == null) {
			ordArchiveManager.insertArchiveFailLog(order_id , "订单不存在,或者获取订单数失败!");
			return;
		}
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setArchive_type(archive_type);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		
		if(StringUtils.equals(order_if_cancel, "1")){//order_if_cancel 1:作废单直接归档
			try{
				ordArchiveTacheManager.ordLogisArchive(order_id);
			}catch(Exception e){
				ordArchiveManager.insertArchiveFailLog(order_id , e.getMessage());
			}
		}else if(StringUtils.equals(archive_type, EcsOrderConsts.ARCHIVE_TYPE_4)) {//archive_type 4:积压单归档
			try{
				ordArchiveTacheManager.ordArchive(order_id); // 积压单只做数据归档,不逻辑归档
			}catch(Exception e){
				ordArchiveManager.insertArchiveFailLog(order_id , e.getMessage());
			}
		}else{
			orderTree =new OrderTreeBusiRequest();// CommonDataFactory.getInstance().getOrderTree(order_id);
			orderTree.setOrder_id(order_id);
			orderTree.setCol3(EcsOrderConsts.TRACE_TRIGGER_INF);//加一个标示表示从区分从接口、页面来源
			orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderTree.store();
			//删除规则执行日志
			OrderExtBusiRequest  oeb = orderTree.getOrderExtBusiRequest();
			String rule_id = ZjEcsOrderConsts.ORDER_DATA_ARCHIVE_RULE_AOP;
			//区分是否aop调用不同的归档规则
			if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(oeb.getIs_aop()) 
					|| StringUtils.isEmpty(oeb.getIs_aop())){
				rule_id = ZjEcsOrderConsts.ORDER_ARCHIVE_RULE;
			}else if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(oeb.getIs_aop())){
				rule_id = ZjEcsOrderConsts.ORDER_ARCHIVE_RULE_BSSKL;
			}else{
				rule_id = ZjEcsOrderConsts.ORDER_DATA_ARCHIVE_RULE_AOP;
			}
			
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			RuleExeLogDelReq dreq = new RuleExeLogDelReq();
			dreq.setPlan_id(new String[]{ZjEcsOrderConsts.ORDER_DATA_ARCHIVE_PLAN});
			dreq.setRule_id(rule_id);
			dreq.setObj_id(order_id);
			ruleService.delRuleExeLog(dreq);
			logger.info("删除归档执行日志"+order_id + "," + order_if_cancel);
			//执行归档校验规则
			RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
			ruleTreeExeReq.setCheckCurrRelyOnRule(true);
			ruleTreeExeReq.setCheckAllRelyOnRule(true);
			ruleTreeExeReq.setPlan_id(ZjEcsOrderConsts.ORDER_DATA_ARCHIVE_PLAN);
			//区分是否aop调用不同的归档规则
			ruleTreeExeReq.setRule_id(rule_id);
			ruleTreeExeReq.setFact(fact);
			try{
				RuleTreeExeResp presp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
				//规则失败插入归档失败表
				if(null != presp && !StringUtils.equals(presp.getError_code(), ConstsCore.ERROR_SUCC)){
					ordArchiveManager.insertArchiveFailLog(order_id , presp.getError_msg());
				}
				logger.info("归档执行");
			}catch(Exception e){
				ordArchiveManager.insertArchiveFailLog(order_id , e.getMessage());
			}
		}
	}

	private void execDataArchive(Map<String,String> order_ids) {
		String order_id = order_ids.get("order_id");
		try{
			ordArchiveTacheManager.ordArchive(order_id);
		}catch(Exception e){
			ordArchiveManager.insertArchiveFailLog(order_id , "数据归档失败："+e.getMessage());
		}
	}
}
