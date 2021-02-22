package params.member.resp;

import params.ZteResponse;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MemberPriceListByGoodsIdResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="商品会员价格列表",type="List",isNecessary="Y",desc="goodsLvPriceList：商品会员价格列表")
	private List goodsLvPriceList;

	public List getGoodsLvPriceList() {
		return goodsLvPriceList;
	}

	public void setGoodsLvPriceList(List goodsLvPriceList) {
		this.goodsLvPriceList = goodsLvPriceList;
	}
	
}
