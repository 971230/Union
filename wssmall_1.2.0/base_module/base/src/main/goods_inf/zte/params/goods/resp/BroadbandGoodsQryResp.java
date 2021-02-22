package zte.params.goods.resp;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class BroadbandGoodsQryResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="可受理宽带商品",type="String",isNecessary="Y",desc="goodsList：可受理宽带商品。")
	public List<Map> product_list;
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="resp_code：返回代码")
	private String resp_code;	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="resp_msg：返回描述")
	private String resp_msg;
	
	public List<Map> getProduct_list() {
		return product_list;
	}

	public void setProduct_list(List<Map> product_list) {
		this.product_list = product_list;
	}

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_msg() {
		return resp_msg;
	}

	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
}
