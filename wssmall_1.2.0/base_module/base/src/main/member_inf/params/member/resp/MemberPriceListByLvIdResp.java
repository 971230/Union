package params.member.resp;

import params.ZteResponse;

import java.util.List;

public class MemberPriceListByLvIdResp extends ZteResponse{
	
	private List goodsLvPriceList;

	public List getGoodsLvPriceList() {
		return goodsLvPriceList;
	}

	public void setGoodsLvPriceList(List goodsLvPriceList) {
		this.goodsLvPriceList = goodsLvPriceList;
	}
	
}
