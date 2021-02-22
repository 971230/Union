package zte.params.cfg.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Bank;

public class PaymentBankListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="银行列表",type="List",isNecessary="N",desc="银行列表")
	private List<Bank> bankList;

	public List<Bank> getBankList() {
		return bankList;
	}

	public void setBankList(List<Bank> bankList) {
		this.bankList = bankList;
	}
	
}
