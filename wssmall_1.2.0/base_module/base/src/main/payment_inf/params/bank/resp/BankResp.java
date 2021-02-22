package params.bank.resp;

import com.ztesoft.net.mall.core.model.Bank;

import params.ZteResponse;

public class BankResp extends ZteResponse{
	private Bank bank;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
}
