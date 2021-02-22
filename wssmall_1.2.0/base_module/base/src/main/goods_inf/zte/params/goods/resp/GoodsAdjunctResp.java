package zte.params.goods.resp;

import com.ztesoft.net.mall.core.model.GoodsAdjunct;

import params.ZteResponse;

import java.util.List;

public class GoodsAdjunctResp extends ZteResponse {

	private List<GoodsAdjunct> adjunctList;

	public List<GoodsAdjunct> getAdjunctList() {
		return adjunctList;
	}

	public void setAdjunctList(List<GoodsAdjunct> adjunctList) {
		this.adjunctList = adjunctList;
	}
	
}
