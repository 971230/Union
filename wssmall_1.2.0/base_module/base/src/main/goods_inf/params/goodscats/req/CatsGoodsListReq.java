package params.goodscats.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @title 查询分类推荐商品入参
 * @author zou.qinghua
 *
 */
public class CatsGoodsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品分类ID",type="String",isNecessary="Y",desc="商品分类ID")
	private String cat_id;

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String catId) {
		cat_id = catId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodscats.listGoods";
	}
}
