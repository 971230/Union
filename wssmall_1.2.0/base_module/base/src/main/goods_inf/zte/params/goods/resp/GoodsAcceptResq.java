package zte.params.goods.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class GoodsAcceptResq  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="Y",desc="name：查询商品受理详情商品名称")
	private String name;
	@ZteSoftCommentAnnotationParam(name="商品价格",type="String",isNecessary="Y",desc="price：查询商品受理详情商品价格")
	private String price;
	@ZteSoftCommentAnnotationParam(name="商品规格、商品规格属性",type="Map",isNecessary="Y",desc="goodsAttr：查询商品受理详情商品规格、商品规格属性")
	private Map goodsAttr;
	@ZteSoftCommentAnnotationParam(name="商品温馨提醒",type="String",isNecessary="Y",desc="reminder：查询商品受理详情商品温馨提醒")
	private String reminder;	
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getView_count() {
		return view_count;
	}
	public void setView_count(String view_count) {
		this.view_count = view_count;
	}
	@ZteSoftCommentAnnotationParam(name="商品库存",type="String",isNecessary="Y",desc="store：查询商品受理详情商品库存")
	private String store;	
	@ZteSoftCommentAnnotationParam(name="商品已售卖量",type="String",isNecessary="Y",desc="view_count：查询商品受理详情商品已售卖量")
	private String view_count;		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Map getGoodsAttr() {
		return goodsAttr;
	}
	public void setGoodsAttr(Map goodsAttr) {
		this.goodsAttr = goodsAttr;
	}
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

}
