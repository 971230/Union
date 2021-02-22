package params.goodscats.resp;

import java.util.List;

import params.ZteResponse;

public class CatsGoodsListResp extends ZteResponse {
	private List goodsList;

	public List getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List goodsList) {
		this.goodsList = goodsList;
	}
}
