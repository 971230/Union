package params.member.resp;

import params.ZteResponse;

import java.util.List;

public class PricePrivListResp extends ZteResponse{
	
	private List pricePrivList;

	public List getPricePrivList() {
		return pricePrivList;
	}

	public void setPricePrivList(List pricePrivList) {
		this.pricePrivList = pricePrivList;
	}
	
}
