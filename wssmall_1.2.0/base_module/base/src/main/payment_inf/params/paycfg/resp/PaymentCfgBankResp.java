package params.paycfg.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.Bank;

public class PaymentCfgBankResp extends ZteResponse{

	private List<Bank> bankList;

	public List<Bank> getBankList() {
		return bankList;
	}

	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}
	
	
}
