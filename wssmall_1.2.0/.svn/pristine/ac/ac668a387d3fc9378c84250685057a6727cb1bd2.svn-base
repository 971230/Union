package zte.params.ordercenter.req;

import params.ZteRequest;
import zte.params.ordercenter.resp.MemberOrderPageQueryResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class MemberOrderPageQueryReq extends ZteRequest<MemberOrderPageQueryResp> {
	
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="Y",desc="第几页")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="分页大小")
	private int pageSize=10;
	@ZteSoftCommentAnnotationParam(name="环节ID",type="String",isNecessary="Y",desc="环节ID")
	private String flow_trace_id;
	@ZteSoftCommentAnnotationParam(name="支付状态",type="String",isNecessary="Y",desc="支付状态[0未支付1已支付]")
	private String pay_status;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="订单创建开始时间",type="String",isNecessary="Y",desc="订单创建开始时间")
	private String create_start;
	@ZteSoftCommentAnnotationParam(name="订单创建结束时间",type="String",isNecessary="Y",desc="订单创建结束时间")
	private String create_end;
	@ZteSoftCommentAnnotationParam(name="是否历史单",type="String",isNecessary="Y",desc="是否历史单")
	private boolean hisFlag = false;
	@ZteSoftCommentAnnotationParam(name="是否返回orderTree",type="String",isNecessary="Y",desc="是否返回orderTree")
	private boolean retOrderTree = true;
	@ZteSoftCommentAnnotationParam(name="是否已经发货（0表示未发货，1表示已发货）",type="String",isNecessary="Y",desc="是否已经发货（0表示未发货，1表示已发货）")
	private String ship_status = "-1";
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.ordercenter.member.order.page.query";
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

	public String getFlow_trace_id() {
		return flow_trace_id;
	}

	public void setFlow_trace_id(String flow_trace_id) {
		this.flow_trace_id = flow_trace_id;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCreate_start() {
		return create_start;
	}

	public void setCreate_start(String create_start) {
		this.create_start = create_start;
	}

	public String getCreate_end() {
		return create_end;
	}

	public void setCreate_end(String create_end) {
		this.create_end = create_end;
	}

	public boolean isHisFlag() {
		return hisFlag;
	}

	public void setHisFlag(boolean hisFlag) {
		this.hisFlag = hisFlag;
	}

	public boolean isRetOrderTree() {
		return retOrderTree;
	}

	public void setRetOrderTree(boolean retOrderTree) {
		this.retOrderTree = retOrderTree;
	}

	/**
	 * @return the ship_status
	 */
	public String getShip_status() {
		return ship_status;
	}

	/**
	 * @param ship_status the ship_status to set
	 */
	public void setShip_status(String ship_status) {
		this.ship_status = ship_status;
	}

	
}
