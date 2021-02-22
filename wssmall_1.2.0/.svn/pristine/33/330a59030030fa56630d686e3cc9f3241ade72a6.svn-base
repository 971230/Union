package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.OrderOuterQryResp;


/**
 * 外系统信息获取入参
 * @author hu.yi
 * @date 2014.05.26
 */public class OrderOuterQryReq extends ZteRequest<OrderOuterQryResp>{

	@ZteSoftCommentAnnotationParam(name="订单id",type="String",isNecessary="Y",desc="订单id")
	private String order_id;
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(order_id)){
			throw new ApiRuleException("-1","order_id不能为空");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.orderOuter.qry";
	}

}
