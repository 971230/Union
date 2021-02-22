package zte.params.goods.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

public class ProductPageQueryResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="货品分页",type="Page",isNecessary="N",desc="productPage：货品分页。")
	private Page productPage;

	public Page getProductPage() {
		return productPage;
	}

	public void setProductPage(Page productPage) {
		this.productPage = productPage;
	}
	
	
}
