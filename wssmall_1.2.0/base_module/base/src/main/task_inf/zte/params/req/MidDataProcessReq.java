package zte.params.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class MidDataProcessReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="处理的中间表数据",type="String",isNecessary="Y",desc="处理的中间表数据，json格式。")
	private String json;
	@ZteSoftCommentAnnotationParam(name="来源系统",type="String",isNecessary="Y",desc="来源系统。")
	private String source_from;
	@ZteSoftCommentAnnotationParam(name="规则类型：import",type="String",isNecessary="Y",desc="规则类型：import。")
	private String exec_rule;
	@ZteSoftCommentAnnotationParam(name="中间数据标识",type="String",isNecessary="Y",desc="中间数据标识。")
	private String id;
	@ZteSoftCommentAnnotationParam(name="导入日志标识",type="String",isNecessary="Y",desc="导入日志标识。")
	private String log_id;
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getExec_rule() {
		return exec_rule;
	}

	public void setExec_rule(String exec_rule) {
		this.exec_rule = exec_rule;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(json))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "处理数据不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return null;
	}

}
