package zte.params.goodscats.req;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * @title 查询分类推荐商品入参
 * @author zou.qinghua
 *
 */
public class CatsGoodsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品分类ID",type="String",isNecessary="Y",desc="商品分类ID，根据cat_id获取该分类下的商品列表")
	private String cat_id;

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String catId) {
		cat_id = catId;
	}

	@Override
	public void check() throws ApiRuleException {
		if(cat_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"cat_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodscats.listGoods";
	}
}
