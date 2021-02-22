package zte.net.ecsord.params.warehouse.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchRandomResp;

import com.ztesoft.api.ApiRuleException;


public class WareHouseMatchRandomReq extends ZteRequest<WareHouseMatchRandomResp>{
	private static final long serialVersionUID = 2828239192339453303L;
	private String order_id;
	private String apiMethodName;
	private List<Object> wareHouseList;
	@Override
	public void check() throws ApiRuleException {
	
	}
	@Override
	public String getApiMethodName() {
		return this.apiMethodName == null ? "zte.service.warehouse.randomFilter":this.apiMethodName;
	}
	public void setApiMethodName(String apiMethodName) {
		this.apiMethodName = apiMethodName;
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
