/**
 * 
 */
package params.orderqueue.req;

import params.ZteError;
import params.ZteRequest;
import params.orderqueue.resp.OrderHandleLogsAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.OrderHandleLogs;
import commons.CommonTools;

import consts.ConstsCore;


/**
 * @Description 保存订单日志
 * @author  zhangJun
 * @date    2015-3-12
 * @version 1.0
 */
public class OrderHandleLogsAddReq extends ZteRequest<OrderHandleLogsAddResp> {

	private OrderHandleLogs  orderHandleLogs;
	
	
	
	
	public OrderHandleLogs getOrderHandleLogs() {
		return orderHandleLogs;
	}

	public void setOrderHandleLogs(OrderHandleLogs orderHandleLogs) {
		this.orderHandleLogs = orderHandleLogs;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (orderHandleLogs==null) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "日志对象【orderHandleLogs】不能为空！"));
        }
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.service.orderqueue.orderHandleLogsAdd";
	}
	
}
