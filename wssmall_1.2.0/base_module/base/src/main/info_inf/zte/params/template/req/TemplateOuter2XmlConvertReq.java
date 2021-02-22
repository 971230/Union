package zte.params.template.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.template.resp.TemplateOuter2XmlConvertResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.Consts;
import commons.CommonNTools;

/**
 * 模板转出入参
 * @author xuefeng
 */
public class TemplateOuter2XmlConvertReq extends ZteRequest<TemplateOuter2XmlConvertResp>{
	@ZteSoftCommentAnnotationParam(name="模板编码",type="String",isNecessary="Y",desc="模板编码")
	private String template_code;
	
	@ZteSoftCommentAnnotationParam(name="版本号",type="String",isNecessary="Y",desc="版本号")
	private String template_version;
	
	@ZteSoftCommentAnnotationParam(name="实例ID",type="String",isNecessary="Y",desc="订单号")
	private String inst_id;
	
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(template_code))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "模板编码不能为空"));
		if(StringUtils.isEmpty(template_version))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "模板版本号不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return  "zte.net.iservice.template.tplOuter2XmlConvert";
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

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	
}
