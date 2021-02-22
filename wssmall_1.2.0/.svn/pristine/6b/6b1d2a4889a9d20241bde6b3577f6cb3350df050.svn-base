package zte.params.template.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.template.resp.TemplateQryResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.Consts;
import commons.CommonNTools;
/**
 * 模板查询入参
 * @author xuefeng
 */
public class TemplateQryReq extends ZteRequest<TemplateQryResp>{
	@ZteSoftCommentAnnotationParam(name="模板编码",type="String",isNecessary="Y",desc="模板编码")
	private String template_code;
	
	@ZteSoftCommentAnnotationParam(name="版本号",type="String",isNecessary="Y",desc="版本号")
	private String template_version;
	
	@ZteSoftCommentAnnotationParam(name="模板类型",type="String",isNecessary="Y",desc="in:入,out:出")
	private String template_type;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(template_code))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "模板编码不能为空"));
		if(StringUtils.isEmpty(template_version))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "模板版本号不能为空"));
		if(StringUtils.isEmpty(template_type))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "模板类型不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "zte.net.iservice.template.qry";
	}
	
	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public String getTemplate_version() {
		return template_version;
	}

	public void setTemplate_version(String template_version) {
		this.template_version = template_version;
	}

	public String getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(String template_type) {
		this.template_type = template_type;
	}
	
}
