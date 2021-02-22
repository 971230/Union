package com.ztesoft.net.auto.rule.exe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import params.ZteResponse;

import com.powerise.ibss.util.RuleUtil;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.parser.IKExpressionUtil;
import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.rule.manager.service.IRuleManagerService;
import com.ztesoft.zsmart.hound.client.HoundAPI;
import com.ztesoft.zsmart.hound.client.HoundInvocation;
import commons.CommonTools;

/**
 * 规则处理类
 * 
 * @作者 MoChunrun
 * @创建日期 2014-9-10
 * @版本 V 1.0
 */
public class AutoRuleCaller implements IAutoRuleCaller {
	/**
	 * 并行
	 */
	public static final int RULE_REL_TYPE_0 = 0;
	/**
	 * 依赖
	 */
	public static final int RULE_REL_TYPE_1 = 1;
	/**
	 * 互斥
	 */
	public static final int RULE_REL_TYPE_2 = 2;
	/**
	 * 本级所有互斥
	 */
	public static final int RULE_REL_TYPE_3 = 3;

	public static boolean IS_ALLMUTEXRULE_USE_THREAD = true;// 全部互斥规则或方案是否走多线程
	protected IRuleManagerService iRuleManagerService;

	/*
	 * public static final ThreadLocal<Boolean> curr_local = new
	 * ThreadLocal<Boolean>(); public static final ThreadLocal<Boolean>
	 * all_local = new ThreadLocal<Boolean>(); public static final
	 * ThreadLocal<Boolean> check_auto_local = new ThreadLocal<Boolean>();
	 * public static final ThreadLocal<Integer> error_type = new
	 * ThreadLocal<Integer>(); public static final ThreadLocal<AtomicInteger>
	 * exception_couter = new ThreadLocal<AtomicInteger>(); public static final
	 * ThreadLocal<Boolean> check_curr_relyon_rule = new ThreadLocal<Boolean>();
	 * public static final ThreadLocal<Boolean> check_all_relyon_rule = new
	 * ThreadLocal<Boolean>(); //全部互拆空跑不算成功，其它的算成功 public static final
	 * ThreadLocal<Boolean> ALLMUTEXRULE = new ThreadLocal<Boolean>(); public
	 * static final ThreadLocal<Integer> IS_AUTO = new ThreadLocal<Integer>();
	 */

	// public static final ThreadLocal<String> CURR_RULE_ID = new
	// ThreadLocal<String>();//手动执地当前规则ID

	// public static final ThreadLocal<Map<String,RuleExeLog>> exe_logs = new
	// ThreadLocal<Map<String,RuleExeLog>>();

	protected IAutoRule autoRuleImpl;

	static int time = 60 * 24 * 60 * 5;// 缺省缓存20天,memcache最大有效期是30天
	private static INetCache cache = null;

	static {
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}

	public static int space = 7701;

	/*
	 * public void clearThreadLocal(){ curr_local.remove(); all_local.remove();
	 * check_auto_local.remove(); error_type.remove();
	 * exception_couter.remove(); check_curr_relyon_rule.remove();
	 * check_all_relyon_rule.remove(); ALLMUTEXRULE.remove();
	 * //exe_logs.remove(); //CURR_RULE_ID.remove(); }
	 */

	/**
	 * 添加执行记录到缓存
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-6
	 * @param plan_id
	 * @param rule_id
	 * @param obj_id
	 */
	// public static void addRuleExecuteLog(RuleExeLog log){
	// String key = log.getPlan_id()+log.getRule_id()+log.getObj_id();
	// Map<String,RuleExeLog> map = AutoRuleCaller.exe_logs.get();
	// if(map==null){
	// map = new HashMap<String,RuleExeLog>();
	// map.put(key, log);
	// AutoRuleCaller.exe_logs.set(map);
	// }else{
	// map.put(key, log);
	// }
	// }
	/**
	 * 获取执行日志
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-6
	 * @param plan_id
	 * @param rule_id
	 * @param obj_id
	 * @return
	 */
	@Override
	public RuleExeLog getRuleExecuteLog(String plan_id, String rule_id, String obj_id) {
		// long aa = System.currentTimeMillis();
		RuleExeLog log = autoRuleImpl.getRuleExecuteLog(plan_id, rule_id, obj_id);
		;
		/*
		 * String key = plan_id+rule_id+obj_id; Map<String,RuleExeLog> map =
		 * AutoRuleCaller.exe_logs.get(); if(map==null){ map = new
		 * HashMap<String,RuleExeLog>(); log =
		 * autoRuleImpl.getRuleExecuteLog(plan_id,rule_id, obj_id);
		 * if(log!=null)map.put(key, log); AutoRuleCaller.exe_logs.set(map);
		 * }else{ log = map.get(key); if(log==null){ log =
		 * autoRuleImpl.getRuleExecuteLog(plan_id,rule_id, obj_id);
		 * if(log!=null)map.put(key, log); } }
		 */
		// long end = System.currentTimeMillis();
		// logger.info(end-aa);
		return log;
	}

	/**
	 * 判断第一个执行规则是否执行依懒规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22
	 * @return
	 */
	/*
	 * public boolean isCheckCurrRelyonRule(){ Boolean flag =
	 * check_curr_relyon_rule.get(); if(flag == null){
	 * check_curr_relyon_rule.set(true); return true; }
	 * if(!flag)check_curr_relyon_rule.set(true); return flag; }
	 */

	/**
	 * 判断是否检查依赖规则是否执行
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22
	 * @return
	 */
	/*
	 * public boolean isCheckAllRelyOnRule(){ Boolean flag =
	 * check_all_relyon_rule.get(); if(flag==null)return true; return flag; }
	 */

	/**
	 * 获取异常计数器数量
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22
	 * @return
	 */
	/*
	 * public int getExceptionCount(){ AtomicInteger ai =
	 * exception_couter.get(); if(ai==null){ ai = new AtomicInteger();
	 * exception_couter.set(ai); } return ai.incrementAndGet(); }
	 */
	/**
	 * 清空异党计数器
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22
	 */
	/*
	 * public void clearExceptionCounter(){ AtomicInteger ai =
	 * exception_couter.get(); if(ai!=null)ai.set(0); }
	 */

	/**
	 * 获取规则错误类型 1普通错误 2手动规则没有执行
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22
	 * @return
	 */
	/*
	 * public int getErrorType(){ Integer error = error_type.get();
	 * if(error==null)return EcsOrderConsts.RULE_EXE_STATUS_1;
	 * error_type.set(EcsOrderConsts.RULE_EXE_STATUS_1); return error; }
	 */

	/**
	 * 是否强制执行第一个规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-18
	 * @return
	 */
	/*
	 * public boolean isReCurrRule(){ Boolean flag =
	 * AutoRuleCaller.curr_local.get(); if(flag==null) return false;
	 * //如果调用的时后设置为true只有第一个规则执行才会返回true
	 * if(flag)AutoRuleCaller.curr_local.set(false); return flag; }
	 */

	/**
	 * 是否强制执行所有规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-18
	 * @return
	 */
	/*
	 * public boolean isAllRuleExe(){ //Boolean flag =
	 * AutoRuleCaller.all_local.get(); //if(flag==null)return false; //return
	 * flag; return false; }
	 */

	/**
	 * 是否检查手动规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-18
	 * @return
	 */
	/*
	 * public boolean checkAutoExe(){ Boolean flag =
	 * AutoRuleCaller.check_auto_local.get(); if(flag==null) return false;
	 * return flag; }
	 */

	/*
	 * public int isAuto(){ Integer isAuto = AutoRuleCaller.IS_AUTO.get();
	 * if(isAuto==null){ isAuto = 0; } return isAuto; }
	 */

	@Override
	public void exeCataloguePlan(String catalogue_id, AutoFact fact, boolean deleteLogs, Map params) throws Exception {

		List<Plan> planList = autoRuleImpl.queryPlanByCatalogueId(catalogue_id);
		String[] plan_ids = new String[planList.size()];
		for (int i = 0; i < planList.size(); i++) {
			Plan plan = planList.get(i);
			plan_ids[i] = plan.getPlan_id();
		}
		autoRuleImpl.deleteRuleExeLogs(plan_ids, null, fact.getObj_id());
		if (planList != null && planList.size() > 0) {
			if (true) {// IS_ALLMUTEXRULE_USE_THREAD
				for (Plan p : planList) {
					if (autoRuleImpl.isPlanHasRuleExecute(catalogue_id, p.getPlan_id(), fact.getObj_id())) {
						// this.exePlan(p.getPlan_id(), fact, false, false, 0);
						break;
					} else {
						RuleThreadStatus threadStatus = new RuleThreadStatus();
						threadStatus.setParams(params);
						threadStatus.setAllmutexrule(true);
						// ALLMUTEXRULE.set(true);
						this.exePlan(p.getPlan_id(), fact, false, false, 0, false, threadStatus, params);
						threadStatus.setAllmutexrule(false);
						// ALLMUTEXRULE.remove();
					}
				}
			} else {
				// 多线程执行方案规则
				RuleThreadStatus threadStatus = new RuleThreadStatus();
				threadStatus.setParams(params);
				RuleFutureExecutor.execPlanRules(planList, fact, this, autoRuleImpl, threadStatus);
			}
		}

	}

	/**
	 * reCurrRule 强制执行第一个规则，不用考虑后续规则 executeAll 暂不考虑 threadStatus 入口参数
	 */
	@Override
	// @Transactional(propagation = Propagation.REQUIRED)
	public void exePlan(String plan_id, AutoFact fact, boolean reCurrRule, boolean executeAll, int auto_exe,
			boolean deleteLogs, RuleThreadStatus threadStatus, Map params) throws ApiBusiException {
		// AutoRuleImpl._CACHE_RULE=true;
//		String reqStr = "obj_id=" + fact.getObj_id() + ",plan_id=" + plan_id;
//		HoundInvocation planInvocation = HoundAPI.beginLocalInvocation(this.getClass().getName() + "-exePlan", reqStr);
		if (deleteLogs) {
			String[] plan_ids = new String[] { plan_id };
			autoRuleImpl.deleteRuleExeLogs(plan_ids, null, fact.getObj_id());
		}
		if (threadStatus == null)
			threadStatus = new RuleThreadStatus();
		threadStatus.setCurr_local(reCurrRule);
		threadStatus.setAll_local(executeAll);
		threadStatus.setIs_auto(auto_exe);
		threadStatus.setParams(params);
		// AutoRuleCaller.curr_local.set(reCurrRule);
		// AutoRuleCaller.all_local.set(executeAll);
		// AutoRuleCaller.IS_AUTO.set(auto_exe);
		Plan plan = autoRuleImpl.getPlan(plan_id);
		if (plan == null)
			return;
		List<PlanRule> ruleList = autoRuleImpl.listPlanRule(plan_id, auto_exe);
		// add by wui规则过滤
		ruleList = iRuleManagerService.filterRuleByAttrCode(ruleList, plan_id, fact.getObj_id());

		long aa = System.currentTimeMillis();

		exeRules(ruleList, fact, false, threadStatus);

		if (StringUtils.isEmpty(plan_id) && !ListUtil.isEmpty(ruleList) & ruleList.size() > 0) {
			PlanRule prule = ruleList.get(0);
			plan_id = prule.getPlan_id();
		}

		putExecedPlanId(fact.getObj_id(), plan_id);

		// long end = System.currentTimeMillis();
		// if("201410091613000142".equals(plan_id))
		// logger.info("exePlan=============>"+plan_id+"==="+(end-aa));
		// clearThreadLocal();
//		planInvocation.finish(plan_id);

	}

	@Override
	// @Transactional(propagation = Propagation.REQUIRED)
	public void exePlanRule(String plan_id, String rule_id, AutoFact fact, boolean reCurrRule, boolean executeAll,
			boolean isExeAfterPeerRule, boolean isExeParentsPeerAfRules, boolean checkCurrRelyOnRule,
			boolean checkAllRelyOnRule, Map params) throws ApiBusiException {

		// AutoRuleImpl._CACHE_RULE=false;
		RuleThreadStatus threadStatus = new RuleThreadStatus();
		threadStatus.setParams(params);
		threadStatus.setCurr_local(reCurrRule);
		threadStatus.setAll_local(executeAll);
		// 是否执行依赖规则，强制执行特定规则，则需要设置值为false
		threadStatus.setCheck_curr_relyon_rule(checkCurrRelyOnRule);
		threadStatus.setCheck_all_relyon_rule(checkAllRelyOnRule);
		threadStatus.setPlan_exe(false);
		// AutoRuleCaller.curr_local.set(reCurrRule);
		// AutoRuleCaller.all_local.set(executeAll);
		// AutoRuleCaller.check_curr_relyon_rule.set(checkCurrRelyOnRule);
		// AutoRuleCaller.check_all_relyon_rule.set(checkAllRelyOnRule);
		// AutoRuleCaller.CURR_RULE_ID.set(rule_id);
		PlanRule rule = autoRuleImpl.getPlanRule(rule_id, plan_id, EcsOrderConsts.RULE_EXE_ALL);
		if (rule == null)
			return;
		List<PlanRule> list = new ArrayList<PlanRule>();
		list.add(rule);
		if (rule != null && isExeAfterPeerRule) {
			// 如果住下执行是否需要把袓先的所有规则都需要执行？？？ 如果是手动规则又如何处理？？查询同级执行规则
			int priority = rule.getPriority() == null ? 0 : rule.getPriority().intValue();
			List<PlanRule> afList = autoRuleImpl.queryPeerPlanRule(rule.getPlan_id(), rule.getPid(), priority,
					EcsOrderConsts.RULE_EXE_0);
			if (afList != null && afList.size() > 0)
				list.addAll(afList);
		}
		// 查询父级规则
		if (rule != null && isExeParentsPeerAfRules) {
			List<PlanRule> pRules = this.listParentRules(rule.getPlan_id(), rule.getPid(), null);
			if (pRules != null && pRules.size() > 0) {
				for (PlanRule pRule : pRules) {
					int priority = pRule.getPriority() == null ? 0 : pRule.getPriority().intValue();
					List<PlanRule> afList = autoRuleImpl.queryPeerPlanRule(rule.getPlan_id(), pRule.getPid(), priority,
							EcsOrderConsts.RULE_EXE_0);
					if (afList != null && afList.size() > 0)
						list.addAll(afList);
				}
			}
		}
		// add by wui规则过滤
		list = iRuleManagerService.filterRuleByAttrCode(list, plan_id, fact.getObj_id());
		exeRules(list, fact, true, threadStatus);

		if (StringUtils.isEmpty(plan_id) && !ListUtil.isEmpty(list) & list.size() > 0) {
			PlanRule prule = list.get(0);
			plan_id = prule.getPlan_id();
		}
		putExecedPlanId(fact.getObj_id(), plan_id);

		// clearThreadLocal();
	}

	@Override
	// @Transactional(propagation = Propagation.REQUIRED)
	public void exePlanRule(String log_id, AutoFact fact, boolean reCurrRule, boolean executeAll,
			boolean isExeAfterPeerRule, boolean isExeParentsPeerAfRules, boolean checkCurrRelyOnRule,
			boolean checkAllRelyOnRule, Map params) throws ApiBusiException {

		HoundInvocation logInvocation = HoundAPI.beginLocalInvocation(
				this.getClass().getName() + "-exePlanRule:getRuleExeLog",
				"obj_id=" + fact.getObj_id() + ",log_id=" + log_id);
		RuleExeLog log = autoRuleImpl.getRuleExeLog(fact.getObj_id(), log_id);
		if (log == null)
			CommonTools.addBusiError("1", "没找到规则执行日志");
		logInvocation.finish(log);

		HoundInvocation ruleInvocation = HoundAPI.beginLocalInvocation(
				this.getClass().getName() + "-exePlanRule:exePlanRule",
				"obj_id=" + fact.getObj_id() + ",plan_id=" + log.getPlan_id() + ",rule_id=" + log.getRule_id());
		exePlanRule(log.getPlan_id(), log.getRule_id(), fact, reCurrRule, executeAll, isExeAfterPeerRule,
				isExeParentsPeerAfRules, checkCurrRelyOnRule, checkAllRelyOnRule, params);

		ruleInvocation.finish(this.getClass().getName() + "-exePlanRule");

	}

	/**
	 * 执行规则 如果有依赖规则先执行完所有依懒的规则再执行规则本身
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-11
	 * @param ruleList
	 * @param fact
	 * @param executeAll
	 * @throws Exception
	 */
	static String cache_prefix = "EXECED_PLAN_ID_";

	private static boolean isExecedPlanId(String order_id, String plan_id) {
		String execedPlansStr = (String) cache.get(space, cache_prefix + order_id);
		// 为空,则数据库查询
		if (StringUtil.isEmpty(execedPlansStr)) {
			String querySql = " select a.plan_id from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) a where a.obj_id=?";
			IDaoSupport daoSupport = SpringContextHolder.getBean("baseDaoSupport");
			//****************** add by qin.yingxiong at 2016/10/20 start *******************
			boolean useOrgTable = RuleUtil.useOrgTable(order_id);
			if(useOrgTable) {
				querySql = RuleUtil.replaceTableNameForOrgBySql(querySql);
			}
			//****************** add by qin.yingxiong at 2016/10/20 end *******************
			List<Map> execedPlans = daoSupport.queryForList(querySql, order_id);
			StringBuffer planIdBuffer = new StringBuffer("plan_id");
			if (!ListUtil.isEmpty(execedPlans)) {
				for (Map execedPlan : execedPlans) {
					planIdBuffer.append(execedPlan.get("plan_id")).append(",");
				}
			}
			cache.set(space, cache_prefix + order_id, planIdBuffer.toString(), time);
			execedPlansStr = planIdBuffer.toString();
		}
		if (!(execedPlansStr.indexOf(plan_id) > -1))
			return false;
		return true;
	}

	/**
	 * 设置规则已执行
	 * 
	 * @param order_id
	 * @param plan_id
	 */
	private static void putExecedPlanId(String order_id, String plan_id) {
		String execedPlansStr = (String) cache.get(space, cache_prefix + order_id);
		// 为空,则数据库查询
		if (StringUtil.isEmpty(execedPlansStr))
			execedPlansStr = "";
		if (StringUtil.isEmpty(plan_id))
			return;
		StringBuffer planIdBuffer = new StringBuffer(execedPlansStr);
		if (!StringUtil.isEmpty(planIdBuffer.toString()) && !(planIdBuffer.toString().indexOf(plan_id) > -1)) {
			planIdBuffer.append(plan_id).append(",");
			cache.set(space, cache_prefix + order_id, planIdBuffer.toString(), time);
		}
	}

	private boolean exeRules(List<PlanRule> ruleList, AutoFact fact, boolean isRuleExe, RuleThreadStatus threadStatus)
			throws ApiBusiException {

		if (null == ruleList || ruleList.size()==0)
			return false;

//		HoundInvocation rulesInvocation = HoundAPI.beginLocalInvocation(this.getClass().getName() + "-exeRules",
//				"ruleSize:" + ruleList.size());
		boolean run_result = true;
		for (PlanRule rule : ruleList) {

//			HoundInvocation ruleInvocation = HoundAPI.beginLocalInvocation(this.getClass().getName() + "-exeRule",rule);
			if (null != rule.getRel_type() && rule.getRel_type() == RULE_REL_TYPE_3) {
				// 本级全部互斥
				if (isExecedPlanId(fact.getObj_id(), rule.getPlan_id())) { // add
																			// by
																			// wui判断规则是否首次执行，首次执行则不需要做日志查询,否则做日志查询
					if (!autoRuleImpl.hasAllMutexRuleExe(rule.getPid(), rule.getPlan_id(), fact.getObj_id(),
							rule.getRule_id())) {
						RuleExeLog log = this.getRuleExecuteLog(rule.getPlan_id(), rule.getRule_id(), fact.getObj_id());
						run_result = exeRuleTree(rule, fact, log, isRuleExe, threadStatus);
						if (threadStatus.isPlan_exe()) {
							// 如果不是单个规则调用执行成功侧跳出全部互斥
							if (run_result)
								break;
						}
					} else {
						// 不执行当前规则
						run_result = false;
					}
				} else {
					RuleExeLog log = null;// this.getRuleExecuteLog(rule.getPlan_id(),rule.getRule_id(),
											// fact.getObj_id());
					run_result = exeRuleTree(rule, fact, log, isRuleExe, threadStatus);
					if (threadStatus.isPlan_exe()) {
						// 如果不是单个规则调用执行成功侧跳出全部互斥
						if (run_result)
							break;
					}
				}

			} else if (isExecedPlanId(fact.getObj_id(), rule.getPlan_id())
					&& autoRuleImpl.hasExeMutexRule(rule.getRule_id(), rule.getPlan_id(), fact.getObj_id())) {
				// 如果有互斥系,并且互斥系关系已执行侧不执行规则
				run_result = false;
			} else if (rule.getRel_type() != null && rule.getRel_type() == RULE_REL_TYPE_1) {
				// 执行依赖关系
				run_result = exeRelyRule(rule, fact, isRuleExe, threadStatus);
			} else {
				// 执行规则树
				RuleExeLog log = this.getRuleExecuteLog(rule.getPlan_id(), rule.getRule_id(), fact.getObj_id());
				boolean flag = exeRuleTree(rule, fact, log, isRuleExe, threadStatus);
				if (!flag)
					run_result = false;
			}
//			ruleInvocation.finish(run_result);

		}
		// 如果没有可执行规侧视为规则执行成功，无需执行规则
//		rulesInvocation.finish(run_result);
		return run_result;
	}

	private boolean exeRelyRule(PlanRule rule, AutoFact fact, boolean isRuleExe, RuleThreadStatus threadStatus)
			throws ApiBusiException {
		boolean run_result = true;
		// 依赖关系
		boolean isAllRelyOnRuleExecuteSuccess = true;
		RuleExeLog firstlog = this.getRuleExecuteLog(rule.getPlan_id(), rule.getRule_id(), fact.getObj_id());
		if (firstlog != null && firstlog.getExe_result() != null
				&& firstlog.getExe_result().intValue() == EcsOrderConsts.RULE_EXE_STATUS_0
				&& firstlog.getChildren_error() != null
				&& firstlog.getChildren_error().intValue() == EcsOrderConsts.RULE_EXE_STATUS_0) {
			// 如果当前规则已经执行成功成 直接返回true
			return true;
		}
		// 判断是否需要执行依赖规则
		// if(isCheckCurrRelyonRule() && isCheckAllRelyOnRule()){
		if (threadStatus.isCheck_curr_relyon_rule() && threadStatus.isCheck_all_relyon_rule()) {
			// 查询当前规则所有依赖的规则
			List<PlanRule> relyOnRuleList = autoRuleImpl.queryRuleRelyOnRule(rule.getPlan_id(), rule.getRule_id(),
					EcsOrderConsts.RULE_EXE_ALL);
			for (PlanRule relyOnRule : relyOnRuleList) {
				RuleExeLog rlog = this.getRuleExecuteLog(relyOnRule.getPlan_id(), relyOnRule.getRule_id(),
						fact.getObj_id());
				if (rlog != null && rlog.getExe_result() != null
						&& rlog.getExe_result().intValue() == EcsOrderConsts.RULE_EXE_STATUS_0
						&& rlog.getChildren_error() != null
						&& rlog.getChildren_error().intValue() == EcsOrderConsts.RULE_EXE_STATUS_0) {
					// 依赖规则已执行成功，不需要再住下执行规则权
					continue;
				}
				// 如果被依赖的规则还依赖别的规则侧执行完所有被依赖的规则
				if (relyOnRule.getRel_type() != null && relyOnRule.getRel_type().intValue() == RULE_REL_TYPE_1) {
					// 查询所有被依赖的规则树
					List<PlanRule> pRuleList = listRelyOnRule(relyOnRule.getPlan_id(), relyOnRule.getRelyon_rule_id(),
							null);
					// 执行所有被依赖规则
					boolean isAllRelyOnRuleExecuteSuccess2 = true;
					for (int i = pRuleList.size() - 1; i >= 0; i--) {
						PlanRule p_rule = pRuleList.get(i);
						RuleExeLog log = this.getRuleExecuteLog(p_rule.getPlan_id(), p_rule.getRule_id(),
								fact.getObj_id());
						if (log != null && log.getExe_result() != null
								&& log.getExe_result().intValue() == EcsOrderConsts.RULE_EXE_STATUS_0
								&& log.getChildren_error() != null
								&& log.getChildren_error().intValue() == EcsOrderConsts.RULE_EXE_STATUS_0) {
							// 依赖规则已执行成功，不需要再住下执行规则权
							continue;
						}
						if (!canExeRelyOnRule(p_rule, fact, log, threadStatus)) {
							isAllRelyOnRuleExecuteSuccess2 = false;
							break;
						}
						boolean flag = exeRuleTree(p_rule, fact, log, isRuleExe, threadStatus);// 执行规则树
						if (!flag) {
							isAllRelyOnRuleExecuteSuccess2 = false;
							break;// 如果被依懒规则执行失败侧跳出规则执行,进入下一个规则执行
						}
					}
					if (isAllRelyOnRuleExecuteSuccess2) {
						if (!canExeRelyOnRule(relyOnRule, fact, rlog, threadStatus)) {
							isAllRelyOnRuleExecuteSuccess = false;
							break;
						}
						boolean flag = exeRuleTree(relyOnRule, fact, rlog, isRuleExe, threadStatus);// 执行规则树
						if (!flag)
							isAllRelyOnRuleExecuteSuccess = false;
					} else {
						// 依赖规则没有执行成功，不执行当前规则
						isAllRelyOnRuleExecuteSuccess = false;
					}
				} else {

					if (!canExeRelyOnRule(relyOnRule, fact, rlog, threadStatus)) {
						isAllRelyOnRuleExecuteSuccess = false;
						break;
					}
					boolean flag = exeRuleTree(relyOnRule, fact, rlog, isRuleExe, threadStatus);// 执行规则树
					if (!flag)
						isAllRelyOnRuleExecuteSuccess = false;
				}
			}
		}

		// 当所有依赖规则都已经成功执行后执行当前规则
		if (isAllRelyOnRuleExecuteSuccess) {
			boolean flag = exeRuleTree(rule, fact, firstlog, isRuleExe, threadStatus);// 执行规则树
			if (!flag)
				run_result = false;
		} else {
			// 依赖规则执行失败，不执行当前规则，视为规则执行失败。
			run_result = false;
		}
		return run_result;
	}

	private boolean canExeRelyOnRule(PlanRule rule, AutoFact fact, RuleExeLog log, RuleThreadStatus threadStatus)
			throws ApiBusiException {
		// if(autoRuleImpl.hasExeMutexRule(rule.getRule_id(), rule.getPlan_id(),
		// fact.getObj_id())){
		// //如果已经有互斥关系的规则已经执行侧不执行当前规则
		// return false;
		// }
		// 如果是-1则手动规则也执行
		// if(isAuto()!=EcsOrderConsts.RULE_EXE_ALL && rule.getAuto_exe()!=null
		// && rule.getAuto_exe().intValue()==EcsOrderConsts.RULE_EXE_1){
		if (threadStatus.getIs_auto() != EcsOrderConsts.RULE_EXE_ALL && rule.getAuto_exe() != null
				&& rule.getAuto_exe().intValue() == EcsOrderConsts.RULE_EXE_1) {
			// 如果是手动执行的规则侧并没有执行成功不执行
			/*
			 * if(rule.getRule_id().equals(AutoRuleCaller.CURR_RULE_ID.get())){
			 * //如果是手动执行规则侧，并执行的规则ID＝＝调用的规则ID侧执行 return true; }else
			 */
			if (log == null) {
				return false;
			} else if (log.getExe_result() != null
					&& log.getExe_result().intValue() != EcsOrderConsts.RULE_EXE_STATUS_0) {
				return false;
			}
		}
		if (log != null && log.getChildren_error() != null
				&& log.getChildren_error().intValue() != EcsOrderConsts.RULE_EXE_STATUS_0) {
			// 如果有子规则执行失败侧规则执行失败。
			return false;
		}
		return true;
	}

	public static void addCache(String key, Serializable serial, int time_out) {
		INetCache cache = CacheFactory.getCacheByType("");
		cache.set(Const.CACHE_SPACE, key, serial, time_out);
	}

	public static void remove(String key) {
		INetCache cache = CacheFactory.getCacheByType("");
		cache.delete(Const.CACHE_SPACE, key);
	}

	public static final Object _KEY = new Object();

	/**
	 * 执行规则树，执行本身与所有子规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-12
	 * @param rule
	 * @param fact
	 * @param executeAll
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean exeRuleTree(PlanRule rule, AutoFact fact, RuleExeLog log, boolean isRuleExe,
			RuleThreadStatus threadStatus) throws ApiBusiException {

		fact.setRule(rule);
//		String key = fact.getObj_id() + "_" + rule.getPlan_id() + "_" + rule.getRule_id();
//临时屏蔽调
//		if (!StringUtil.isEmpty(fact.getObj_id())) {
//			try {
//				synchronized (_KEY) {
//					String is_running = CacheUtils.getCache(key);
//					if (!StringUtil.isEmpty(is_running)
//							&& !EcsOrderConsts.ACTION_URL_MATCH_PLAN.equals(rule.getPlan_id())) {
//						throw new ApiBusiException("规则正在运行中,key:" + key + "执行时间:" + DateUtil.getTime2());
//					} else {
//						addCache(key, "yes", 2);
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		// 已执行过的规则=============父规则执行成功，子规则执行失败或者未执行,非第一次进入,设置为true则不执行当前规则子规则。
		// if(!isReCurrRule()){
		if (!threadStatus.isCurr_local()) {
			if (log != null && log.getExe_result() == EcsOrderConsts.RULE_EXE_STATUS_0
					&& log.getChildren_error() != null
					&& log.getChildren_error().intValue() != EcsOrderConsts.RULE_EXE_STATUS_0) {
				// 如果子规则没有执行成功 执行子规则
				boolean flag = exeChildrenRule(rule, fact, isRuleExe, threadStatus);
				// 清空异常计数器
				threadStatus.clearExceptionCounter();
				// clearExceptionCounter();
				return flag;
			} else if (log != null && log.getExe_result() == 0
					&& threadStatus.isAll_local()/* !isAllRuleExe() */) { // 不考虑进入
				// 执行子规则
				boolean flag = exeChildrenRule(rule, fact, isRuleExe, threadStatus);
				return flag;// 如果已经成功执行侧返回true
			} else if (/* checkAutoExe() */threadStatus.isCheck_auto_local()
					&& /* isAuto() */threadStatus.getIs_auto() != EcsOrderConsts.RULE_EXE_ALL
					&& rule.getAuto_exe() != null && rule.getAuto_exe().intValue() == EcsOrderConsts.RULE_EXE_1) {
				// AutoRuleCaller.error_type.set(EcsOrderConsts.RULE_EXE_STATUS_2);
				threadStatus.setError_type(EcsOrderConsts.RULE_EXE_STATUS_2);
				// 如果规则是手动执行，并且不是手动执行规则 不执行规则并返回执行失败
//				remove(key);
				if (fact != null && rule != null && !StringUtil.isEmpty(rule.getPlan_id()))
					putExecedPlanId(fact.getObj_id(), rule.getPlan_id());
				throw new ApiBusiException("[" + rule.getRule_id() + "][" + rule.getRule_name() + "]手动规则没有执行");
			}
		}

		// 执行自身
		// 已执行过的规则=============
		// 如果是互斥关系的规则没有进入业务组件算执行失败，如果不是算执行成功
		if (null != rule.getRel_type() && (rule.getRel_type() == RULE_REL_TYPE_3 || rule.getRel_type() == RULE_REL_TYPE_2)) {
			// ALLMUTEXRULE.set(true);
			threadStatus.setAllmutexrule(true);
		}
		// 如果没有执行的再执行一次
		int flag = 0; // 0成功，1失败
		if (log == null || (log.getExe_result() != null
				&& log.getExe_result().intValue() != EcsOrderConsts.RULE_EXE_STATUS_0)) {
			// logger.info("执行规则：["+rule.getRule_id()+"]=======================logs:=="+(log==null?"还没执行":log.getExe_result()));
			flag = exeRule(rule, fact, threadStatus);
		}

		if (null != rule.getRel_type() && (rule.getRel_type() == RULE_REL_TYPE_3 || rule.getRel_type() == RULE_REL_TYPE_2)) {
			// ALLMUTEXRULE.remove();
			threadStatus.setAllmutexrule(false);
		}
		if (0 == flag) {
			// 执行了规则
			boolean cFlag = exeChildrenRule(rule, fact, isRuleExe, threadStatus);
			if (cFlag) {
				flag = EcsOrderConsts.RULE_EXE_STATUS_0;
			} else {
				flag = EcsOrderConsts.RULE_EXE_STATUS_1;
			}
			// 清空异常计数器
			threadStatus.clearExceptionCounter();
			// clearExceptionCounter();
		}
		// 如果规则执行失败 中段执行其它规则
		if (1 == flag) {
//			remove(key);
			if (fact != null && rule != null && !StringUtil.isEmpty(rule.getPlan_id()))
				putExecedPlanId(fact.getObj_id(), rule.getPlan_id());
			throw new ApiBusiException("[" + rule.getRule_id() + "][" + rule.getRule_name() + "]规则执行失败");
		}
		if ((rule.getAuto_exe() != null && rule.getAuto_exe().intValue() == EcsOrderConsts.RULE_EXE_1) || (log != null
				&& log.getExe_result() != null && log.getExe_result().intValue() != EcsOrderConsts.RULE_EXE_STATUS_0)) {
			// 如果是手动规则，或者是执行错误了的规则再次执行时修改标志为子规则错误的规则记录为子规则执行成功
			autoRuleImpl.updateParentRuleExeLogChildErrorInfo(rule.getPlan_id(), rule.getRule_id(), fact.getObj_id(),
					EcsOrderConsts.RULE_EXE_STATUS_0, "");
		}
//		remove(key);

		return (flag == EcsOrderConsts.RULE_EXE_STATUS_0);
	}

	private boolean exeChildrenRule(PlanRule rule, AutoFact fact, boolean isRuleExe, RuleThreadStatus threadStatus)
			throws ApiBusiException {
		// 执行规则树(子规则)
		// 查询子规则列表 包括手动执行规则
		List<PlanRule> childRuleList = autoRuleImpl.queryChildrenRule(rule.getPlan_id(), rule.getRule_id(),
				EcsOrderConsts.RULE_EXE_ALL);
		if (childRuleList != null && childRuleList.size() > 0) {
			// 执行子规则时设置检查手动规则为true
			// AutoRuleCaller.check_auto_local.set(true);
			threadStatus.setCheck_auto_local(true);
			try {
				boolean flag = exeRules(childRuleList, fact, isRuleExe, threadStatus);
				// AutoRuleCaller.check_auto_local.set(false);
				threadStatus.setCheck_auto_local(false);
				return flag;
			} catch (ApiBusiException ex) {
				throw ex;
			}
		} else {
			// 没有子规则 不需要执行，返回成功
			return true;
		}
	}

	@Override
	public int exeRule(String plan_id, String rule_id, AutoFact fact, RuleThreadStatus threadStatus) {
		PlanRule rule = autoRuleImpl.getPlanRule(rule_id, plan_id, EcsOrderConsts.RULE_EXE_ALL);
		return exeRule(rule, fact, threadStatus);
	}

	/*
	 * public boolean isALLMUTEXRULE(){ Boolean b = ALLMUTEXRULE.get();
	 * if(b==null){ return false; }else{ return b; } }
	 */

	/**
	 * 执行规则
	 */
	@Override
	public int exeRule(PlanRule rule, AutoFact fact, RuleThreadStatus threadStatus) {
		long start = System.currentTimeMillis();
		try {
			fact.setRule(rule);
			Map map = threadStatus.getParams();
			if (map != null) {
				String deal_from = (String) map.get("deal_from");
				String deal_type = (String) map.get("deal_type") == null ? Const.ORDER_HANDLER_TYPE_DEFAULT
						: map.get("deal_type").toString();
				;
				String deal_desc = (String) map.get("deal_desc") == null ? null : map.get("deal_desc").toString();
				fact.setDeal_from(deal_from);
				fact.setDeal_type(deal_type);
				fact.setDeal_desc(deal_desc);
			}
			// if("201410101591000060".equals(rule.getRule_id()))
			// logger.info("===============");
			// long aa = System.currentTimeMillis();
			// DroolsUtil.callRule(rule.getRule_id(),rule.getRule_script(),
			// rule.getResource_type(), fact);
			// DroolsUtil.callRuleByLess(rule.getRule_id(),rule.getRule_script(),
			// rule.getResource_type(), fact);
			IKExpressionUtil.createOrderRuleExpress(rule.getRule_id(), fact).express();
			long end = System.currentTimeMillis();
			long time_num = end - start;
			// logger.info("["+rule.getRule_id()+rule.getRule_name()+rule.getExe_path()+"]:"+"drool------------------------->"+(end-aa));
			ZteResponse resp = fact.getRuleResponse(rule.getAction_id());
			// 如果是全部互斥的算失败
			int status1 = EcsOrderConsts.RULE_EXE_STATUS_0;
			// boolean b = isALLMUTEXRULE();
			boolean b = threadStatus.isAllmutexrule();
			if (b) {
				// 规则已经执行，但条件不符合所以没有进入规则动作，如果是全部互斥的算失败,其它的认为是规则执行成功。
				status1 = EcsOrderConsts.RULE_EXE_STATUS_2;
			}

			if (!rule.isExecute()) {
				// 规则已经执行，但条件不符合所以没有进入规则动作，如果是全部互斥的算失败,其它的认为是规则执行成功。
				fact.log(rule, status1, "规则执行成功[没有进行业务处理]", time_num);
			}
			// ALLMUTEXRULE.remove();
			int result = EcsOrderConsts.RULE_EXE_STATUS_0;
			if (resp == null) {
				// 没执行动作
				if (b) {
					result = EcsOrderConsts.RULE_EXE_STATUS_2;
				} else {
					result = EcsOrderConsts.RULE_EXE_STATUS_0;
				}
			} else if (!"0".equals(resp.getError_code())) {
				List<PlanRule> pList = this.listParentRules(rule.getPlan_id(), rule.getPid(), null);
				// int status = getErrorType();
				int status = threadStatus.getError_type();
				for (PlanRule r : pList) {
					// 更新父规则执行日志子环节的错误信息
					autoRuleImpl.updateLogChildrenErrorInfo(r.getPlan_id(), r.getRule_id(), fact.getObj_id(), status,
							fact.getRule().getRule_id());
				}
				// 执行失败
				result = EcsOrderConsts.RULE_EXE_STATUS_1;
			} else {
				// 执行成功
				result = EcsOrderConsts.RULE_EXE_STATUS_0;
			}
			// RuleExeLog clog = fact.getCurr_log();
			// if(clog!=null)AutoRuleCaller.addRuleExecuteLog(clog);
			return result;
		} catch (Exception ex) {
			long end = System.currentTimeMillis();
			long time_num = end - start;
			ex.printStackTrace();
			if (!fact.isExecute()) {
				fact.log(rule, EcsOrderConsts.RULE_EXE_STATUS_1, "执行失败", time_num);
				// RuleExeLog clog = fact.getCurr_log();
				// if(clog!=null)AutoRuleCaller.addRuleExecuteLog(clog);
			}
			List<PlanRule> pList = this.listParentRules(rule.getPlan_id(), rule.getPid(), null);
			// int status = getErrorType();
			int status = threadStatus.getError_type();
			for (PlanRule r : pList) {
				// 更新父规则执行日志子环节的错误信息
				autoRuleImpl.updateLogChildrenErrorInfo(r.getPlan_id(), r.getRule_id(), fact.getObj_id(), status,
						fact.getRule().getRule_id());
			}
			// 执行失败
			return EcsOrderConsts.RULE_EXE_STATUS_1;
		}
	}

	/**
	 * 查询所有被依赖的规则树 返回的列表是倒序，处理的时后需要返过来执行
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10
	 * @param p_rule_id
	 * @param list
	 *            可传null 不传null会在list后面追加数据
	 * @return
	 * @throws Exception
	 */
	private List<PlanRule> listRelyOnRule(String plan_id, String p_rule_id, List<PlanRule> list)
			throws ApiBusiException {
		if (list == null)
			list = new ArrayList<PlanRule>();
		PlanRule p_rule = autoRuleImpl.getPlanRule(p_rule_id, plan_id, EcsOrderConsts.RULE_EXE_ALL);
		if (p_rule == null)
			return list;
		list.add(p_rule);
		if (p_rule.getRel_type() != null && p_rule.getRel_type() == RULE_REL_TYPE_1) {
			listRelyOnRule(plan_id, p_rule.getRelyon_rule_id(), list);
		}
		return list;
	}

	/**
	 * 查询方案的所有父规则
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-15
	 * @param plan_id
	 * @param pid
	 * @param list
	 *            可传null 不传null会在list后面追加数据
	 * @return
	 */
	private List<PlanRule> listParentRules(String plan_id, String rule_pid, List<PlanRule> list) {
		if (list == null)
			list = new ArrayList<PlanRule>();
		PlanRule p_rule = autoRuleImpl.getPlanRule(rule_pid, plan_id, EcsOrderConsts.RULE_EXE_0);
		if (p_rule == null)
			return list;
		list.add(p_rule);
		listParentRules(plan_id, p_rule.getPid(), list);
		return list;
	}

	public IAutoRule getAutoRuleImpl() {
		return autoRuleImpl;
	}

	public void setAutoRuleImpl(IAutoRule autoRuleImpl) {
		this.autoRuleImpl = autoRuleImpl;
	}

	public IRuleManagerService getiRuleManagerService() {
		return iRuleManagerService;
	}

	public void setiRuleManagerService(IRuleManagerService iRuleManagerService) {
		this.iRuleManagerService = iRuleManagerService;
	}

}
