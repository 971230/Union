package com.ztesoft.net.outter.inf.taobao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.model.Outer;

public class JSTService extends AbsTaobaoService{
	private static Logger logger = Logger.getLogger(JSTService.class);
	@Override
	public List<Outer> executeInfService(String start_time, String end_time,
			Map params, String order_from) throws Exception {
		String url = String.valueOf(params.get("url"));
		String appkey = String.valueOf(params.get("appkey"));
		String secret = String.valueOf(params.get("secret"));
		String sessionKey = String.valueOf(params.get("sessionKey"));
		long pageNo = 1;
		long pageSize = 100;
		boolean hasNextPage = true;
		List<Outer> orderList = new ArrayList<Outer>();
		while(hasNextPage){
			TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
			
			/**
			 * 按修改时间增量查询
			 */
			TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
			req.setFields(INC_ORDER_EDITTIME_FIELDS);
			req.setType(ORDER_TYPE);
			req.setPageNo(pageNo);
			req.setPageSize(pageSize);	
			if(StringUtils.isEmpty(start_time)) 
				start_time = DF.format(new Date(System.currentTimeMillis()-30*60*1000));//"2014-04-02 21:12:00";
			req.setStartModified(DF.parse(start_time));
			req.setEndModified(DF.parse(end_time));
			req.setUseHasNext(true);
			TradesSoldIncrementGetResponse response = client.execute(req , sessionKey);
			logger.info(response.getBody());
			//null 为成功
			if(response.getErrorCode()==null){
				List<Trade> list = response.getTrades();//testOrderList(appkey, secret, sessionKey);
				if(list==null || list.size()==0){
					//没有数据
					hasNextPage = false;
					break ;
				}
				
				pageNo ++;
				for(Trade t:list){
					/**
					 * 交易状态。可选值: * TRADE_NO_CREATE_PAY(没有创建支付宝交易) * WAIT_BUYER_PAY(等待买家付款) * 
					 * SELLER_CONSIGNED_PART(卖家部分发货) * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) * 
					 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) * 
					 * TRADE_FINISHED(交易成功) * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) * TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
					 */
					//同步所有订单
					if("SELLER_CONSIGNED_PART".equals(t.getStatus())
							|| "WAIT_SELLER_SEND_GOODS".equals(t.getStatus()) || "WAIT_BUYER_CONFIRM_GOODS".equals(t.getStatus())
							|| "TRADE_BUYER_SIGNED".equals(t.getStatus()) || "TRADE_FINISHED".equals(t.getStatus())){
						Outer o = packageTaobaoOrder(t, end_time,appkey,secret,sessionKey,url,order_from,null,true,null,null);
						if(o!=null){
							o.setOrder_from(order_from);
							orderList.add(o);
						}
					}
				}
				if(!response.getHasNext()){
					//没有下一页
					hasNextPage = false;
					break ;
				}
			}else{
				hasNextPage = false;
				throw new Exception("["+response.getErrorCode()+"]["+response.getMsg()+"]");
			}
		}
		return orderList;
	}

	@Override
	public void callback(List<Outer> list) {
		// TODO Auto-generated method stub
		
	}

}
