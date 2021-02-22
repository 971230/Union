package params.onlinepay.req;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.ZteResponse;

/**
 * 在线支付回调入参
 * @作者 MoChunrun
 * @创建日期 2013-12-23 
 * @版本 V 1.0
 */
public class OnlinePayCallbackReq extends ZteRequest<ZteResponse> {

	private String transaction_id;
	
	private Map<String,String> reqParams;

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Map<String, String> getReqParams() {
		return reqParams;
	}

	public void setReqParams(Map<String, String> reqParams) {
		this.reqParams = reqParams;
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
