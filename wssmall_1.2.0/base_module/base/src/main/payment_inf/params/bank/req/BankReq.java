package params.bank.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;

/**
 * 支付银行
* @作者 wui 
* @创建日期 2013-10-10 
* @版本 V 1.0
 */
public class BankReq extends ZteRequest{
	private String bank_code;
	private String bank_id;

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	@Override
	public void check() throws ApiRuleException {}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
