package zte.params.goodstype.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
/**
 * @title 返回商品分类下的所有商品
 * @author zou.qinghua
 *
 */
public class TypeRelGoodsListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品列表",type="List",isNecessary="Y",desc="goodsList：商品列表")
	private List goodsList;

	public List getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List goodsList) {
		this.goodsList = goodsList;
	}
}
