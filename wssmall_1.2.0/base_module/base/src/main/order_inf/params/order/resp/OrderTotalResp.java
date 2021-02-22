package params.order.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.support.OrderPrice;
import params.ZteResponse;

public class OrderTotalResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="订单价格信息",type="String",isNecessary="Y",desc="订单价格信息",hasChild=true)
	private OrderPrice orderPrice;

	public OrderPrice getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(OrderPrice orderPrice) {
		this.orderPrice = orderPrice;
	}
	
}
