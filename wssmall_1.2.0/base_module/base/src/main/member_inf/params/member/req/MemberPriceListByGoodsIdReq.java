package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class MemberPriceListByGoodsIdReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="商品Id",type="String",isNecessary="Y",desc="goods_id：商品Id， 不能为空。")
	private String goods_id;
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {		
		return "zte.memberServer.memberPrice.qryPriceList";
	}

}
