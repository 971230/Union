package zte.net.ecsord.params.spec.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GoodsTypeIdGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品/货品类型ID",type="String",isNecessary="N",desc="返回商品/货品的type_id")
	private String type_id;

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	
}
