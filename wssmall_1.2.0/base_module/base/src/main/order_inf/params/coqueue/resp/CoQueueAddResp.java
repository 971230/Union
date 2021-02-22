package params.coqueue.resp;

import params.ZteResponse;
import zte.params.order.req.OrderCollect;

import com.ztesoft.net.mall.core.model.CoQueue;

public class CoQueueAddResp extends ZteResponse {
	CoQueue coQueue;

	private OrderCollect co; //add by wui 订单标准化数据透传
	
	public CoQueue getCoQueue() {
		return coQueue;
	}

	public void setCoQueue(CoQueue coQueue) {
		this.coQueue = coQueue;
	}

	public OrderCollect getCo() {
		return co;
	}

	public void setCo(OrderCollect co) {
		this.co = co;
	}
	
}
