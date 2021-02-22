package zte.net.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.net.params.resp.PlanRuleTreeQueryResp;

public class PlanRuleTreeQueryReq extends ZteRequest<PlanRuleTreeQueryResp> {
	
	@ZteSoftCommentAnnotationParam(name="方案ID",type="String",isNecessary="Y",desc="方案ID")
	private String plan_id;
	@ZteSoftCommentAnnotationParam(name="数据实例ID",type="String",isNecessary="Y",desc="数据实例ID[当实例ID不为空时会查询实例规则数据的执行记录]")
	private String obj_id;

	@ZteSoftCommentAnnotationParam(name="是否是查询历史表数据",type="String",isNecessary="N",desc="是否是查询历史表数据[默认为否]")
	private String is_his_order=EcsOrderConsts.IS_ORDER_HIS_NO;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(plan_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "plan_id不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.plan.rule.tree.query";
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	@NotDbField
	public String getIs_his_order() {
		return is_his_order;
	}

	public void setIs_his_order(String is_his_order) {
		this.is_his_order = is_his_order;
	}

}
