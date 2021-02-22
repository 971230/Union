package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class GoodsDetialReq extends ZteRequest {

	private String goods_id;
	private String member_id;
	private String z_goods_id;
	public String getZ_goods_id() {
		return z_goods_id;
	}
	public void setZ_goods_id(String z_goods_id) {
		this.z_goods_id = z_goods_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
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
