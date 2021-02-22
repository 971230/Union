package params.pay.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

/**
 * 去支付请求
 * @作者 MoChunrun
 * @创建日期 2013-10-21 
 * @版本 V 1.0
 */
public class GoToPayReq extends ZteRequest {

	private String order_id;
	private String bank_id;
	private String type_code;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
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
