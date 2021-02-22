package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;

/**
 * @title 获取商品促销优惠信息请求入参
 * @author zou.qinghua
 *
 */
public class GoodsPromotionGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品促销ID",type="String",isNecessary="N",desc="商品促销ID")
	private String pmt_id;
	
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="N",desc="商品ID")
	private String goods_id;
	public String getPmt_id() {
		return pmt_id;
	}

	public void setPmt_id(String pmtId) {
		pmt_id = pmtId;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.promotion.get";
	}

}