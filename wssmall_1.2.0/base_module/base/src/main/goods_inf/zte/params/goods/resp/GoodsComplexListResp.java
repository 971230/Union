package zte.params.goods.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GoodsComplexListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="N",desc="complex：查询商品的相关商品列表。")
	private List complex;

	public List getComplex() {
		return complex;
	}

	public void setComplex(List complex) {
		this.complex = complex;
	}
}
