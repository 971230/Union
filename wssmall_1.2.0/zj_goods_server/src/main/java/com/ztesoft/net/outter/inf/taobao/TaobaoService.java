package com.ztesoft.net.outter.inf.taobao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.FenxiaoOrdersGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.FenxiaoOrdersGetResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.mall.core.timer.CheckTaoBaoTimerServer;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.model.OuterError;

public class TaobaoService extends AbsTaobaoService {
	private static Logger logger = Logger.getLogger(TaobaoService.class);
	
	
	@Override
	public List<Outer> executeInfService(String start_time,String end_time,Map params,String order_from) throws Exception{
		//boolean test = true;
		/*if(test){
			List<TaobaoOrder> list = new ArrayList<TaobaoOrder>();
			TaobaoOrder to = TestOrderData.packageTaobaoOrder();
			list.add(to);
			return list;
		}*/
		String url = String.valueOf(params.get("url"));
		String appkey = String.valueOf(params.get("appkey"));
		String secret = String.valueOf(params.get("secret"));
		String sessionKey = String.valueOf(params.get("sessionKey"));
		long pageNo = 1;
		long pageSize = 100;
		boolean hasNextPage = true;
		List<Outer> orderList = new ArrayList<Outer>();
		//测试
		//if(true)
		//	return orderList;
		while(hasNextPage){
			TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
			/**
			 * 全量查询
			 */
			/*TradesSoldGetRequest req=new TradesSoldGetRequest();
			req.setFields(ORDER_LIST_FIELDS);
			//req.setStatus(STATUS);
			req.setType(ORDER_TYPE);
			req.setUseHasNext(true);
			req.setPageNo(pageNo);
			req.setPageSize(pageSize);
			//if(!StringUtils.isEmpty(start_time))
				start_time = "2014-03-31 19:40:00";
			req.setStartCreated(DF.parse(start_time));
			req.setEndCreated(DF.parse(end_time));
			TradesSoldGetResponse response = client.execute(req,sessionKey);*/
			
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
			req.setStartModified(DF.parse(DF.format((DF.parse(start_time).getTime()-3*1000*60))));
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
						TradeGetResponse tradeInfo = null;
						try{
							tradeInfo = getTrade(t.getTid(),appkey,secret,sessionKey,url);
						}catch(Exception ex){
							OuterError ng = new OuterError("", "", "", "", order_from, t.getTid()+"", "sysdate","orderinfoerror");
							outterECSTmplManager.insertOuterError(ng);
						}
						if(tradeInfo==null)continue ;
						Outer o = packageTaobaoOrder(t, end_time,appkey,secret,sessionKey,url,order_from,tradeInfo,false,null,null);
						if(o!=null){
							logger.info("----开始对淘宝单"+o.getOut_tid()+"进行归属地市校验------");
							if(CheckTaoBaoTimerServer.isTaobaoCityCode(o.getOrder_city_code(),"","")){
								logger.info("----淘宝单"+o.getOut_tid()+"通过归属地市校验------"+o.getOrder_city_code());
							o.setOrder_from(order_from);
							orderList.add(o);
							}else{
								logger.info("----淘宝单"+o.getOut_tid()+"归属地市校验失败------"+o.getOrder_city_code());
							}
							//测试=================
							//hasNextPage = false;
							//break;
							//测试=================
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
	
	public static List<Trade> testOrderList(String appkey,String secret,String sessionKey) throws ParseException, ApiException{
		String url = "http://gw.api.taobao.com/router/rest";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret,"xml");
		TradesSoldGetRequest req=new TradesSoldGetRequest();
		req.setFields(ORDER_LIST_FIELDS);
		//req.setStatus(STATUS);
		//req.setType("fixed");
		req.setUseHasNext(true);
		req.setPageNo(1l);
		req.setPageSize(50l);
		//req.setBuyerNick("梦中噬");
		String start_time = "2014-04-11 15:55:00";
		req.setStartCreated(DF.parse(start_time));
		req.setEndCreated(new Date());
		TradesSoldGetResponse response = client.execute(req,sessionKey);
		logger.info(response.getBody());
		for(Trade t:response.getTrades()){
			logger.info(t.getTid()+"\t"+t.getOrders().get(0).getTitle()+"\t"+t.getType()+"\t"+t.getOrders().get(0).getStatus()+"\t"+t.getTradeSource()+"\t"+t.getReceiverState()+"\t"+t.getReceiverCity()+"\t"+t.getReceiverDistrict()+"\t"+t.getReceiverZip()+"\t"+t.getReceiverPhone()+"\t"+t.getBuyerArea()+"\t"+t.getIsWt()+"\t"+t.getStatus());
			//TradeWtverticalGetResponse resp = getTradeWtvertical(String.valueOf(t.getTid()),appkey,secret,sessionKey);
			//logger.info(resp.getBody());
		}
		return response.getTrades();
	}
	
	public static void testIncOrderList(String appkey,String secret,String sessionKey) throws ParseException, ApiException{
		String url = "http://gw.api.taobao.com/router/rest";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret,"xml");
		TradesSoldIncrementGetRequest req = new TradesSoldIncrementGetRequest();
		req.setFields(INC_ORDER_EDITTIME_FIELDS);
		req.setType(ORDER_TYPE);
		req.setPageNo(1l);
		req.setPageSize(10l);
		String start_time = "2014-04-09 10:22:06";
		req.setStartModified(DF.parse(start_time));
		req.setEndModified(DF.parse("2014-04-09 18:40:06"));
		TradesSoldIncrementGetResponse response = client.execute(req , sessionKey);
		logger.info(response.getBody());
		for(Trade t:response.getTrades()){
			//t.getOrders().get(0).getOuterSkuId()+"\t"+
			logger.info(t.getTid()+"\t"+t.getOrders().get(0).getTitle()+"\t"+t.getType()+"\t"+t.getOrders().get(0).getStatus()+"\t"+t.getTradeSource()+"\t"+t.getTradeFrom());
			//TradeGetResponse tradeInfo = getTrade(t.getTid(),appkey,secret,sessionKey);
			//logger.info(tradeInfo.getTrade().getSellerMemo());
			//TradeWtverticalGetResponse resp = getTradeWtvertical(String.valueOf(t.getTid()),appkey,secret,sessionKey);
			//logger.info(resp.getBody());
		}
	}
	
	public static void testFenXiao(String appkey,String secret,String sessionKey) throws ApiException{
		String url = "http://gw.api.taobao.com/router/rest";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret,"xml");
		FenxiaoOrdersGetRequest req=new FenxiaoOrdersGetRequest();
		req.setPageNo(1l);
		req.setPageSize(50l);
		req.setTimeType("update_time_type");
		try {
			req.setStartCreated(DF.parse("2014-04-6 12:40:06"));
			req.setEndCreated(DF.parse("2014-04-12 12:40:06"));
			//req.setEndCreated(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		FenxiaoOrdersGetResponse response = client.execute(req , sessionKey);
		logger.info(response.getBody());
	}

	public static void main(String[] args) throws ApiException, ParseException {
		//网厅的key
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey_wt = "12469285";
		String secret_wt = "d7f3540761ae620397baaa27afc1c035";
		String sessionKey_wt = "6100a00011dbbb3e5cb6fe62abb830bbad19b4277093977747143122";
		long start = System.currentTimeMillis();
		TradeFullinfoGetResponse resp = getFullTrade(664767955382460l, appkey_wt, secret_wt, sessionKey_wt, url);
		logger.info(resp.getBody());
		logger.info(resp.getTrade().getTid()+"\t"+resp.getTrade().getStatus()+"\t"+DF.format(resp.getTrade().getModified())/*+"\t"+DF.format(resp.getTrade().getPayTime())*/+"\t"+resp.getTrade().getBuyerArea()+"\t"+resp.getTrade().getOrders().get(0).getOuterSkuId()+"\t"+resp.getTrade().getSellerMemo());
		logger.info("=====================================");
		logger.info("仓库编码:="+resp.getTrade().getOrders().get(0).getStoreCode());
		//String [] sa = resp.getTrade().getSellerMemo().replace("#", "").replace("\r\n", "").replace("\r", "").replace("\n", "").split(",");
		/*for(String s:sa){
			logger.info(s+"=====================");
			String [] s2 = s.trim().split("=");
			if(s2.length>=2)
				logger.info(s2[0]+"\t"+s2[1]);
		}*/
		
		/*logger.info("===========pm===========");
		List<PromotionDetail> pms = resp.getTrade().getPromotionDetails();
		if(pms!=null){
			logger.info("pms-size:="+pms.size());
			for(PromotionDetail p:pms){
				logger.info(p.getGiftItemId()+"\t"+p.getGiftItemName()+"\t"+p.getPromotionName()+"\t"+p.getGiftItemNum());
			}
		}*/
		
		//TradeGetResponse resp = getTrade(594552003448715l,appkey_wt,secret_wt,sessionKey_wt);
		//logger.info(resp.getBody());
		//logger.info(resp.getTrade().getOrders().get(0).getOuterSkuId());
		//网厅  582944192302802
		//TradeWtverticalGetResponse wtresp = getTradeWtvertical("664809302902460",appkey_wt,secret_wt,sessionKey_wt,url);
		//logger.info(wtresp.getBody());
		//logger.info(wtresp.getWtextResults().get(0).getOutPackageId());
//		logger.info(resp.getWtextResults().get(0).getOutPackageId()+"\t"+resp.getWtextResults().get(0).getOutPlanId());
		
		//testOrderList(appkey_wt, secret_wt, sessionKey_wt);
		//testIncOrderList(appkey_wt, secret_wt, sessionKey_wt);
		//testFenXiao(appkey_wt, secret_wt, sessionKey_wt);
		
		long end = System.currentTimeMillis();
		logger.info((end-start));
	}

	//@Override
	@Override
	public void callback(List<Outer> list) {
		// TODO Auto-generated method stub
		
	}
	
}