package zte.net.ecsord.params.order.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.order.resp.StartOrderPlanResp;

public class StartOrderPlanReq extends ZteRequest<StartOrderPlanResp>{

	private String service_code = "CO_GUIJI_NEW";
	private List<String> orderIdList ;
	
	public String getService_code(){
		return service_code;
	}
	public void setService_code(String service_code){
		this.service_code = service_code;
	}
	public List<String> getOrderIdList(){
		return orderIdList;
	}
	public void setOrderIdList(List<String> orderIdList){
		this.orderIdList = orderIdList;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.orderstandingplan.start";
	}
}
