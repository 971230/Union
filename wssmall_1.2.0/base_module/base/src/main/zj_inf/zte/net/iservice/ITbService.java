package zte.net.iservice;

import com.taobao.api.ApiException;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeWtverticalGetResponse;

public interface ITbService {

	public TradeGetResponse getTrade(long tid,String appkey,String secret,String sessionKey,String url) throws ApiException;
	public TradeWtverticalGetResponse getTradeWtvertical(String tids,String appkey,String secret,String sessionKey,String url) throws ApiException;
}
