package com.ztesoft.net.outter.inf.taobao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.taobao.api.domain.Trade;
import com.taobao.api.response.TradeGetResponse;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.model.OuterError;
import commons.CommonTools;

public class TaobaoOrdStdService extends AbsTaobaoService {
	private static Logger logger = Logger.getLogger(TaobaoOrdStdService.class);
	public List<Outer> executeInfService(String req_content,Map param,String order_from) throws Exception{
		String inJson = req_content;
		List<Outer> out_order_List = new ArrayList<Outer>();
		try{
			
			Trade t = CommonTools.jsonToBean(inJson, Trade.class);
			//Trade t = JsonUtil.fromJson(inJson, Trade.class);
			
			String appkey = String.valueOf(param.get("appkey"));
			String secret = String.valueOf(param.get("secret"));
			String sessionKey = String.valueOf(param.get("sessionKey"));
			String url = String.valueOf(param.get("url"));
			
			String end_time = DF.format(new Date());
			if("SELLER_CONSIGNED_PART".equals(t.getStatus())
					|| "WAIT_SELLER_SEND_GOODS".equals(t.getStatus()) || "WAIT_BUYER_CONFIRM_GOODS".equals(t.getStatus())
					|| "TRADE_BUYER_SIGNED".equals(t.getStatus()) || "TRADE_FINISHED".equals(t.getStatus())){
				TradeGetResponse tradeInfo = null;
				try{
					tradeInfo = getTrade(t.getTid(),appkey,secret,sessionKey,url);
				}catch(Exception ex){
					OuterError ng = new OuterError("", "", "", "", order_from, t.getTid()+"", "sysdate","orderinfoerror");
					outterECSTmplManager.insertOuterError(ng);
				}
				if(tradeInfo==null){
					return out_order_List;
				}
				Outer o = packageTaobaoOrder(t, end_time,appkey,secret,sessionKey,url,order_from,tradeInfo,false,null,null);
				if(o!=null){
					
					out_order_List.add(o);
					
					
				}
			}
		}catch (Exception e) {		
			logger.info(e.getMessage(), e);
		}
		return out_order_List;
	}

	//@Override
	@Override
	public void callback(List<Outer> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Outer> executeInfService(String start_time, String end_time,
			Map params, String order_from) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
