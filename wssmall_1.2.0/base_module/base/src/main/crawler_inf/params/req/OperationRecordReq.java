package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class OperationRecordReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.queryZBLogisticsInformation";
	}
	
	private String orderId;//总商ID

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
}
