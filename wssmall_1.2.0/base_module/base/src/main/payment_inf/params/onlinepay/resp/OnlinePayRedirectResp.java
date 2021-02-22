package params.onlinepay.resp;

import params.ZteResponse;

import com.ztesoft.net.model.PayResult;

/**
 * 在线支付重定向页面出参
 * @作者 MoChunrun
 * @创建日期 2013-12-23 
 * @版本 V 1.0
 */
public class OnlinePayRedirectResp extends ZteResponse {

	private PayResult result;

	public PayResult getResult() {
		return result;
	}

	public void setResult(PayResult result) {
		this.result = result;
	}
	
}
