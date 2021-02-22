package params.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class PartnerByIdReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="Y",desc="分销商ID")
	private String partnerId;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(partnerId))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "分销商ID不允许为空"));
	}
    
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.service.getPartnerById";
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	

}
