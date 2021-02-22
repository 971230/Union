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
 * 根据商品crm_offer_id查询商品
 */
public class GoodsByCrmOfferIdReq extends ZteRequest<GoodsByCrmOfferIdResp>{

	
	private String crm_offer_id;
	private String source_from;
	
	
	
	
	public String getCrm_offer_id() {
		return crm_offer_id;
	}

	public void setCrm_offer_id(String crm_offer_id) {
		this.crm_offer_id = crm_offer_id;
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
		if(ApiUtils.isBlank(crm_offer_id)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"crm_offer_id不能为空"));
		}
		if(ApiUtils.isBlank(source_from)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"source_from不能为空"));
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.qryGoodsByCrmOfferId";
	}

}
