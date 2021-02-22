package params.order.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import params.order.resp.OrderOuterResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

public class OrderSyReq extends ZteRequest {
	List<OrderOuterResp> orderOuterResps = new ArrayList<OrderOuterResp>();
	private List<Map> paramsl;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OrderOuterResp> getOrderOuterResps() {
		return orderOuterResps;
	}

	public void setOrderOuterResps(List<OrderOuterResp> orderOuterResps) {
		this.orderOuterResps = orderOuterResps;
	}
	public List<Map> getParamsl() {
		return paramsl;
	}
	public void setParamsl(List<Map> paramsl) {
		this.paramsl = paramsl;
	}
	
}
