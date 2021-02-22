package zte.net.ecsord.params.pub.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class DcPublicExtListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="字典编码",type="String",isNecessary="Y",desc="stype：字典编码")
	private String stype;
	
	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(stype)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "stype字典编码不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.dcPublicService.dcPublicList.list";
	}

}
