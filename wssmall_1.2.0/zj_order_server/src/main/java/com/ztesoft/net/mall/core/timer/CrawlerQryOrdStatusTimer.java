package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.req.ZbOrderStateQueryReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.ListUtil;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AopQueryDataVo;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdArchiveManager;
import com.ztesoft.net.service.IOrdArchiveTacheManager;

import consts.ConstsCore;

public class CrawlerQryOrdStatusTimer {
	private static Logger logger = Logger.getLogger(CrawlerQryOrdStatusTimer.class);
	ZteClient client= ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private IOrdArchiveManager ordArchiveManager;	
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;	
	private static final int maxNum = 500;  //每次扫描500条
	public void run(){
		if("N".equals(EopSetting.DB_CONNECT)){
			return;
		}
		
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		List<AopQueryDataVo> list = ecsOrdManager.getOrdId();
		if(ListUtil.isEmpty(list)||list.size()<=0){
			return;
		}
		logger.info("---------------------定时归档开始--------------------");
		ThreadPoolExecutor executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_CACHE, 50);
		try {
		
			for(int i=0;i<list.size();i++){
				ZbOrderStateQueryReq req=new ZbOrderStateQueryReq();
				String order_id = list.get(i).getOrder_id();
				String out_tid = list.get(i).getOut_tid();
				//锁定
				ecsOrdManager.updateDealStatus(order_id,"1");
				
				req.setOrderNo(out_tid);
				req.setOrderId(order_id);
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
				
					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
					
						try {
							//调用规则
							coQueue((ZbOrderStateQueryReq)zteRequest);
						
						} catch (Exception e) {
						
							e.printStackTrace();
						}
					
						return new ZteResponse();
					
					}
				});
			
				//放入任务池
				ThreadPoolFactory.submit(taskThreadPool, executor);  
			
			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			//关闭
			ThreadPoolFactory.closeThreadPool(executor);
		}
		
	}
	
	public ZteResponse coQueue(ZbOrderStateQueryReq req) throws ApiBusiException {
		ZteResponse rsp = client.execute(req, ZteResponse.class);//调用接口 
		if(ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){
			String orderStatus = rsp.getBody();
			if("成功关闭".equals(orderStatus)){
				CommonDataFactory.getInstance().updateAttrFieldValue(req.getOrderId(), new String[]{AttrConsts.ACTIVE_FLAG}, new String[]{EcsOrderConsts.LATER_ACTIVE_STATUS_3});
				
				TacheFact fact = new TacheFact();
				fact.setOrder_id(req.getOrderId());
				RuleTreeExeResp rresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.ORDER_ARCHIVE_PLAN, ZjEcsOrderConsts.CRAWLER_ORDER_BACK_RULE_ID,
						fact, false, false,EcsOrderConsts.DEAL_FROM_PAGE);
				
				ecsOrdManager.updateDealStatus(req.getOrderId(),"2");//修改状态为处理成功
			}else{
				ecsOrdManager.updateDealStatus(req.getOrderId(),"3");
				CommonDataFactory.getInstance().updateAttrFieldValue(req.getOrderId(), new String[]{AttrConsts.ACTIVE_FLAG}, new String[]{EcsOrderConsts.LATER_ACTIVE_STATUS_1});
			}
		}else{
			ecsOrdManager.updateDealStatus(req.getOrderId(),"3");
			CommonDataFactory.getInstance().updateAttrFieldValue(req.getOrderId(), new String[]{AttrConsts.ACTIVE_FLAG}, new String[]{EcsOrderConsts.LATER_ACTIVE_STATUS_1});
		}
		return rsp;
		
	}
	
}
