package zte.net.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.net.params.resp.RuleTreeExeResp;

public class RuleTreeExeReq extends ZteRequest<RuleTreeExeResp> {

	@ZteSoftCommentAnnotationParam(name="方案ID",type="String",isNecessary="Y",desc="方案ID")
	private String plan_id;
	@ZteSoftCommentAnnotationParam(name="规则ID",type="String",isNecessary="Y",desc="规则ID")
	private String rule_id;
	@ZteSoftCommentAnnotationParam(name="是否强制重新规则侧树的第一个规则",type="String",isNecessary="Y",desc="是否强制重新规则侧树的第一个规则：true 是 false 否 默认为false")
	private boolean reCurrRule;
	@ZteSoftCommentAnnotationParam(name="是否执行同级执行顺序在当前规则后面的规则",type="String",isNecessary="Y",desc="是否执行同级执行顺序在当前规则后面的规则[true是 false否 默认为true]")
	private boolean exePeerAfRules = true;
	@ZteSoftCommentAnnotationParam(name="是否执行父级执行顺序在父级规则后面的规则",type="String",isNecessary="Y",desc="是否执行父级执行顺序在父级规则后面的规则[true是 false否 默认为true]")
	private boolean exeParentsPeerAfRules = true;
	@ZteSoftCommentAnnotationParam(name="是否检查并执行当前规则的依赖规则",type="String",isNecessary="Y",desc="是否检查并执行当前规则的依赖规则[true 是 false 否 默认为false]")
	private boolean checkCurrRelyOnRule = false;
	@ZteSoftCommentAnnotationParam(name="是否检查并执行所有规则的依赖规则",type="String",isNecessary="Y",desc="是否检查并执行所有规则的依赖规则[true 是 false 否 默认为false]")
	private boolean checkAllRelyOnRule = false;
	@ZteSoftCommentAnnotationParam(name="规则实例数据",type="String",isNecessary="Y",desc="规则实例数据")
	private AutoFact fact;
	@ZteSoftCommentAnnotationParam(name="处理来源",type="String",isNecessary="Y",desc="处理来源[page 界面处理 inf接口处理]")
	private String deal_from;
	
	private String deal_type;
	
	private String deal_desc;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		//if(StringUtils.isEmpty(plan_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "plan_id不能为空"));
		if(StringUtils.isEmpty(rule_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "rule_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.plan.rule.tree.exe";
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public boolean isReCurrRule() {
		return reCurrRule;
	}

	public void setReCurrRule(boolean reCurrRule) {
		this.reCurrRule = reCurrRule;
	}

	public AutoFact getFact() {
		return fact;
	}

	public void setFact(AutoFact fact) {
		this.fact = fact;
	}

	public boolean isExePeerAfRules() {
		return exePeerAfRules;
	}

	public void setExePeerAfRules(boolean exePeerAfRules) {
		this.exePeerAfRules = exePeerAfRules;
	}

	public boolean isExeParentsPeerAfRules() {
		return exeParentsPeerAfRules;
	}

	public void setExeParentsPeerAfRules(boolean exeParentsPeerAfRules) {
		this.exeParentsPeerAfRules = exeParentsPeerAfRules;
	}

	public boolean isCheckCurrRelyOnRule() {
		return checkCurrRelyOnRule;
	}

	public void setCheckCurrRelyOnRule(boolean checkCurrRelyOnRule) {
		this.checkCurrRelyOnRule = checkCurrRelyOnRule;
	}

	public boolean isCheckAllRelyOnRule() {
		return checkAllRelyOnRule;
	}

	public void setCheckAllRelyOnRule(boolean checkAllRelyOnRule) {
		this.checkAllRelyOnRule = checkAllRelyOnRule;
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

}
