package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GoodsSpecificationInfoResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="商品参数",type="String",isNecessary="Y",desc="param：商品参数信息。")
	public String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	
}
