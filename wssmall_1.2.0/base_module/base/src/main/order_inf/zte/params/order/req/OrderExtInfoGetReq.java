package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderExtInfoGetResp;

/**
 * 订单扩展信息查询
 * @作者 MoChunrun
 * @创建日期 2014-7-2 
 * @版本 V 1.0
 */
public class OrderExtInfoGetReq extends ZteRequest<OrderExtInfoGetResp> {

	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String order_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(order_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.orderOuter.getext.info";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
