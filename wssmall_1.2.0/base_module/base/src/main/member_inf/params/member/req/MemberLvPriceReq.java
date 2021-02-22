package params.member.req;

import params.ZteRequest;
import params.member.resp.MemberLvPriceResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MemberLvPriceReq extends ZteRequest<MemberLvPriceResp>{
	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="product_id：产品Id， 不能为空。")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="商品id",type="String",isNecessary="Y",desc="goods_id：商品， 不能为空。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="等级Id",type="String",isNecessary="Y",desc="lvid：等级Id， 不能为空。")
	private String lvid;
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getLvid() {
		return lvid;
	}

	public void setLvid(String lvid) {
		this.lvid = lvid;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.regionService.memberLvPrice.getMemerLvPrice";
	}

}
