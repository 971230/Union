package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Order;

public class OrderTaobaoSyncResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="订单同步列表", type="String", isNecessary="Y", desc="订单同步列表",hasChild=true)
	private List<Order> orderSyncList;
	
	private long totalCount; // 总记录数

	public List<Order> getOrderSyncList() {
		return orderSyncList;
	}

	public void setOrderSyncList(List<Order> orderSyncList) {
		this.orderSyncList = orderSyncList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
