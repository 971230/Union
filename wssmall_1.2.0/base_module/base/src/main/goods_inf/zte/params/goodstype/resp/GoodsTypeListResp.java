package zte.params.goodstype.resp;

import java.util.List;

import com.ztesoft.net.mall.core.model.GoodsType;

import params.ZteResponse;

public class GoodsTypeListResp extends ZteResponse {

	private List<GoodsType> typeList;

	public List<GoodsType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<GoodsType> typeList) {
		this.typeList = typeList;
	}
}
