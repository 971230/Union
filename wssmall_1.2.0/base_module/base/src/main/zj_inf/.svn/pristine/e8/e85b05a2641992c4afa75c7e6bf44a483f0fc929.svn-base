package zte.net.ecsord.params.pub.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class DropdownDataReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="字典编码",type="String",isNecessary="Y",desc="attrCode：字典编码")
	private String attrCode;

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(attrCode)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "attrCode编码不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.dcPublicService.dropDown.list";
	}

}
