package zte.params.goods.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.params.goods.resp.GoodsByIdsResp;


/**
 * 根据商品id或产品id查询商品
 * @author hu.yi
 * @datew 2014.04.15
 */
public class GoodsByIdsReq extends ZteRequest<GoodsByIdsResp>{

	
	private String goods_id;
	private String product_id;
	private String source_from;
	
	
	
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
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
		if(StringUtils.isEmpty(goods_id) && StringUtils.isEmpty(product_id)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"goods_id或product_id不能为空"));
		}
		if(StringUtils.isNotEmpty(goods_id) && StringUtils.isNotEmpty(product_id)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"goods_id或product_id不能同时传入"));
		}
		if(StringUtils.isEmpty(source_from)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"source_from不能为空"));
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.qryByIds";
	}

}
