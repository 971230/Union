package zte.params.order.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.ReturnsOrder;

import params.ZteResponse;

public class MemberReturnedOrderListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="退货列表",type="String",isNecessary="Y",desc="退货列表")
	private List<ReturnsOrder> returnedOrders;

	public List<ReturnsOrder> getReturnedOrders() {
		return returnedOrders;
	}

	public void setReturnedOrders(List<ReturnsOrder> returnedOrders) {
		this.returnedOrders = returnedOrders;
	}
	
}
