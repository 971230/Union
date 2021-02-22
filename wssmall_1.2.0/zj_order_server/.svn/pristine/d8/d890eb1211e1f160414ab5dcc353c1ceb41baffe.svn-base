package com.ztesoft.net.mall.core.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import params.ZteResponse;
import params.req.QueryOrderAllocationStatusReq;
import util.Utils;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
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
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IOrdVisitTacheManager;


/**
 * 订单分配定时器
 * @作者 lzg
 * @创建日期 2016-12-28 
 * @版本 V 1.0
 */
public class OrderAutoAllocationTask{
	
	private static Logger log = Logger.getLogger(OrderAutoAllocationTask.class);
	
	@Resource
	private IOrdVisitTacheManager ordVisitTacheManager;
	
	/**
	 * 订单分配配时器
	 * @throws Exception
	 */
	public void runAuditTask() throws Exception {
        try {
        	if("N".equals(EopSetting.DB_CONNECT)){
    			return;
    		}
        	
    		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "runAuditTask")) {
      			return;
    		}
            log.info("开始[总部系统-自动分配]任务:");
            log.info("开始[总部系统-自动分配]任务:");
            autoAllocation();
            log.info("结束[总部系统-自动分配]任务:");
            log.info("结束[总部系统-自动分配]任务:");
        } catch (Exception e) {
            log.info("[总部系统-订单审核]任务出错", e);
			log.info("OrderAutoAllocationTask Exception [总部系统-订单审核]任务出错");
            e.printStackTrace();
        }
    }
	
	/**
	 * 自动分配
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void autoAllocation() throws Exception {
		//审核状态：0未审核，1审核成功，2审核失败,3老用户无需审核
		String sql = "select a.out_tid from es_order_audit_zb a where  a.audit_status in ('1','3')   and a.distribute_status = '0'";
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		List<Map<String, String>> outIdList = support.queryForList(sql);
		if(null != outIdList && outIdList.size() > 0){
			for (Map<String, String> map : outIdList) {
				String outTid = map.get("out_tid");
				
				OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTreeByOutId(outTid);
				
				OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
				
				String order_id = orderTree.getOrder_id();
				String order_model = orderExt.getOrder_model();
				
				QueryOrderAllocationStatusReq reqParams = new QueryOrderAllocationStatusReq();
				reqParams.setOrderNo(outTid);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				ZteResponse resp = client.execute(reqParams, ZteResponse.class);
				if("0".equals(resp.getError_code()) && "0".equals(resp.getBody())){
					RuleTreeExeReq reqNext = new RuleTreeExeReq();
					if(ZjEcsOrderConsts.ORDER_MODEL_08.equals(order_model)){//自动写卡模式
						reqNext.setRule_id(ZjEcsOrderConsts.CRAWLER_ORDER_ALLOCATION_AUTO);
					}else if("07".equals(order_model)){//手动写卡模式
						reqNext.setRule_id(ZjEcsOrderConsts.CRAWLER_ORDER_ALLOCATION_MANUAL);
					}else {
						log.info("==========[自动分配未匹配到生产模式 ：]order_id:"+order_id+",order_model:"+order_model);
						continue;
					}
					TacheFact factNext = new TacheFact(); 
					factNext.setOrder_id(order_id);
					reqNext.setFact(factNext);
					reqNext.setCheckAllRelyOnRule(true);
					reqNext.setCheckCurrRelyOnRule(true);
					RuleTreeExeResp rsp = client.execute(reqNext, RuleTreeExeResp.class);
					BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
					if(null == busiResp || !"0".equals(busiResp.getError_code())){//当调用不成功时打印日志
						log.info("OrderAutoAllocationTask.autoAllocation 自动分配订单Job执行【"+reqNext.getRule_id()+"】规则失败");
					}else{
						PlanRuleTreeExeResp rresp =RuleFlowUtil.executePlanRuleTree(ZjEcsOrderConsts.ORDER_PRE_DEAL_PLAN, 0, factNext, ZjEcsOrderConsts.DEAL_FROM_INF);
					}

				} else if("0".equals(resp.getError_code()) && "2".equals(resp.getBody())){
					Map paramsMap = new HashMap();
					paramsMap.put("distribute_status", "2");
					paramsMap.put("out_tid", outTid);
					this.ordVisitTacheManager.upZbOrderAuditStatus(paramsMap);
					Utils.writeExp(order_id, "查询总商订单分配环节", "查询总商系统订单分配环节失败"+resp.getError_msg(), EcsOrderConsts.BASE_YES_FLAG_1);
				}
			}
		}
    }
	
//	public static void main(String[] args) throws InterruptedException {
//		try {
//			String time = DateUtil.getTime2();
//			log.info("start"+time);
//			Thread.sleep(10000);
//			log.info("end  "+DateUtil.getTime2());
//		} catch (FrameException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
