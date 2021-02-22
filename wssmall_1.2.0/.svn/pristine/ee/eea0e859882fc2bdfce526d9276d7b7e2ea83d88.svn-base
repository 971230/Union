package zte.net.params.req;

import params.ZteRequest;
import zte.net.params.resp.CataloguePlanExeResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.framework.database.NotDbField;

public class CataloguePlanExeReq extends ZteRequest<CataloguePlanExeResp> {
	
	@ZteSoftCommentAnnotationParam(name="目录ID",type="String",isNecessary="Y",desc="目录ID")
	private String catalogue_id;
	@ZteSoftCommentAnnotationParam(name="规则实例数据",type="String",isNecessary="Y",desc="规则实例数据")
	private AutoFact fact;
	@ZteSoftCommentAnnotationParam(name="是否删除执行日志",type="String",isNecessary="Y",desc="是否删除执行日志")
	private boolean deleteLogs;
	@ZteSoftCommentAnnotationParam(name="处理来源",type="String",isNecessary="Y",desc="处理来源[page 界面处理 inf接口处理]")
	private String deal_from;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.ruleService.catalogue.plan.rules.tree.auto.exe";
	}

	public String getCatalogue_id() {
		return catalogue_id;
	}

	public void setCatalogue_id(String catalogue_id) {
		this.catalogue_id = catalogue_id;
	}

	public AutoFact getFact() {
		return fact;
	}

	public void setFact(AutoFact fact) {
		this.fact = fact;
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

}
