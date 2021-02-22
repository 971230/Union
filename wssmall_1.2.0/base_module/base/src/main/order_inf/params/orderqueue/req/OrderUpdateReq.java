package params.orderqueue.req;

import params.ZteRequest;
import params.orderqueue.resp.OrderUpdateResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @Description 订单修改
 * @author  zhangJun
 * @date    2015-3-12
 * @version 1.0
 */
public class OrderUpdateReq extends ZteRequest<OrderUpdateResp> {
	
	@ZteSoftCommentAnnotationParam(name="队列id",type="String",isNecessary="Y",desc="队列id")
	private String co_id;
	@ZteSoftCommentAnnotationParam(name="订单id",type="String",isNecessary="Y",desc="订单id")
	private String order_id;


	
	

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}
	
	@Override
	public String getApiMethodName() {
		return "zte.service.orderqueue.orderUpdate";
	}

	
	
}
