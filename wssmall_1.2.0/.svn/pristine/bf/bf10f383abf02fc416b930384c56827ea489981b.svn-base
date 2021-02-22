package com.ztesoft.net.auto.rule.fact;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import oracle.net.aso.e;
import params.ZteRequest;
import params.ZteResponse;
import utils.UUIDUtil;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.rule.mode.ModeFact;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.rule.workflow.WorkFlowFact;
import zte.net.iservice.IOrderServices;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.auto.rule.i.IAutoFact;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.json.filter.ExcludesProFilter;
import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public abstract class AutoFact implements IAutoFact,Serializable {
	
	/**
	 * 规则执行结果 key值为action_id
	 */
	protected Map<String,ZteResponse> responses = new HashMap<String,ZteResponse>();
	protected Map<String,Object> valueResponse = new HashMap<String,Object>();
	protected Map<String,Boolean> rule_execute = new HashMap<String,Boolean>();
	public static final ThreadLocal<PlanRule> threadRule = new ThreadLocal<PlanRule>();
	//protected PlanRule rule;
	protected String obj_id;
	protected String flow_id;
	protected String trace_flow_id;
	protected ZteRequest request;
	protected String deal_from;
	protected String deal_type;
	protected String deal_desc;
	protected boolean execute;
	private   String currTraceId;
	private static Logger logger = Logger.getLogger(AutoFact.class);
	/**
	 * 已执行的规则
	 */
	protected List<PlanRule> hasExeRuleList = new ArrayList<PlanRule>();
	/**
	 * 已执行的规则日志
	 */
	protected List<RuleExeLog> hasExeRuleLogs = new ArrayList<RuleExeLog>();
	
	protected RuleExeLog curr_log;
	
	/**
	 * 获取执行结果
	 * @作者 MoChunrun
	 * @创建日期 2014-9-12 
	 * @param action_id
	 * @return
	 */
	public ZteResponse getRuleResponse(String action_id){
		return responses.get(action_id);
	}
	
	/**
	 * 返回数据记录主键
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @return
	 */
	public abstract String getObj_id();
	/**
	 * 获取流程ID 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-16 
	 * @return
	 */
	public abstract String getTrace_flow_id();
	
	/**
	 * 规则条件通过后执行该方法
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param rule_id
	 */
	public void execute(String action_id)throws Exception{
		this.obj_id=this.getObj_id();
		this.trace_flow_id=this.getTrace_flow_id();
		RuleExeLog log = null;
		rule_execute.put(action_id, true);
		PlanRule rule = getRule();
		String plan_id = rule.getPlan_id();
		String rule_id = rule.getRule_id();
		String exec_path = rule.getExe_path();
		String rule_name = rule.getRule_name();
		rule.setExecute(true);//已经执行了业务组件
		long start = System.currentTimeMillis();
		try{
			rule.setAction_id(action_id);
			IAutoRule autoRuleImpl = SpringContextHolder.getBean("autoRuleImpl");
			IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
			Plan plan = !StringUtil.isEmpty(rule.getPlan_id()) ? autoRuleImpl.getPlan(rule.getPlan_id()) : null;
			List<RuleConsts> consts = autoRuleImpl.queryRuleConsts(rule.getRule_id(), rule.getAction_id());
			rule.setConsts(consts);
			//执行组件================
			
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("CONSTS", consts);
			queryParams.put("plan", plan);
			queryParams.put("order_id", this.getObj_id());
			queryParams.put("plan_id", plan_id);
			queryParams.put("rule_id", rule_id);
			queryParams.put("fact", this);
			queryParams.put("deal_from", deal_from);
			queryParams.put("deal_type", this.getDeal_type());
			queryParams.put("deal_desc", this.getDeal_desc());
//			if(!StringUtil.isEmpty(rule_id)){
//				String traceId = getTraceId(rule_id);		//根据plan_id得到
//				if(!StringUtil.isEmpty(traceId)){
//					this.currTraceId = traceId;
//				}
//			}
			
			if(this instanceof TacheFact){
				TacheFact tacheFact = (TacheFact)this;
				tacheFact.toStringN();
			}
			if(this instanceof ModeFact){
				ModeFact modeFact = (ModeFact)this;
				modeFact.toStringN();
			}
			busi.setEnginePath(exec_path);
			busi.setOrder_id(this.getObj_id());
			busi.setQueryParams(queryParams);
			if(this instanceof WorkFlowFact){
				WorkFlowFact cc = (WorkFlowFact)this;
				cc.toStringN();
				queryParams.put("flow_id",cc.getFlow_id());
			}
			ZteResponse response = orderServices.execBusiComp(busi);
			//response.setError_code("0");//测试时写死
			responses.put(action_id, response);
			if(response!=null){
				response.setRule_id(rule_id);
				response.setRule_name(rule_name);
				response.setExec_path(exec_path);
				logger.info("业务组件返回结果==========>["+rule.getRule_id()+"]"+rule.getRule_name()+"\t"+response.getError_code()+":"+response.getError_msg());
			}

			long end = System.currentTimeMillis();
			long time_num = end-start;
			//执行组件================
			this.exeAction(this,consts);//此处需要返回结果
			if(response==null){
				log = log(rule,EcsOrderConsts.RULE_EXE_STATUS_1, "执行失败",time_num);
			}else if(!"0".equals(response.getError_code())){
				int status = Integer.valueOf(StringUtil.isEmpty(response.getError_code())?"1":response.getError_code());
				String ss = response.getError_msg();
				if(ss!=null && ss.length()>50){
					ss = ss.substring(0,50);
				}
				log = log(rule,status, ss,time_num);
			}else{
				String ss = response.getError_msg();
				if(ss!=null && ss.length()>50){
					ss = ss.substring(0,50);
				}
				log = log(rule,EcsOrderConsts.RULE_EXE_STATUS_0, ss,time_num);
			}
		}catch(InvocationTargetException ex){
			ex.printStackTrace();
			long end = System.currentTimeMillis();
			long time_num = end-start;
			String ss = ex.getTargetException().getMessage();
			if(ss!=null && ss.length()>50){
				ss = ss.substring(0,50);
			}
			ZteResponse response = new ZteResponse();
			response.setError_code("1");
			response.setError_msg(ss);
			response.setRule_id(rule_id);
			response.setRule_name(rule_name);
			response.setExec_path(exec_path);
			responses.put(action_id, response);
			log = log(rule,1, ss,time_num);
		}catch(Exception ex){
			long end = System.currentTimeMillis();
			long time_num = end-start;
			ex.printStackTrace();
			ZteResponse response = new ZteResponse();
			response.setError_code("1");
			response.setRule_id(rule_id);
			response.setRule_name(rule_name);
			response.setExec_path(exec_path);
			String ss = ex.getMessage();
			if(ss!=null && ss.length()>50){
				ss = ss.substring(0,50);
			}
			response.setError_msg(ex.getMessage());
			responses.put(action_id, response);
			log = log(rule,1, ss,time_num);
		}
		//long end = System.currentTimeMillis();
//		logger.info("["+getRule().getRule_id()+getRule().getRule_name()+getRule().getExe_path()+"]业务执行时间:="+(end-aa));
	}
	
	/**
	 * 启动业务业务组件
	 * @param prequest
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws e 
	 */
//	public static BusiCompResponse  performBusiComp(BusiCompRequest prequest) throws Exception {
//		long begin =System.currentTimeMillis();
//		String enginePath =prequest.getEnginePath();
//		DefaultActionBeanDefine context = DefaultActionBeanDefine.getInstance();
//		String methodName = enginePath,version="1.0";
//		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
////		String group = "order";
////		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(null);
////		ServiceMethodHandler serviceMethodHandler = null;
////		if (context.isValidMethodVersion(enginePath, version)) {
////			serviceMethodHandler = context.getServiceMethodHandler(enginePath, version);
////			group = serviceMethodHandler.getServiceMethodDefinition().getMethodGroupTitle();
////		}
////		
////		//add by wui 缺省按local模式执行
////		if("local".equals(group)){
////			prequest.setApiMethodName("zte.net.ecsord.params.base.req.busirulerequest.local");
////		} else {
////			prequest.setApiMethodName("zte.net.ecsord.params.base.req.busirulerequest.order");
////		}
////		BusiCompResponse resp = client.execute(prequest, BusiCompResponse.class);
////		long end =System.currentTimeMillis();
////		
//		IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
//		BusiCompResponse resp = orderServices.execBusiRule(prequest);
////		if(serviceMethodHandler !=null)
////		logger.info("BusiCompPraser方法调用"+serviceMethodHandler.getServiceMethodDefinition().getMethodTitle()+methodName+"执行时间"+(end-begin));
//		return resp;
//	}
	
	
	public static <T> String beanToJson(T src){
		//过滤不需要转json的字段
		ExcludesProFilter filter = new ExcludesProFilter("hasExeRuleList","hasExeRuleLogs","responses","execute","rule_script","rule");
		SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
				SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
				SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
				SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
				SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
				SerializerFeature.WriteClassName
			};
		return JSON.toJSONString(src,filter,features);
	}
	
	public static <T> T jsonToBean(String json,Class clazz){
		return (T) JSON.parseObject(json, clazz);
	}
	public RuleExeLog log(PlanRule rule,int result,String result_msg,long time_num){
		//PlanRule rule = getRule();
		IAutoRule autoRuleImpl = SpringContextHolder.getBean("autoRuleImpl");
		RuleExeLog log = new RuleExeLog();
		String log_id = UUIDUtil.getUUID();//autoRuleImpl.getSeq("s_es_rule_exe_log");
		log.setTime_num(time_num);
		log.setLog_id(log_id);
		log.setRule_id(rule.getRule_id());
		log.setCreate_time("sysdate");
		log.setExe_result(result);
		log.setResult_msg(result_msg);
		log.setPlan_id(rule.getPlan_id());
		log.setObj_id(this.getObj_id());
		log.setFlow_inst_id(this.getTrace_flow_id());
		log.setEngine_path(rule.getExe_path());
		log.setChildren_error(0);
		String fact_class = this.getClass().getName();
		log.setFact_class(fact_class);
//		log.setReq_fact(beanToJson(this));
		ZteResponse resp = this.getRuleResponse(rule.getAction_id());
//		if(resp!=null)log.setResp_data(beanToJson(resp));
		autoRuleImpl.log(log);
		//规则执行记录返回值
		hasExeRuleLogs.add(log);
		this.setCurr_log(log);
		return log;
	}

	@JSONField(deserialize=true,serialize=false)
	public PlanRule getRule() {
		return threadRule.get();
	}
	public void setRule(PlanRule rule) {
		threadRule.set(rule);
		//执行规则返回值
		hasExeRuleList.add(rule);
	}

	@JSONField(deserialize=true,serialize=false)
	public Map<String, ZteResponse> getResponses() {
		return responses;
	}
//	private String  getTraceId(String rule_id){
//		String currTraceId = "";
//		try{
//			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
//			RuleConfigGetReq req = new RuleConfigGetReq();
//			req.setRule_id(rule_id);
//			RuleConfigGetResp resp = client.execute(req, RuleConfigGetResp.class);
//			if(resp.getRule() == null 
//					|| StringUtil.isEmpty(resp.getRule().getPlan_id())
//					|| StringUtil.isEmpty(resp.getRule().getTache_code()))
//				return currTraceId = "";
//			return resp.getRule().getTache_code();
//		}catch(Exception e){
//			e.printStackTrace();
//			return currTraceId;
//		}
//	}
	public boolean isExecute() {
		PlanRule rule = getRule();
		if(rule==null){
			return execute;
		}else if(!StringUtil.isEmpty(rule.getAction_id())){
			Boolean flag = rule_execute.get(rule.getAction_id());
			if(flag!=null){
				return flag;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public void setExecute(boolean execute) {
		this.execute = execute;
	}

	@JSONField(deserialize=true,serialize=false)
	public List<PlanRule> getHasExeRuleList() {
		return hasExeRuleList;
	}

	public void setHasExeRuleList(List<PlanRule> hasExeRuleList) {
		this.hasExeRuleList = hasExeRuleList;
	}

	@JSONField(deserialize=true,serialize=false)
	public List<RuleExeLog> getHasExeRuleLogs() {
		return hasExeRuleLogs;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

	public ZteRequest getRequest() {
		return request;
	}

	public void setRequest(ZteRequest request) {
		this.request = request;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setTrace_flow_id(String trace_flow_id) {
		this.trace_flow_id = trace_flow_id;
	}

	public RuleExeLog getCurr_log() {
		return curr_log;
	}

	public void setCurr_log(RuleExeLog curr_log) {
		this.curr_log = curr_log;
	}

	public String getDeal_from() {
		return deal_from;
	}

	public void setDeal_from(String deal_from) {
		this.deal_from = deal_from;
	}

	public String getDeal_type() {
		return deal_type;
	}

	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
	}

	public String getDeal_desc() {
		return deal_desc;
	}

	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}

	public Map<String, Object> getValueResponse() {
		return valueResponse;
	}

	public void setValueResponse(Map<String, Object> valueResponse) {
		this.valueResponse = valueResponse;
	}

	public String getCurrTraceId() {
		return currTraceId;
	}

	public void setCurrTraceId(String currTraceId) {
		this.currTraceId = currTraceId;
	}
}
