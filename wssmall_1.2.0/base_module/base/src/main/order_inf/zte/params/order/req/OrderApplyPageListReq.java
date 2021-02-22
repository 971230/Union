package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.OrderApplyPageListResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.database.NotDbField;

public class OrderApplyPageListReq extends ZteRequest<OrderApplyPageListResp> {

	public static final String ORDER_TYPE_2 = OrderStatus.ORDER_TYPE_2;
	public static final String ORDER_TYPE_3 = OrderStatus.ORDER_TYPE_3;
	public static final String ORDER_TYPE_4 = OrderStatus.ORDER_TYPE_4;
	
	@ZteSoftCommentAnnotationParam(name="申请类型2、退款3、换货4、退货",type="String",isNecessary="Y",desc="申请类型2、退款3、换货4、退货")
	private String service_type = OrderStatus.ORDER_TYPE_4;
	@ZteSoftCommentAnnotationParam(name="申请ID",type="String",isNecessary="N",desc="申请ID")
	private String apply_id;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="N",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="N",desc="会员ID")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="Y",desc="页码")
	private int pageNo = 1;
	@ZteSoftCommentAnnotationParam(name="每页大小",type="String",isNecessary="Y",desc="每页大小")
	private int pageSize = 50;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.apply.page";
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getApply_id() {
		return apply_id;
	}

	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

}
