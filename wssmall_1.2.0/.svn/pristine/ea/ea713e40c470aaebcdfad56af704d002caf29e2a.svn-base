/**
 * 
 */
package zte.params.order.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * TODO
 *
 * @author Musoon
 * 2014-4-20 下午9:00:58
 * 
 */
public class OrderCommitReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="order：订单ID不能为空。")
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.commitOrder";
	}

}
