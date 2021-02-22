package com.ztesoft.net.mall.core.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IUosService;
import zte.params.process.req.UosFlowReq;
import zte.params.process.resp.UosFlowResp;
import zte.params.process.resp.UosNode;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.CorrectFlowTraceLogModel;

import consts.ConstsCore;

/**
 * 修复订单流程环节错误定时器
 * @author zhaoc
 *
 */
public class CorrectFlowTraceIdTimer {


	private Logger log = Logger.getLogger(this.getClass());

	public void doTask() throws Exception{
		try {
        	if("N".equals(EopSetting.DB_CONNECT)){
    			return;
    		}
        	
    		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "doTask")) {
      			return;
    		}
    		
    		//查询天数
    		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
    		String days = cacheUtil.getConfigInfo("CORRECT_FLOW_TRACE_DAYS");
    		if(StringUtils.isBlank(days))
    			days = "1";
    		
    		String platType = cacheUtil.getConfigInfo("CORRECT_FLOW_TRACE_FROM");
            
    		//查询在支付环节的订单
    		String sql = "select a.order_id from es_order_ext a where a.flow_trace_id='"+EcsOrderConsts.DIC_ORDER_NODE_P
    				+"' and a.tid_time>(sysdate-"+days+") and a.tid_time<(sysdate-2/24/60) "
    				+" and a.order_from in ("+platType+")";
    		
    		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
    		log.info("CorrectFlowTraceIdTimer query sql :"+sql);
    		List<Map<String, String>> outIdList = support.queryForList(sql);
    		
    		if(null != outIdList && outIdList.size() > 0){
    			for (Map<String, String> map : outIdList) {
    				String orderId = map.get("order_id");
    				
    				//循环处理的时间可能较长，根据订单编号重新从数据库中查询flow_trace_id
    				String traceQuerySql = "SELECT a.flow_trace_id FROM es_order_ext a WHERE a.order_id='"+orderId+"'";
    				
    				List<Map<String, String>> ret = support.queryForList(traceQuerySql);
    				String flowTraceId = "";
    				
    				if(null != ret && ret.size()>0){
    					flowTraceId = ret.get(0).get("flow_trace_id");
    				}
    				
    				//查询已经不在支付状态的跳出
    			    if(!EcsOrderConsts.DIC_ORDER_NODE_P.equals(flowTraceId)){
    			    	log.info(orderId+" order's current flow_trace_id="+flowTraceId+",skip");
    			    	continue;
    			    }
    				
    				OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(orderId);
    			    String processInstanceId = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
    				
    			    String wrongTrace = flowTraceId;
    			    
    				IUosService service = SpringContextHolder.getBean("uosService");
    				
    			    UosFlowReq req = new UosFlowReq();
    			    req.setProcessInstanceId(processInstanceId);
    			    
    			    //查询工单环节
    			    UosFlowResp resp = service.queryFlow(req);
    			    
    			    ArrayList<UosNode> flowNodes = resp.getNodes();
    			    
    			    boolean needCorrect = false;
    			    
    			    for(UosNode node : flowNodes){
    			    	if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(node.getTacheCode())
    			    			&& StringUtils.isNotBlank(node.getWorkItemId())){
    			    		needCorrect = true;
    			    		break;
    			    	}
    			    }
    			    
    			    if(needCorrect){
    			    	//修复数据
    			    	Map<String,String> fields = new HashMap<String, String>();
    			    	fields.put("flow_trace_id", EcsOrderConsts.DIC_ORDER_NODE_B);
    			    	
						Map<String,String> where = new HashMap<String, String>();
						where.put("order_id", orderId);
						
						support.update("order_ext", fields , where );
    			    	
    			    	//更新订单树
    			    	OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
    			    	
    			    	orderExtBusiRequest.setFlow_trace_id(EcsOrderConsts.DIC_ORDER_NODE_B);
    					orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
    					orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
    					//修改订单流程状态
    					orderExtBusiRequest.store();
    					
    					//保存日志
    					CorrectFlowTraceLogModel l = new CorrectFlowTraceLogModel();
    					l.setOrder_id(orderId);
    					l.setWrong_trace_id(wrongTrace);
    					l.setRight_trace_id(orderExtBusiRequest.getFlow_trace_id());
    					
    					Date now = new Date();
    					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    					
    					String done_date = format.format(now);
						l.setDone_date(done_date);
    					
						support.insert("es_corret_flow_trace_log", l);
    			    }
    			}
    		}
    		
        } catch (Exception e) {
            log.info("CorrectFlowTraceIdTask修复流程环节错误TASK执行出错", e);
			log.info("CorrectFlowTraceIdTask修复流程环节错误TASK执行出错");
            e.printStackTrace();
        }
	}
}
