package params.member.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class OrderInfoReq extends ZteRequest {

	private String order_id;
	private String menber_id;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMenber_id() {
		return menber_id;
	}

	public void setMenber_id(String menber_id) {
		this.menber_id = menber_id;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
