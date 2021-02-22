package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class ProductPageQueryReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="货品ID",type="String",isNecessary="N",desc="product_id：货品ID。")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="商品Id",type="String",isNecessary="N",desc="product_id：货品ID。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="货品名称",type="String",isNecessary="N",desc="product_id：货品ID。")
	private String name;
	
	private int pageNo=1;
	private int pageSize=10;
	
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		
		return "com.goodsService.product.page.quer";
	}

}
