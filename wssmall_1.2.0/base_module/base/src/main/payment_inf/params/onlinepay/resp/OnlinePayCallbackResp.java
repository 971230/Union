package params.onlinepay.resp;

import params.ZteResponse;

import com.ztesoft.net.model.PayResult;

/**
 * 在线支付回调返回出参
 * @作者 MoChunrun
 * @创建日期 2013-12-23 
 * @版本 V 1.0
 */
public class OnlinePayCallbackResp extends ZteResponse {

	private String respMsg;//返回给银行的信息
	
	private PayResult payResult;

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public PayResult getPayResult() {
		return payResult;
	}

	public void setPayResult(PayResult payResult) {
		this.payResult = payResult;
	}
	
}
