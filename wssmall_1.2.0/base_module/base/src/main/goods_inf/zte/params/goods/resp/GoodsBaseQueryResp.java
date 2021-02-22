package zte.params.goods.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Goods;

import params.ZteResponse;

public class GoodsBaseQueryResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="商品",type="String",isNecessary="Y",desc="商品基本信息：goods_id：商品ID，name：商品名称，sn：商品编号，mktprice：市场价，price：销售价，cat_id：商品分类ID，brand_id：商品品牌ID，type_id：商品类型ID，stype_id：商品服务类型ID，brief：产品简述，weight：重量，view_count：浏览次数，buy_count：购买数量，store：库存，point：积分，isgroup：是否团购，islimit：是否限购，image_default：默认图片，image_file：图片,type_code：类型编码，state：状态，supper_id：供应商ID，audit_state：审核状态，simple_name：简称，specifications：规格信息，userid：供应商，agent_name：供应商名称，不包括详情页属性信息，详情信息")
	private Goods goods;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
}