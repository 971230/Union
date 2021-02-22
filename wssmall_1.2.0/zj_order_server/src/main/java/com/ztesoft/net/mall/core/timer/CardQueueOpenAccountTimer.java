package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.order.req.OrderQueueCardActionLogReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.PCWriteCardTools;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

/**
 * PC批量写卡开户处理定时任务
 * @author duan.shaochu
 *
 */
public class CardQueueOpenAccountTimer {
	/**
	 * queue_no 队列编码
	 * max_open_num 最大开户阀
	 * open_coeff 开户系数
	 * avail_card_mac_num 可用写卡机数量
	 * source_from 
	 * queue_switch 队列
	 */
	private static Logger logger = Logger.getLogger(CardQueueOpenAccountTimer.class);
	//写卡机开户写卡顺序按照订单成交顺序倒叙排序
	//查询所有队列
	private static String QUEUE_SELECT = "select c.queue_no,nvl(c.max_open_num,'0') "
			+ " max_open_num,nvl(c.open_coeff,'0') open_coeff,nvl(c.avail_card_mac_num,'0') avail_card_mac_num "
			+ " from ES_QUEUE_MANAGER c where c.source_from = ? and queue_switch = '0'";
	//查询订单已开户订单数(排除等待回退的)
	private static String OPENED_ORDER_SELECT = "select count(1) from es_queue_write_card d "
			+ " where d.queue_code = ? and d.open_account_status in ('1', '2') and d.source_from = ? and d.queue_status = '0'";
	//查询队列待开户订单
	private static String WAIT_OPEN_SELECT = "select order_id,queue_code,create_time from (select c.source_from,c.order_id,c.queue_code ,d.create_time from es_queue_write_card c"
			+ "  left join es_order d on d.order_id=c.order_id where c.queue_code = ? and c.source_from = ? and nvl(c.queue_status,'0') = '0' and nvl(c.open_account_status, '0') = '0' "
			+ " and exists (select 1 from es_queue_manager q where q.queue_no = c.queue_code and q.queue_switch = '0') and c.create_time <= sysdate - 1 / 24 / 60 "
			+ " order by d.create_time)  where source_from = ? and rownum < ? ";

	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		//增加定时任务读取各个队列的已开户订单数量，如果未超出开户系数*可用写卡机数量，并且未超出最大开户阀值，
		//则读取队列中最先进入的未开户、非异常状态、状态标识为正常的订单进行开户操作；
		//获取所有队列
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		List queueList = support.queryForList(QUEUE_SELECT, ManagerUtils.getSourceFrom());
		if(null != queueList && queueList.size() > 0){
			try{
				//线程数与队列数一致
				ExecutorService service = Executors.newFixedThreadPool(queueList.size());
				for(int i = 0; i < queueList.size(); i++){
					Map m = (Map)queueList.get(i);
					String queue_no = m.get("queue_no").toString();					
					//获取当前队列已开户订单数
					int openedNums = support.queryForInt(OPENED_ORDER_SELECT, queue_no,ManagerUtils.getSourceFrom());
					int max_open_num = Integer.parseInt(m.get("max_open_num").toString());
					int open_coeff = Integer.parseInt(m.get("open_coeff").toString());
					int avail_card_mac_num = Integer.parseInt(m.get("avail_card_mac_num").toString());
					
					//队列的已开户订单数量，未超出开户系数*可用写卡机数量，并且未超出最大开户阀值
					if(openedNums < (open_coeff * avail_card_mac_num)){
						//队列可以处理订单数
						int wait_deal_nums = 0;
						wait_deal_nums = (open_coeff * avail_card_mac_num) - openedNums + 1;
						final List waitOpenList = support.queryForList(WAIT_OPEN_SELECT, queue_no,ManagerUtils.getSourceFrom(),ManagerUtils.getSourceFrom(),wait_deal_nums);
						logger.info("CardQueueOpenAccountTimerCardQueueOpenAccountTimer openedNums["+openedNums+"],wait_deal_nums["+wait_deal_nums+"],waitOpenList["+waitOpenList.size()+"]");
						if(null != waitOpenList && waitOpenList.size() > 0){
							CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
							String thread_num_open = cacheUtil.getConfigInfo("QUEUE_OPEN_TIMER_THREAD_NUM");
							if (StringUtils.isEmpty(thread_num_open)) thread_num_open = "10"; // 默认线程池大小为10
							int thread_num = Integer.parseInt(thread_num_open);
							if(thread_num > 30){
								thread_num = 30;
							}
							if(thread_num > 0){
								final Integer thread_num_l = thread_num;
						        Runnable runOpen = new Runnable() {
									@Override
									public void run() {
										orderOpenAccount(waitOpenList, thread_num_l);
									}
						        };
								service.execute(runOpen);
							}
						}
					}
				}
				//关闭启动线程
				service.shutdown();
				//等待子线程结束，再继续执行下面的代码
				//service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			}catch(Exception e){
				logger.info("批量写卡开户处理订时任务执行失败" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 订单开户
	 * @param list
	 * @param thread_num
	 */
	private void orderOpenAccount(List list, Integer thread_num) {
		logger.info("orderOpenAccount===============start");
		try{
			ExecutorService service = Executors.newFixedThreadPool(thread_num);
			if(null != list && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					final Map<String,String> m = (Map<String,String>)list.get(i);
					Runnable run = new Runnable() {
						@Override
						public void run() {
							try{
								String order_id = m.get("order_id");
								String queue_code = m.get("queue_code");
								OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
								OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
								//集中写卡生产模式、开户环节、AOP通道
								if(StringUtils.equals(orderExt.getOrder_model(), EcsOrderConsts.OPER_MODE_XK) 
										&& StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_D)
										&& (StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP) 
												|| StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS)) ){
									//标记为开户中
									PCWriteCardTools.modifyOrderStatus(order_id, EcsOrderConsts.NO_DEFAULT_VALUE, EcsOrderConsts.QUEUE_ORDER_OPEN_1);
									TacheFact fact = new TacheFact();
									String rule_id = "";
									if(StringUtils.equals(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP, orderExt.getIs_aop())){
										rule_id = EcsOrderConsts.ORDER_MODEL_06_OPEN_RULE_ID_AOP_ZJ;
									}else if(StringUtils.equals(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS, orderExt.getIs_aop())){
										rule_id = EcsOrderConsts.ORDER_MODEL_06_OPEN_RULE_ID_BSSKL;
									}
									fact.setOrder_id(order_id);
									RuleTreeExeReq ruleTreeExeReq = new RuleTreeExeReq();
									ruleTreeExeReq.setCheckAllRelyOnRule(true);
									ruleTreeExeReq.setCheckCurrRelyOnRule(true);
									ruleTreeExeReq.setRule_id(rule_id);
									ruleTreeExeReq.setFact(fact);
									RuleTreeExeResp presp = CommonDataFactory.getInstance().exeRuleTree(ruleTreeExeReq);
									BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(presp);
									if(!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
										logger.info("订单["+order_id+"]执行开户规则失败");
										//开户失败次数计算
										PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_0);
										//标记为等待回退   标记异常时统一回退
										PCWriteCardTools.modifyOrderQueueStatus(order_id, EcsOrderConsts.IS_DEFAULT_VALUE);
									}else{
										//更新开户成功时间
										String open_time = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_ACCOUNT_TIME);
										PCWriteCardTools.modifyOpenOrWriteDate(order_id, EcsOrderConsts.NO_DEFAULT_VALUE, open_time);
										OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
										logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_02);
										logReq.setAction_desc("队列订单开户完成");
										logReq.setOrder_id(order_id);
										logReq.setQueue_code(queue_code);
										PCWriteCardTools.saveQueueCardActionLog(logReq);
										//开户成功清空连续失败次数
										PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_2);
									}
								}else{
									logger.info("订单["+order_id+"]数据不正确["+orderExt.getOrder_model()+","+orderExt.getFlow_trace_id()+","+orderExt.getIs_aop()+"]");
								}
							}catch(Exception e){
								logger.info("执行开户规则失败" + e.getMessage());
								e.printStackTrace();
							}
						}
						
					};
					service.execute(run);
				}
			}
			service.shutdown();
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		}catch(Exception e){
			logger.info("orderOpenAccount执行失败" + e.getMessage());
			e.printStackTrace();
		}
		logger.info("orderOpenAccount===============end");
	}
	
}
