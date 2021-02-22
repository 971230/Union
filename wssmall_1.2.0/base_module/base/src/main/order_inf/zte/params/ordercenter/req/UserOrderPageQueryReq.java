package zte.params.ordercenter.req;

import params.ZteRequest;
import zte.params.ordercenter.resp.UserOrderPageQueryResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.model.OrderQueryParams;

/**
 * 用户订单查询
 * @作者 MoChunrun
 * @创建日期 2015-2-2 
 * @版本 V 1.0
 */
public class UserOrderPageQueryReq extends ZteRequest<UserOrderPageQueryResp> {

	@ZteSoftCommentAnnotationParam(name="查询参数",type="String",isNecessary="Y",desc="查询参数",hasChild=true)
	private OrderQueryParams params;
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="Y",desc="第几页")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="分页大小")
	private int pageSize=10;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.user.order.page.query";
	}

	public OrderQueryParams getParams() {
		return params;
	}

	public void setParams(OrderQueryParams params) {
		this.params = params;
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

}
