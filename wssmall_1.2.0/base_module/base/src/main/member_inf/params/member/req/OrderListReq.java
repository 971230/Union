package params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class OrderListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="会员id",type="String",isNecessary="Y",desc="会员Id")
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="N",desc="pageSize：分页大小，每页多少条数据，默认为10。")
	private int pageSize;
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="N",desc="pageNo：页码，返回第几页数据，默认为1。")
	private int pageNo;
	@ZteSoftCommentAnnotationParam(name="订单状态",type="String",isNecessary="Y",desc="订单状态   -2退货\n  -1退款\n 0未付款\n 1已付款\n 2已发货\n3.完成\n 4作废,99异常")
	private String orderStatus;
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.order.member.pageOrder";
	}
}
