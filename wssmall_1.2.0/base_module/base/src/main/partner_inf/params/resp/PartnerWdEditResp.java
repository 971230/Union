package params.resp;

import com.ztesoft.net.app.base.core.model.PartnerShop;

import params.ZteResponse;

public class PartnerWdEditResp extends ZteResponse{
   private PartnerShop partnerShop;

public PartnerShop getPartnerShop() {
	return partnerShop;
}

public void setPartnerShop(PartnerShop partnerShop) {
	this.partnerShop = partnerShop;
}


   
}
