package zte.net.ecsord.params.warehouse.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchResp;

import com.ztesoft.api.ApiRuleException;


public class WareHouseMatchReq extends ZteRequest<WareHouseMatchResp>{
	private static final long serialVersionUID = 2828239192339453303L;
	private String plan_id;
	private String apiMethodName;
	private String order_id;
	private List<Object> wareHouseList;
	@Override
	public void check() throws ApiRuleException {
	
	}
	@Override
	public String getApiMethodName() {
		return this.apiMethodName;
	}
	public void setApiMethodName(String apiMethodName) {
		this.apiMethodName = apiMethodName;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public List<Object> getWareHouseList() {
		return wareHouseList;
	}
	public void setWareHouseList(List<Object> wareHouseList) {
		this.wareHouseList = wareHouseList;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
