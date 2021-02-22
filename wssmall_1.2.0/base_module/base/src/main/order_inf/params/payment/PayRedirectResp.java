package params.payment;

import com.ztesoft.net.model.PayResult;
import params.ZteResponse;

public class PayRedirectResp extends ZteResponse {

	private PayResult result;

	public PayResult getResult() {
		return result;
	}

	public void setResult(PayResult result) {
		this.result = result;
	}
	
}
