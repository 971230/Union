package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.OrderOuter;

public class OrderOuterSyncResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="订单同步列表", type="String", isNecessary="Y", desc="订单同步列表",hasChild=true)
	private List<OrderSync> orderSyncList;
	
	public static class OrderSync{
		@ZteSoftCommentAnnotationParam(name="订单基本信息", type="String", isNecessary="Y", desc="订单基本信息",hasChild=true)
		private OrderOuter orderOuter;
		/*@ZteSoftCommentAnnotationParam(name="订单属性", type="String", isNecessary="N", desc="订单属性",hasChild=true)
		private OrderJsonAttr otherAttr;
		@ZteSoftCommentAnnotationParam(name="商品列表", type="String", isNecessary="N", desc="商品列表",hasChild=true)
		private List<GoodsJsonAttr> goodsAttrList;*/
		public OrderOuter getOrderOuter() {
			return orderOuter;
		}
		public void setOrderOuter(OrderOuter orderOuter) {
			this.orderOuter = orderOuter;
		}
		/*public OrderJsonAttr getOtherAttr() {
			return otherAttr;
		}
		public void setOtherAttr(OrderJsonAttr otherAttr) {
			this.otherAttr = otherAttr;
		}
		public List<GoodsJsonAttr> getGoodsAttrList() {
			return goodsAttrList;
		}
		public void setGoodsAttrList(List<GoodsJsonAttr> goodsAttrList) {
			this.goodsAttrList = goodsAttrList;
		}*/
	}

	public List<OrderSync> getOrderSyncList() {
		return orderSyncList;
	}

	public void setOrderSyncList(List<OrderSync> orderSyncList) {
		this.orderSyncList = orderSyncList;
	}
	
}
