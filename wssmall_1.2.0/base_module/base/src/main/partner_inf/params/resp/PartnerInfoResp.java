package params.resp;

import params.ZteResponse;

import com.ztesoft.net.app.base.core.model.Partner;


/**
 * 分销商信息查询出参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerInfoResp extends ZteResponse{

	private Partner partner;

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}
}
