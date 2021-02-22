package params.goodstype.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class GoodsTypeGetReq extends ZteRequest {
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
		return "zte.goodsService.goodsType.get";
	}

}
