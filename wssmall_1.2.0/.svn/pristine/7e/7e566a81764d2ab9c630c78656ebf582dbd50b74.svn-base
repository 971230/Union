package zte.net.params.req;

import params.ZteError;
import params.ZteRequest;
import zte.net.params.resp.PlanRuleTreeExeResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

public class PlanRuleTreeExeReq extends ZteRequest<PlanRuleTreeExeResp> {

	@ZteSoftCommentAnnotationParam(name="方案ID",type="String",isNecessary="Y",desc="方案ID")
	private String plan_id;
	@ZteSoftCommentAnnotationParam(name="是否强制重新规则侧树的第一个规则",type="String",isNecessary="Y",desc="是否强制重新规则侧树的第一个规则：true 是 false 否")
	private boolean reCurrRule;
	@ZteSoftCommentAnnotationParam(name="规则类型",type="String",isNecessary="Y",desc="规则类型：-1 所有规则 0自动执行规则 1手动调用规则")
	private int auto_exe;
	@ZteSoftCommentAnnotationParam(name="规则实例数据",type="String",isNecessary="Y",desc="规则实例数据")
	private AutoFact fact;
	@ZteSoftCommentAnnotationParam(name="是否删除执行日志",type="String",isNecessary="Y",desc="是否删除执行日志")
	private boolean deleteLogs;
	@ZteSoftCommentAnnotationParam(name="处理来源",type="String",isNecessary="Y",desc="处理来源[page 界面处理 inf接口处理]")
	private String deal_from;
	
	private String deal_type;
	
	private String deal_desc;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(plan_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "plan_id不能为空"));
		if(fact==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "fact不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.plan.rules.tree.auto.exe";
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
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

	public int getAuto_exe() {
		return auto_exe;
	}

	public void setAuto_exe(int auto_exe) {
		this.auto_exe = auto_exe;
	}

	public boolean isDeleteLogs() {
		return deleteLogs;
	}

	public void setDeleteLogs(boolean deleteLogs) {
		this.deleteLogs = deleteLogs;
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
