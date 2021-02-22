package zte.params.goodstype.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

/**
 * @title 根据商品类型ID查询商品列表请求入参
 * @author zou.qinghua
 *
 */
public class TypeRelGoodsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品类型ID",type="String",isNecessary="Y",desc="商品类型ID，查询类型下的商品列表")
	private String type_id;
	
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String typeId) {
		type_id = typeId;
	}

	@Override
	public void check() throws ApiRuleException {
		if(type_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"type_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodsType.list";
	}

}
