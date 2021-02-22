package zte.params.goodstype.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.GoodsType;

import params.ZteResponse;
/**
 * 返回商品类型信息
 * @author zou.qinghua
 *
 */
public class GoodsTypeGetResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="商品类型",type="GoodsType",isNecessary="Y",desc="goodsType：商品类型",hasChild=true)
	private GoodsType goodsType;

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
}
