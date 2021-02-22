package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class QueryOrderAllocationStatusReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.queryOrderAllocationStatusByOrderNo";
	}

	private String orderNo;//总商订单编号

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	
	
}
