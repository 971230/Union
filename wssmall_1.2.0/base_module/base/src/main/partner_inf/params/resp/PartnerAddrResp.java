package params.resp;

import params.ZteResponse;

import com.ztesoft.net.app.base.core.model.PartnerAddress;



/**
 * 查询分销商地址出参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerAddrResp extends ZteResponse{

	private PartnerAddress partnerAddress;

	public PartnerAddress getPartnerAddress() {
		return partnerAddress;
	}

	public void setPartnerAddress(PartnerAddress partnerAddress) {
		this.partnerAddress = partnerAddress;
	}
}
