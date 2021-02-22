package params.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.app.base.core.model.PartnerShop;

public class PartnerByIdResp extends ZteResponse{
    private Partner partner;
    private PartnerShop partnerShop;
	@ZteSoftCommentAnnotationParam(name="分销商账户",type="String",isNecessary="Y",desc="分销商账户:account_code=p,account_name=分销商账户;只需送partner_id,account_id")
    private PartnerAccount primaryAccount;
	@ZteSoftCommentAnnotationParam(name="分销商子账户",type="String",isNecessary="Y",desc="分销商子账户:account_code=s,account_name=分销商分账户;只需送partner_id,account_id")
    private PartnerAccount secondaryAccount;

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public PartnerShop getPartnerShop() {
		return partnerShop;
	}

	public void setPartnerShop(PartnerShop partnerShop) {
		this.partnerShop = partnerShop;
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
