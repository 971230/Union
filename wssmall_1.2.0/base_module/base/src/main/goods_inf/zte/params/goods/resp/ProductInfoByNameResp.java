package zte.params.goods.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class ProductInfoByNameResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="货品信息",type="Map",isNecessary="N",desc="data：货品信息。")
	private Map<String,String> data;

	public Map<String,String> getData() {
		return data;
	}

	public void setData(Map<String,String> data) {
		this.data = data;
	}
}
