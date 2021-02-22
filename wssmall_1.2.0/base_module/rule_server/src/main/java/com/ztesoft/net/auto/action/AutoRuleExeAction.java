package com.ztesoft.net.auto.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import params.ZteResponse;
import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IRuleService;
import zte.net.iservice.IUosService;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.process.req.UosDealReq;
import zte.params.process.req.UosFlowListReq;
import zte.params.process.req.UosFlowReq;
import zte.params.process.req.UosStartReq;
import zte.params.process.resp.FlowDefine;
import zte.params.process.resp.UosDealResp;
import zte.params.process.resp.UosFlowListResp;
import zte.params.process.resp.UosFlowResp;
import zte.params.process.resp.UosNode;
import zte.params.process.resp.UosStartResp;
import zte.params.process.resp.WorkItem;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CoQueue;

import consts.ConstsCore;

public class AutoRuleExeAction extends WWAction{

	private IRuleService ruleService;
	
	//@Resource
	private IUosService uosService;
	//@Resource
	private IOrderServices orderServices;
	//@Resource
	private IAutoRule autoRuleImpl;
	//@Resource
	private CoQueueBaseRule orderStandardizing;
	
	private String order_id;
	
	public static int count = 1;
	
	public String guiji(){
		CoQueue coQueue = autoRuleImpl.getQueueById("201411063206376719",true);
		CoQueueRuleReq coQueueRuleReq = new CoQueueRuleReq();
		coQueueRuleReq.setCo_id(coQueue.getCo_id());
		coQueueRuleReq.setService_code(Consts.SERVICE_CODE_CO_GUIJI_NEW);
		coQueueRuleReq.setSource_from(coQueue.getSource_from());
		coQueueRuleReq.setObject_id(coQueue.getObject_id());
		coQueueRuleReq.setCoQueue(coQueue);
		for(int i=0;i<1;i++){
			orderStandardizing.coQueue(coQueueRuleReq);
			/*new Thread(new Runnable() {
				@Override
				public void run() {
					orderStandardizing.coQueue(coQueueRuleReq);
				}
			}).start();*/
		}
		return null;
	}
	
	public String testRule(){
		TacheFact fact = new TacheFact();
		fact.setOrder_id("201410204788860477");
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
    	req.setPlan_id("201410091613000142");
    	req.setFact(fact);
    	PlanRuleTreeExeResp resp = ruleService.exePlanRuleTree(req);
    	logger.info(resp.getError_msg());
    	count ++;
		return null;
	}
	
	public String startFlow() throws Exception{
		long start = System.currentTimeMillis();
		if(StringUtil.isEmpty(order_id))order_id= "ZBWO201412046810229601";
		/*for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {*/
					try{
						logger.info("===============================测试规则 start=======================================");
						PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
						TacheFact fact = new TacheFact();
						fact.setOrder_id(order_id);
						req.setPlan_id("201410091613000142");
						req.setFact(fact);
						req.setDeleteLogs(true);
						PlanRuleTreeExeResp resp = ruleService.exePlanRuleTree(req);
						
						/*PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
						TacheFact fact = new TacheFact();
						fact.setOrder_id("DG201410236062860650");
						req.setPlan_id("107");
						req.setFact(fact);
						PlanRuleTreeExeResp resp = CommonDataFactory.getInstance().exePlanRuleTree(req);*/
						logger.info("===============================测试规则 end=======================================");
						
					}catch(Exception ex){
						ex.printStackTrace();
					}
					/*}
			}).start();
		}*/
		long end = System.currentTimeMillis();
		logger.info("================规则测试总时间=======>"+(end-start));
		
		//CommonDataFactory.getInstance().updateOrderTree("SW201411015609860876");
		/*OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree("SW201411011795860883");
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setOrder_id("SW201411011795860883");
		orderExtBusiRequest.setFlow_trace_id("DD");//修改下一环节环节编码
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();*/
		
		try{
			//AttrDef def = CacheUtils.getCacheAttrDef("is_customized-999");
			//logger.info(def);
			
			//CommonDataFactory.getInstance().updateOrderTree("201411256843122086");
			//OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree("HZ201411117195861605");
			//logger.info(1111111111);
			//testQueryFlow();
			
			//testStartFlow();
			
			//testDealFlow();
			
			//testQueryFlowInst();
			
			//startOrderFlow();
			
			//testOrderTree();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		return null;
	}
	
	private void testOrderTree(){
		long start = System.currentTimeMillis();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree("201410166559860470");
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
		orderExtBusiRequest.setCol3("123123");
		orderExtBusiRequest.setCol4("dsfd");
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		long end = System.currentTimeMillis();
		logger.info(end-start);
	}

	public String exeRule(){
		long start = System.currentTimeMillis();
		//OrderTestFact fact = new OrderTestFact();
		TacheFact fact = new TacheFact();
		fact.setOrder_id("201410166559860470");
		fact.setTrace_flow_id("B");
		
		/*PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
    	//req.setReCurrRule(true);
    	req.setPlan_id("101");
    	req.setFact(fact);
    	PlanRuleTreeExeResp resp = ruleService.exePlanRuleTree(req);
    	logger.info(resp.getBody());*/
		
		RuleTreeExeReq rreq = new RuleTreeExeReq();
    	//rreq.setPlan_id("1");
    	rreq.setRule_id("2");
    	//rreq.setExeParentsPeerAfRules(false);
    	rreq.setCheckCurrRelyOnRule(false);
    	rreq.setCheckAllRelyOnRule(false);
    	rreq.setFact(fact);
    	RuleTreeExeResp rresp = ruleService.exeRuleTree(rreq);
        logger.info(rresp);
    	
    	long end = System.currentTimeMillis();
    	double e = (end-start)/1000d;
    	logger.info(end-start+"\t"+e);
		/*try{
			//testQueryFlow();
			
			//testStartFlow();
			
			//testDealFlow();
			
			//testQueryFlowInst();
			
			startOrderFlow();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}*/
		return null;
	}
	
	/**
	 * 启动订单工作流
	 * @作者 MoChunrun
	 * @创建日期 2014-10-16 
	 * @throws Exception
	 */
	public void startOrderFlow() throws Exception{
		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		queryParams.put("order_id", "201410166559860470");
		busi.setEnginePath("workFlowStart.exec");
		busi.setQueryParams(queryParams);
		ZteResponse response = orderServices.execBusiComp(busi);
	}
	
	/**
	 * 查询流程
	 * @作者 MoChunrun
	 * @创建日期 2014-10-15 
	 * @return
	 */
	private UosFlowListResp testQueryFlow(){
		UosFlowListReq req = new UosFlowListReq();
		req.setPageIndex(2);
		req.setPageSize(4);
		//req.setProcessName("3G合约机");
		UosFlowListResp resp = uosService.queryFlowDefineList(req);
		ArrayList<FlowDefine> list = resp.getFlowList();
		for(FlowDefine d:list){
			logger.info(d.getProcessId()+"\t"+d.getProcessName());
		}
		return resp;
	}
	
	/**
	 * 启动流程
	 * @作者 MoChunrun
	 * @创建日期 2014-10-15 
	 * @return
	 */
	private UosStartResp testStartFlow(){
		UosStartReq req = new UosStartReq();
		req.setStaffId(100);
		req.setStaffName("");
		req.setProcessId("3280");
		UosStartResp resp = uosService.startProcess(req);
		ArrayList<WorkItem> list = resp.getWorkItems();
		for(WorkItem i:list){
			logger.info(i.getTacheId()+"\t"+i.getTacheCode()+"\t"+i.getProcessInstanceId()+"\t"+i.getState());
		}
		return resp;
	}
	
	private UosDealResp testDealFlow(){
		UosDealReq req = new UosDealReq();
		req.setStaffId(100);
		req.setStaffName("");
		req.setProcessInstanceId("3278347");
		req.setTacheCode("B");
		//req.setDealType("B");
		UosDealResp resp = uosService.dealProcess(req);
		logger.info(resp.isEnd()+"\t"+resp.isTerminal());
		ArrayList<WorkItem> list = resp.getWorkItems();
		for(WorkItem i:list){
			logger.info(i.getTacheId()+"\t"+i.getTacheCode()+"\t"+i.getProcessInstanceId()+"\t"+i.getState());
		}
		return resp;
	}
	
	public UosFlowResp testQueryFlowInst(){
		UosFlowReq req = new UosFlowReq();
		req.setProcessInstanceId("3278201");
		UosFlowResp resp = uosService.queryFlow(req);
		for(UosNode n:resp.getNodes()){
			logger.info(n.getTacheCode()+"\t"+n.getName()+"\t"+n.getState()+"\t"+n.getType());
		}
		return resp;
	}

	public IRuleService getRuleService() {
		return ruleService;
	}

	public void setRuleService(IRuleService ruleService) {
		this.ruleService = ruleService;
	}

	public IUosService getUosService() {
		return uosService;
	}

	public void setUosService(IUosService uosService) {
		this.uosService = uosService;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public IAutoRule getAutoRuleImpl() {
		return autoRuleImpl;
	}

	public void setAutoRuleImpl(IAutoRule autoRuleImpl) {
		this.autoRuleImpl = autoRuleImpl;
	}

	public CoQueueBaseRule getOrderStandardizing() {
		return orderStandardizing;
	}

	public void setOrderStandardizing(CoQueueBaseRule orderStandardizing) {
		this.orderStandardizing = orderStandardizing;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	
}
