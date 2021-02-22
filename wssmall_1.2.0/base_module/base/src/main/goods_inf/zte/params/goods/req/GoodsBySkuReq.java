package zte.params.goods.req;

import params.ZteError;
import params.ZteRequest;
import zte.params.goods.resp.GoodsByCrmOfferIdResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;


/**
 * 根据sku查询商品
 */
public class GoodsBySkuReq extends ZteRequest<GoodsByCrmOfferIdResp>{

	private String sku;
	private String source_from;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(sku)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"sku不能为空"));
		}
		if(ApiUtils.isBlank(source_from)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"source_from不能为空"));
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.qryGoodsBySku";
	}

}
