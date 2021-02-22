package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.params.order.resp.OrderStatusEditResp;

/**
 * 修改订单状态
 * @作者 MoChunrun
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class OrderStatusEditReq extends ZteRequest<OrderStatusEditResp> {
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="订单状态",type="String",isNecessary="N",desc="订单状态2、未付款、审核通过。"
		+"3、已支付、待受理。 " 
		+"4、已受理待发货。    "
		+"5、已发货待确认。    "
		+"6、确认收货。        "
		+"7、已完成。          "
		+"8、作废。            "
		+"9、撤单。            "
		+"-10、取消订单。       "
		+"99、异常。	")
	private String order_status;
	@ZteSoftCommentAnnotationParam(name="支付状态",type="String",isNecessary="N",desc="支付状态0、未支付。"        
		+"1、已支付。    "
		+"2、已经退款。  "
		+"3、部分退款。  "
		+"4、部分付款。  "
		+"5、退款申请中。")
	private String pay_status;
	@ZteSoftCommentAnnotationParam(name="发货状态",type="String",isNecessary="N",desc="发货状态0待发货   。"
		+"1已发货   。"
		+"2.已退货  。"
		+"4 部分发货。"
		+"3 部分退货。"
		+"5部分换货	。"
		+"6已换货   。")
	private String ship_status;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不能为空"));
		if(StringUtils.isEmpty(order_status) && StringUtils.isEmpty(pay_status) && StringUtils.isEmpty(ship_status))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_status、pay_status、ship_status不能同时为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.orderStatus.edit";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getShip_status() {
		return ship_status;
	}

	public void setShip_status(String ship_status) {
		this.ship_status = ship_status;
	}

}
