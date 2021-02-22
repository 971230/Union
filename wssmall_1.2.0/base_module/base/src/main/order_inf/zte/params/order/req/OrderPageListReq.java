package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import params.ZteRequest;
import zte.params.order.resp.OrderPageListResp;

/**
 * 分页查询订单
 * @作者 MoChunrun
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class OrderPageListReq extends ZteRequest<OrderPageListResp> {

	@ZteSoftCommentAnnotationParam(name="查询参数",type="orderQueryParam",isNecessary="N",desc="查询参数",hasChild=true)
	private OrderQueryParam orderQueryParam;
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="Y",desc="第几页  默认1")
	private int pageNo =1;
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="Y",desc="每页记录数：默认50")
	private int pageSize =50;
	@ZteSoftCommentAnnotationParam(name="是否按权限查询",type="String",isNecessary="Y",desc="是否按权限查询[默认为 false]")
	private boolean security = false;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.page";
	}

	public OrderQueryParam getOrderQueryParam() {
		return orderQueryParam;
	}

	public void setOrderQueryParam(OrderQueryParam orderQueryParam) {
		this.orderQueryParam = orderQueryParam;
	}

	public boolean isSecurity() {
		return security;
	}

	public void setSecurity(boolean security) {
		this.security = security;
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
