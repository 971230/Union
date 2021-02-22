package params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

/**
 * @title 获取商品促销优惠信息请求入参
 * @author zou.qinghua
 *
 */
public class GoodsPromotionGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品促销ID",type="String",isNecessary="Y",desc="商品促销ID")
	private String pmt_id;
	public String getPmt_id() {
		return pmt_id;
	}

	public void setPmt_id(String pmtId) {
		pmt_id = pmtId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.promotion.get";
	}

}
