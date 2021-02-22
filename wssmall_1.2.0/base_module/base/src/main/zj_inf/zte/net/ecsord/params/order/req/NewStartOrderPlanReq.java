package zte.net.ecsord.params.order.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.ZteResponse;

public class NewStartOrderPlanReq extends ZteRequest<ZteResponse>{
	private List<String> orderIdList ;
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.orderstandingplan.start.new";
	}

	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}

}
