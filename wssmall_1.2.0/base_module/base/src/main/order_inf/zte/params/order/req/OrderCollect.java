package zte.params.order.req;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.model.Outer;

/**
 * 处理后订单原数据与处理后的数据
 * @作者 MoChunrun
 * @创建日期 2014-3-14 
 * @版本 V 1.0
 */
public class OrderCollect implements Serializable {

	private List<OrderOuter> orderOuterList;//处理后订单数据
	private Outer outer;//订单原数据
	
	public List<OrderOuter> getOrderOuterList() {
		return orderOuterList;
	}
	public void setOrderOuterList(List<OrderOuter> orderOuterList) {
		this.orderOuterList = orderOuterList;
	}
	public Outer getOuter() {
		return outer;
	}
	public void setOuter(Outer outer) {
		this.outer = outer;
	}
	
	/*private List<? extends AbsOrderAccept> acceptList;
	
	private AbsOrder infOrder;
	
	private OrderJsonAttr orderAttr;
	
	private List<GoodsJsonAttr> goodsAttrList;*/

	/*public OrderOuter getOrderOuter() {
		return orderOuter;
	}

	public void setOrderOuter(OrderOuter orderOuter) {
		this.orderOuter = orderOuter;
	}

	public List<? extends AbsOrderAccept> getAcceptList() {
		return acceptList;
	}

	public void setAcceptList(List<? extends AbsOrderAccept> acceptList) {
		this.acceptList = acceptList;
	}

	public AbsOrder getInfOrder() {
		return infOrder;
	}

	public void setInfOrder(AbsOrder infOrder) {
		this.infOrder = infOrder;
	}

	public OrderJsonAttr getOrderAttr() {
		return orderAttr;
	}

	public void setOrderAttr(OrderJsonAttr orderAttr) {
		this.orderAttr = orderAttr;
	}

	public List<GoodsJsonAttr> getGoodsAttrList() {
		return goodsAttrList;
	}

	public void setGoodsAttrList(List<GoodsJsonAttr> goodsAttrList) {
		this.goodsAttrList = goodsAttrList;
	}*/
	
}
