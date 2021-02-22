package params.goodstype.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class GoodsTypeReq extends ZteRequest {
	String goods_id;

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
		// TODO Auto-generated method stub
		return null;
	}
}
