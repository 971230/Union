package com.ztesoft.rule.core.exe.def;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.exe.IApplyPlan;
import com.ztesoft.rule.core.ext.IPlanCtrl;
import com.ztesoft.rule.core.module.cfg.BizPlan;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.util.Const;
import com.ztesoft.rule.core.util.LocalBeanFactory;

/**
 * 默认方案申请分配处理： 通过预占模式进行方案分配处理,处理流程：
 * 
 * 1.获取所有可处理方案，遍历每个方案，分别做一下处理 1.1 尝试去预占,如果不成功，则处理下一个 1.2 如果预占成功,判断方案控制配置信息; -
 * 配置信息验证不通过,则继续1.1 - 如果配置验证通过,，则进入 2操作
 * 
 * 2.加载方案相关的其他配置信息
 * 
 * 3.返回ConfigData
 * 
 * @author easonwu
 * @creation Dec 23, 2013
 * 
 */
public class DefApplyPlan implements IApplyPlan {

	private IRuleDBAccess ruleDBAccess;
	private IPlanCtrl planCtrl;// Plan控制

	@Override
	public ConfigData apply(long resId) {
		return loadTimingRuleConfigData(resId);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private ConfigData loadTimingRuleConfigData(long resId) {
		List<BizPlan> plans = ruleDBAccess.loadExecBizPlans();
		ConfigData data = null;
		for (BizPlan plan : plans) {
			if (ruleDBAccess.takeUpBizPlan(plan.getPlan_id(), plan
					.getStatus_cd())) {// 预占

				//验证
				if (Const.SQL.equals(plan.getCtrl_type())) {// sql验证
					if (!getPlanCtrl().valid(plan.getCtrl_val(), plan)) {// 不满足条件,则释放
						ruleDBAccess.releaseBizPlan(plan.getPlan_id(), plan
								.getStatus_cd());
						continue;
					}
				} else if (Const.JAVABEAN.equals(plan.getCtrl_type())) {// sql验证
					IPlanCtrl ctrl = LocalBeanFactory.createPlanCtrl(plan
							.getCtrl_val());
					if (!ctrl.valid(plan)) {// 不满足条件,则释放
						ruleDBAccess.releaseBizPlan(plan.getPlan_id(), plan
								.getStatus_cd());
						continue;
					}
				}

				//更新运行日志表
				Map pdata = new HashMap() ;
				pdata.put("res_id", String.valueOf(resId));
				pdata.put("plan_id", plan.getPlan_id());
				pdata.put("exec_cycle", plan.getExec_cycle());
				ruleDBAccess.insertPlanLog(pdata);
				
				// 配置数据设置
				data = new ConfigData();
				data.setBizPlan(plan);
				data.setRuleConfigs(ruleDBAccess.loadRuleConfigs(plan.getPlan_id()));
				data.setMdConfigs(ruleDBAccess.loadMidDataConfigs(plan.getPlan_id())  , plan);

				break;
			}
		}

		return data;
	}

	// 【注】提供默认实现
	public IPlanCtrl getPlanCtrl() {
		if (planCtrl == null)
			planCtrl = SpringContextHolder.getBean("defPlanCtrl");

		return planCtrl;
	}

	@Override
	public void setPlanCtrl(IPlanCtrl planCtrl) {
		this.planCtrl = planCtrl;
	}

	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}

	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}

}
