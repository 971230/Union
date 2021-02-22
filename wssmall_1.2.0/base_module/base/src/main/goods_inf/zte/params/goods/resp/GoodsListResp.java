package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

/**
 * 商品列表返回对像
 * @作者 MoChunrun
 * @创建日期 2013-10-28 
 * @版本 V 1.0
 */
public class GoodsListResp extends ZteResponse {

	/**
	 * page的result存放的是GoodsView对像
	 */
	@ZteSoftCommentAnnotationParam(name="分页器",type="String",isNecessary="N",desc="分页器：goodsPage.getResult()获取商品信息列表数据，商品信息：goods_id：商品ID，name：商品名称，sn：商品编码，brand_id：品牌id，cat_id：商品目录，type_id：商品类型ID，goods_type：商品类型，weight：重量，market_enable：上下架状态（1上架、0下架），brief：产品简述，price：销售价，mktprice：市场价，view_count：查看次数，buy_count：购买数，store：库存，point：积分，specifications：规格信息等")	
	private Page goodsPage;

	public Page getGoodsPage() {
		return goodsPage;
	}

	public void setGoodsPage(Page goodsPage) {
		this.goodsPage = goodsPage;
	}
	
}
