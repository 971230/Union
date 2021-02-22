package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.OrderQueryReq;
import zte.net.ecsord.params.ecaop.resp.OrderQueryRespone;
import zte.net.ecsord.params.ecaop.resp.vo.OrderInfoRespVo;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.ListUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AopQueryDataVo;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdArchiveManager;
import com.ztesoft.net.service.IOrdArchiveTacheManager;

public class QryOrdTimer {
	private static Logger logger = Logger.getLogger(QryOrdTimer.class);
	ZteClient client= ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private IOrdArchiveManager ordArchiveManager;	
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;	
	private static final int maxNum = 500;  //每次扫描500条
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		List<AopQueryDataVo> list = ecsOrdManager.getOrdId();
		if(ListUtil.isEmpty(list)||list.size()<=0){
			return;
		}
		logger.info("---------------------定时归档开始--------------------");
		ThreadPoolExecutor executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_CACHE, 500);
		try {
		
			for(int i=0;i<list.size();i++){
				OrderQueryReq req=new OrderQueryReq();
				String order_id = list.get(i).getOrder_id();
				
				//锁定
				ecsOrdManager.updateDealStatus(order_id,"1");
				
				req.setNotNeedReqStrOrderId(order_id);
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
				
					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
					
						try {
							//调用规则
							coQueue((OrderQueryReq)zteRequest);
						
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
	
	public OrderQueryRespone coQueue(OrderQueryReq req) throws ApiBusiException {
		OrderQueryRespone rsp = client.execute(req, OrderQueryRespone.class);//调用接口 
		if(!"-9999".equals(rsp.getCode())&&StringUtils.isEmpty(rsp.getCode())){
			List<OrderInfoRespVo> ordiInfo_list = rsp.getOrdiInfo();
			for(int j=0;j<ordiInfo_list.size();j++){
				OrderInfoRespVo ordiInfo = ordiInfo_list.get(j);
				String orderCode= ordiInfo.getOrderCode();
				if(!StringUtils.isEmpty(orderCode)&&"0".equals(orderCode)){
					/*try{
						ordArchiveTacheManager.ordLogisArchive(req.getNotNeedReqStrOrderId());//逻辑归档
						ecsOrdManager.updateDealStatus(req.getNotNeedReqStrOrderId(),"2");
					}catch(Exception e){
						ordArchiveManager.insertArchiveFailLog(req.getNotNeedReqStrOrderId() , e.getMessage());//记录错误日志
						ecsOrdManager.updateDealStatus(req.getNotNeedReqStrOrderId(),"3");
					}*/
					TacheFact fact = new TacheFact();
					fact.setOrder_id(req.getNotNeedReqStrOrderId());
					RuleTreeExeResp rresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.ORDER_ARCHIVE_PLAN, ZjEcsOrderConsts.DT_ORDER_BACK_RULE_ID,
							fact, false, false,EcsOrderConsts.DEAL_FROM_PAGE);
					if(rresp!=null && "0".equals(rresp.getError_code())){
						ecsOrdManager.updateDealStatus(req.getNotNeedReqStrOrderId(),"2");
					}
					else{
						ecsOrdManager.updateDealStatus(req.getNotNeedReqStrOrderId(),"3");
					}
				}else{
					ecsOrdManager.updateDealStatus(req.getNotNeedReqStrOrderId(),"3");
				}
			}
		}else{
			ecsOrdManager.updateDealStatus(req.getNotNeedReqStrOrderId(),"3");
		}
		return rsp;
		
	}
	
}