package params.goodstype.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

/**
 * @title 根据商品类型ID查询商品列表请求入参
 * @author zou.qinghua
 *
 */
public class GoodsTypeListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品类型ID",type="String",isNecessary="Y",desc="商品类型ID")
	private String type_id;
	
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String typeId) {
		type_id = typeId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodsType.list";
	}

}
