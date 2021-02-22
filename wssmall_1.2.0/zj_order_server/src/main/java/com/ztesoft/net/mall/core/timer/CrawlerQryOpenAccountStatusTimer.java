package com.ztesoft.net.mall.core.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.order.req.OrderQueueCardActionLogReq;
import params.req.OpenAccountDetailReq;
import params.req.QueryOrderProcessReq;
import params.resp.OpenAccountDetailResp;
import params.resp.QueryOrderProcessResp;
import util.Utils;
import zte.net.card.params.req.QueryQueueAlowCountReq;
import zte.net.card.params.resp.QueryQueueAlowCountResp;
import zte.net.card.params.resp.vo.MachineGroupStateVo;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.PCWriteCardTools;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.sqls.SF;

import consts.ConstsCore;
/**
 * PC自动化生产爬虫同步总商开户状态
 * @author ricky
 *
 */
public class CrawlerQryOpenAccountStatusTimer {
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private CacheUtil cacheUtil;
	private static String autoWriteCardType;
	private static Logger logger = Logger.getLogger(CrawlerQryOpenAccountStatusTimer.class);
	public CrawlerQryOpenAccountStatusTimer() {
		// TODO Auto-generated constructor stub
	}

	public void run(){
		if("N".equals(EopSetting.DB_CONNECT)){
			return;
		}
		
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		if(StringUtils.isEmpty(autoWriteCardType)){
			autoWriteCardType =cacheUtil.getConfigInfo("AUTO_WRITE_CARD_TYPE");
		}
		if("1".equals(autoWriteCardType)){//软创自动写卡
			zteAutoWriteCard();
		}else if("2".equals(autoWriteCardType)){//森锐自动写卡
			srAutoWriteCard();
		}
		
		
	}
	
	private void zteAutoWriteCard(){
		//步骤一 查询调度中心是否有可写卡的额度
		Map<String,Integer> writeNumMap= new HashMap<String,Integer>();
		QueryQueueAlowCountReq allowCountReq = new QueryQueueAlowCountReq();
		final ZteClient client = ClientFactory.getZteDubboClient( ManagerUtils.getSourceFrom());
		QueryQueueAlowCountResp allowCountResp = client.execute(allowCountReq, QueryQueueAlowCountResp.class);
		if(null!=allowCountResp){
			List<MachineGroupStateVo> machineGroupList =  allowCountResp.getMachineGroupStateList();
			if(null!=machineGroupList){
				for(MachineGroupStateVo vo :machineGroupList){
					writeNumMap.put(vo.getGroupNo(), vo.getAllowCount());
				}
						
				//查询处于开户环节的
				String sql ="select * from es_queue_open_account where status='0' order by create_time asc";
				final IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
						
				List<Map> list = baseDaoSupport.queryForList(sql);
				if(null!=list&&list.size()>0){
					for(Map map:list){
						String queueCode= map.get("queue_code").toString();
						if(writeNumMap.get(queueCode)<=0){
							continue;
						}
						String outTid= map.get("out_tid").toString();
						final String orderId = map.get("order_id").toString();
						final String orderModel = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_MODEL);
						QueryOrderProcessReq req = new QueryOrderProcessReq();
						req.setOrderNo(outTid);
						QueryOrderProcessResp resp= client.execute(req, QueryOrderProcessResp.class);
						logger.info("订单"+orderId+"查询总商系统开户环节数据结果："+resp.getError_msg());
						if(ConstsCore.ERROR_SUCC.equals(resp.getError_code())){//总商已处于开户环节
							//开始执行后面的环节
							writeNumMap.put(queueCode, writeNumMap.get(queueCode)-1);
									
							//修改订单状态为开户中
							String sql1= SF.ecsordSql("UPDATE_ORDER_OPEN_ACCOUNT_STATUS");
							baseDaoSupport.execute(sql1, ZjEcsOrderConsts.QUEUE_ORDER_OPEN_1,orderId);
							OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
							logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_01);
							logReq.setAction_desc("订单执行开户操作,开户中");
							logReq.setOrder_id(orderId);
							logReq.setQueue_code(queueCode);
							PCWriteCardTools.saveQueueCardActionLog(logReq);
							Thread thead = new Thread(new Runnable() {
								@Override
								public void run() {
									String ruleId ="";
									if(ZjEcsOrderConsts.ORDER_MODEL_07.equals(orderModel)){
										ruleId = ZjEcsOrderConsts.ORDER_MODEL_07_ZB_OPEN_ACCOUNT_DETAIL;
									}else if(ZjEcsOrderConsts.ORDER_MODEL_08.equals(orderModel)){
										ruleId = ZjEcsOrderConsts.ORDER_MODEL_08_ZB_OPEN_ACCOUNT_DETAIL;
									}
										
									RuleTreeExeReq req = new RuleTreeExeReq();
									ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
									req.setRule_id(ruleId);
									req.setExePeerAfRules(false);
									req.setExeParentsPeerAfRules(false);
									TacheFact fact = new TacheFact();
									fact.setOrder_id(orderId);
									req.setFact(fact);
									RuleTreeExeResp busiResp = client.execute(req,RuleTreeExeResp.class);
									if(!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
										String sql1= SF.ecsordSql("UPDATE_ORDER_OPEN_ACCOUNT_STATUS");
										baseDaoSupport.execute(sql1, ZjEcsOrderConsts.QUEUE_ORDER_OPEN_3,orderId);//开户异常
									}else{
										PlanRuleTreeExeResp rresp =RuleFlowUtil.executePlanRuleTree(ZjEcsOrderConsts.OPEN_ACCOUNT_PLAN_ID, 0, fact, ZjEcsOrderConsts.DEAL_FROM_INF);
										logger.info("订单"+orderId+"执行总商进入开户详情"+rresp.getError_msg()+"====="+rresp.getError_code());
										if(!ConstsCore.ERROR_SUCC.equals(rresp.getError_code())){
											String sql1= SF.ecsordSql("UPDATE_ORDER_OPEN_ACCOUNT_STATUS");
											baseDaoSupport.execute(sql1, ZjEcsOrderConsts.QUEUE_ORDER_OPEN_3,orderId);//开户异常
										}else{
											String sql1= SF.ecsordSql("UPDATE_ORDER_OPEN_ACCOUNT_STATUS");
											baseDaoSupport.execute(sql1, ZjEcsOrderConsts.QUEUE_ORDER_OPEN_2,orderId);//开户成功
										}
									}
								}
							});
							thead.start();
						} 
					}
				}
			}else{
				logger.info("未配置写卡机组");
			}
		}else{
			logger.info("查询调度中心是否有可写卡的额度时发生异常");
		}
	}
	private void srAutoWriteCard(){
		//查询处于开户环节的
		String sql="select * from es_queue_write_card a,es_order_ext  b where a.source_from =b.source_from and  a.source_from = +'"+ManagerUtils.getSourceFrom()+"' and a.order_id =b.order_id and b.order_from ="+ZjEcsOrderConsts.ORDER_FROM_100032+" and a.open_account_status='0'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		final ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		List<Map> list = baseDaoSupport.queryForList(sql);
		if(null!=list&&list.size()>0){
			for(Map map:list){
				String outTid= map.get("out_tid").toString();
				final String orderId = map.get("order_id").toString();
				final String orderModel = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_MODEL);
				//查询订单详情
				OpenAccountDetailReq req = new OpenAccountDetailReq();
				req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_NUM));
				req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.OUT_TID));
				OpenAccountDetailResp rsp = client.execute(req, OpenAccountDetailResp.class);
				if(!StringUtils.equals(rsp.getError_code(), ConstsCore.ERROR_SUCC)){
					logger.info("=====OrdOpenAccountTacheManager zbEnterAccountDetailPage 进入总部开户详情页接口调用出错【errorMsg:"+rsp.getError_msg()+"】");
					//标记异常
					Utils.markException(orderId,false,EcsOrderConsts.ABNORMAL_TYPE_OPEN,rsp.getError_msg());
				}else{
					final String zbOpenType = rsp.getZb_open_type();//总部开户类型
					if("1".equals(zbOpenType)&&ZjEcsOrderConsts.ORDER_MODEL_08.equals(orderModel)){//总商手动开户订单在订单系统也只能手动开户
						CommonDataFactory.getInstance().updateAttrFieldValue(orderId,new String[]{AttrConsts.ORDER_MODEL}, new String[]{ZjEcsOrderConsts.ORDER_MODEL_07});
						OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
						orderTree.getOrderExtBusiRequest().setOrder_model(ZjEcsOrderConsts.ORDER_MODEL_07);
						orderTree.store();
					}
					CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[]{AttrConsts.ZB_OPEN_TYPE}, new String[]{zbOpenType}) ;
					Thread thead = new Thread(new Runnable() {
						@Override
						public void run() {
							String ruleId="" ;
							RuleTreeExeReq reqNext = new RuleTreeExeReq();
							
							TacheFact factNext = new TacheFact();
							factNext.setOrder_id(orderId);
							reqNext.setFact(factNext);

							if(ZjEcsOrderConsts.ORDER_MODEL_07.equals(orderModel)){
								ruleId = ZjEcsOrderConsts.ORDER_MODEL_07_ZB_OPEN_ACCOUNT_DETAIL;//如果是手动开户则只执行一条规则

								reqNext.setExePeerAfRules(false);
								reqNext.setExeParentsPeerAfRules(false);
							}else if(ZjEcsOrderConsts.ORDER_MODEL_08.equals(orderModel)){
								ruleId = ZjEcsOrderConsts.ORDER_MODEL_08_ZB_OPEN_ACCOUNT_DETAIL;
								reqNext.setCheckAllRelyOnRule(true);

								reqNext.setCheckCurrRelyOnRule(true);

							}
							reqNext.setRule_id(ruleId);
							RuleTreeExeResp rsp = client.execute(reqNext, RuleTreeExeResp.class);
							if(ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){
								//更新订单状态为开户中
								String updateSql= "update es_queue_write_card set open_account_status ='1' where order_id =?";
								IDaoSupport baseDaoSupport1 = SpringContextHolder.getBean("baseDaoSupport");
								baseDaoSupport1.execute(updateSql, orderId);
							}
						}
					});
					thead.start();
				}
				
			}
		}
	}
}
