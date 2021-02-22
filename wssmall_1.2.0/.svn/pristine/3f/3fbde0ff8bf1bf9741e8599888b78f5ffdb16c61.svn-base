package params.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.app.base.core.model.PartnerShop;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class PartnerWdEditReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="分销商编号",type="String",isNecessary="Y",desc="分销商编号")
    private PartnerShop partnerShop;
	
	@ZteSoftCommentAnnotationParam(name="分销商店铺logo",type="String",isNecessary="Y",desc="分销商店铺logo")
    private Partner partner;
	
	@ZteSoftCommentAnnotationParam(name="分销商账户",type="String",isNecessary="Y",desc="分销商账户:account_code=p,account_name=分销商账户;只需送partner_id,account_id")
    private PartnerAccount primaryAccount;

	@ZteSoftCommentAnnotationParam(name="分销商子账户",type="String",isNecessary="Y",desc="分销商子账户:account_code=s,account_name=分销商分账户;只需送partner_id,account_id")
    private PartnerAccount secondaryAccount;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(partnerShop.getPartner_id()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "分销商编码不允许为空"));
//		if(StringUtils.isEmpty(partner.getPartner_id()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "分销商编码不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.service.editPartnerWd";
	}

	public PartnerShop getPartnerShop() {
		return partnerShop;
	}

	public void setPartnerShop(PartnerShop partnerShop) {
		this.partnerShop = partnerShop;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public PartnerAccount getPrimaryAccount() {
		return primaryAccount;
	}

	public void setPrimaryAccount(PartnerAccount primaryAccount) {
		this.primaryAccount = primaryAccount;
	}

	public PartnerAccount getSecondaryAccount() {
		return secondaryAccount;
	}

	public void setSecondaryAccount(PartnerAccount secondaryAccount) {
		this.secondaryAccount = secondaryAccount;
	}

}
