package zte.params.order.req;

import java.util.List;

import params.ZteRequest;
import zte.params.order.resp.OrderApplyAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.database.NotDbField;

import com.ztesoft.net.mall.core.model.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;

public class OrderApplyAddReq extends ZteRequest<OrderApplyAddResp> {
	@ZteSoftCommentAnnotationParam(name="申请单基本信息",type="String",isNecessary="Y",desc="申请单基本信息",hasChild=true)
	private OrderApply orderApply;
	@ZteSoftCommentAnnotationParam(name="申请类型2、退款3、换货4、退货",type="String",isNecessary="Y",desc="申请类型2、退款3、换货4、退货")
	private String service_type = OrderStatus.ORDER_TYPE_4;
	@ZteSoftCommentAnnotationParam(name="申请的商品信息",type="String",isNecessary="Y",desc="申请的商品信息",hasChild=true)
	private List<OrderApplyItem> applyItems;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="会员等级ID",type="String",isNecessary="N",desc="会员等级ID")
	private String member_lv_id = "0";
	@ZteSoftCommentAnnotationParam(name="支付方式ID",type="String",isNecessary="N",desc="支付方式ID")
	private String payment_id = "2";
	@ZteSoftCommentAnnotationParam(name="配送方式ID",type="String",isNecessary="N",desc="配送方式ID")
	private String shipping_id;
	@ZteSoftCommentAnnotationParam(name="执行动作 new新增 edit修改",type="String",isNecessary="Y",desc="执行动作 new新增 edit修改")
	private String action = "new";
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.apply.add";
	}

	public OrderApply getOrderApply() {
		return orderApply;
	}

	public void setOrderApply(OrderApply orderApply) {
		this.orderApply = orderApply;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public List<OrderApplyItem> getApplyItems() {
		return applyItems;
	}

	public void setApplyItems(List<OrderApplyItem> applyItems) {
		this.applyItems = applyItems;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMember_lv_id() {
		return member_lv_id;
	}

	public void setMember_lv_id(String member_lv_id) {
		this.member_lv_id = member_lv_id;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(String shipping_id) {
		this.shipping_id = shipping_id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
