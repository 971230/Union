package params.resp;

import params.ZteResponse;

import com.ztesoft.net.framework.database.Page;

public class PartnerShopListResp extends ZteResponse{
    private Page partnerShopPage;

	public Page getPartnerShopPage() {
		return partnerShopPage;
	}

	public void setPartnerShopPage(Page partnerShopPage) {
		this.partnerShopPage = partnerShopPage;
	}
    
    
    
}
