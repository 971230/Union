package params.order.resp;

import params.ZteResponse;

import java.util.List;

public class BankListResp extends ZteResponse {

	private List bankList;

	public List getBankList() {
		return bankList;
	}

	public void setBankList(List bankList) {
		this.bankList = bankList;
	}
	
	
}
