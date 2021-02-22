package zte.net.iservice.impl;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradeWtverticalGetRequest;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeWtverticalGetResponse;
import zte.net.iservice.ITbService;

public class TbService implements ITbService {

	
	public static final String TRADE_FIELD = "seller_nick,buyer_nick,title,type,created,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,buyer_message,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,is_daixiao,consign_interval,arrive_interval,arrive_cut_time,orders.title,orders.pic_path,orders.price,orders.num,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.outer_sku_id,orders.outer_iid,orders.buyer_rate,orders.seller_rate,orders.is_daixiao";
	@Override
	public TradeGetResponse getTrade(long tid,String appkey,String secret,String sessionKey,String url) throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		TradeGetRequest req=new TradeGetRequest();
		req.setFields(TRADE_FIELD);
		req.setTid(tid);
		TradeGetResponse response = client.execute(req , sessionKey);
		return response;
	}
	/**
	 * 网厅垂直信息查询接口   暂时没有权限
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param tids
	 * @return
	 * @throws ApiException 
	 */
	@Override
	public TradeWtverticalGetResponse getTradeWtvertical(String tids,String appkey,String secret,String sessionKey,String url) throws ApiException{
		//if(true)return null;
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		TradeWtverticalGetRequest req=new TradeWtverticalGetRequest();
		req.setTids(tids);
		TradeWtverticalGetResponse response = client.execute(req , sessionKey);
		return response;
	}
}
