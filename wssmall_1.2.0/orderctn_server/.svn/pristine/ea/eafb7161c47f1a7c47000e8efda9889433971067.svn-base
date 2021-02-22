package com.ztesoft.net.mall.server.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import com.taobao.api.domain.PurchaseOrder;
import com.taobao.api.request.FenxiaoOrdersGetRequest;
import com.taobao.api.response.FenxiaoOrdersGetResponse;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.utils.MallUtils;
import com.ztesoft.net.mall.utils.TaoBaoHttpClientUtils;
import com.ztesoft.net.model.Outer;

import consts.ConstsCore;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

public class TaobaoFenxiaoCtnService extends AbsTaobao {
	private static Logger logger = Logger.getLogger(TaobaoCtnService.class);
	
	private IOrderCtnService orderCtnService;
	private IOrderQueueBaseManager orderQueueBaseManager;
	private ICacheUtil cacheUtil;

	private void initBean() {
		if(null == orderCtnService) {
			orderCtnService = SpringContextHolder.getBean("orderCtnService");
		}
		if (null == orderQueueBaseManager) {
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		}
		if(null == cacheUtil) {
			cacheUtil = SpringContextHolder.getBean("cacheUtil");
		}
	}
	
	@Override
	public List<Outer> executeInfService(String start_time, String end_time,
			Map params, String order_from) throws Exception {
		String url = String.valueOf(params.get("url"));
		String appkey = String.valueOf(params.get("appkey"));
		String secret = String.valueOf(params.get("secret"));
		String sessionKey = String.valueOf(params.get("sessionKey"));
		String jushitaUrl = params.get("jushitaUrl")==null?"":params.get("jushitaUrl").toString();
		String jushitaAppId = params.get("jushitaAppId")==null?"":params.get("jushitaAppId").toString();
		String isJushita = params.get("isJushita")==null?"":params.get("isJushita").toString();
		StringBuffer sb = new StringBuffer();
		
		String strParams = sb.append("{").append("appkey:'").append(appkey)
				.append("',secret:'").append(secret)
				.append("',sessionKey:'").append(sessionKey)
				.append("',url:'").append(url)
				.append("',jushitaUrl:'").append(jushitaUrl)
				.append("',jushitaAppId:'").append(jushitaAppId)
				.append("',isJushita:'").append(isJushita).append("'").append("}").toString();
		long pageNo = 1;
		long pageSize = 100;
		boolean hasNextPage = true;
		List<Outer> orderList = new ArrayList<Outer>();

		while(hasNextPage){
			
			FenxiaoOrdersGetRequest req=new FenxiaoOrdersGetRequest();
			req.setTimeType("update_time_type");//可选值：trade_time_type(采购单按照成交时间范围查询),update_time_type(采购单按照更新时间范围查询)
			req.setPageNo(pageNo);
			req.setPageSize(pageSize);
			if(StringUtils.isEmpty(start_time))
				start_time = DF.format(new Date(System.currentTimeMillis()-30*60*1000));//"2014-04-02 21:12:00";
			req.setStartCreated(DF.parse(start_time));
			req.setEndCreated(DF.parse(end_time));
			FenxiaoOrdersGetResponse response = 
					(FenxiaoOrdersGetResponse)TaoBaoHttpClientUtils.execute(req, strParams);
			logger.info(response.getBody());
			//null 为成功
			if(response.getErrorCode()==null){
				List<PurchaseOrder> orders = response.getPurchaseOrders();//testOrderList(appkey, secret, sessionKey);
				if(orders==null || orders.size()==0){
					//没有数据
					hasNextPage = false;
					break ;
				}
				
				pageNo ++;
				this.initBean();
				INetCache cache = CacheFactory.getCacheByType("");
				for(PurchaseOrder p:orders){
					/**
					 * 采购单交易状态。可选值：
					WAIT_BUYER_PAY(等待付款)
					WAIT_SELLER_SEND_GOODS(已付款，待发货）
					WAIT_BUYER_CONFIRM_GOODS(已付款，已发货)
					TRADE_FINISHED(交易成功)
					TRADE_CLOSED(交易关闭)
					WAIT_BUYER_CONFIRM_GOODS_ACOUNTED(已付款（已分账），已发货。只对代销分账支持)
					PAY_ACOUNTED_GOODS_CONFIRM （已分账发货成功）
					PAY_WAIT_ACOUNT_GOODS_CONFIRM（已付款，确认收货）
					 */
					String status = p.getStatus();
					if("WAIT_SELLER_SEND_GOODS".equals(status) || "WAIT_BUYER_CONFIRM_GOODS".equals(status) || "TRADE_FINISHED".equals(status) 
							|| "WAIT_BUYER_CONFIRM_GOODS_ACOUNTED".equals(status) || "PAY_ACOUNTED_GOODS_CONFIRM".equals(status) || "PAY_WAIT_ACOUNT_GOODS_CONFIRM".equals(status)){
						String tid = p.getFenxiaoId().toString();
						String key = MD5Util.MD5(tid);
						String def = String.valueOf(cache.get(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE, key));
						if (null != def && def.equals(key)) {
							logger.info("[TaobaoFenxiaoCtnService] 淘宝分销订单[" + tid + "]已获取");
							continue;
						} else {
							cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, key,OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
						}
						final String inJson = JsonUtil.toJson(p);
						Map map = BeanUtils.getCurrHostEnv();
						String env_group = (String)map.get("env_group");
						String env_status = (String)map.get("env_status");
						String keyWord = Consts.ZTE_GRAY;
						if(StringUtil.isEmpty(env_group)){
							keyWord = Consts.ZTE_CESHI;
							if(inJson.indexOf(keyWord)==-1){
								logger.info("[TaobaoFenxiaoCtnService] 淘宝分销订单[" + tid + "]不能抓取到测试环境,keyword:"+keyWord);
								continue;
							}
						}else{
							if(env_status.toUpperCase().equals("00A")){
								if(inJson.indexOf(keyWord)>-1){
									logger.info("[TaobaoFenxiaoCtnService] 淘宝分销订单[" + tid + "]不能抓取到生产环境,keyword:"+keyWord);
									continue;
								}
							}else if(env_status.toUpperCase().equals("00X")){
								if(inJson.indexOf(keyWord)==-1){
									logger.info("[TaobaoFenxiaoCtnService] 淘宝分销订单[" + tid + "]不能抓取到灰度环境,keyword:"+keyWord);
									continue;
								}
							}
						}
						//Outer o = packageOuter(p,order_from,end_time,null,null);
						logger.info("淘宝分销订单[" + tid + "]抓取到本地环境");
						OrderCtnReq orderCtnReq  = new OrderCtnReq();
						orderCtnReq.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOFXORDERSTANDARD);
						orderCtnReq.setReqMsgStr(inJson);
						orderCtnReq.setParams(strParams);
						orderCtnReq.setVersion(OrderCtnConsts.VERSION);
						orderCtnReq.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
						final String out_order_id = MallUtils.searchValue(inJson, "fenxiaoid");
						orderCtnReq.setOutOrderId(out_order_id);
						Map<String, Object> dyn_field = new HashMap<String, Object>();
						dyn_field.put("order_from", order_from);
						dyn_field.put("end_time", end_time);//传递抓单时间
						orderCtnReq.setDyn_field(dyn_field);
						long begin = System.currentTimeMillis();
						OrderCtnResp resp = orderCtnService.orderCtn(orderCtnReq);
						long end = System.currentTimeMillis();
						logger.info("[TaobaoFenxiaoCtnService] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
						if (null != resp && !StringUtils.isEmpty(resp.getBase_co_id())) {
							final String out_param ;
							final String co_id = resp.getBase_co_id();
							if (ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
								out_param = "队列ID为[" + co_id + "]订单标准化成功。";
							} else {
								out_param = "队列ID为[" + co_id + "]订单标准化失败。";
							}
							String esearch_flag = "0";//默认关闭
							esearch_flag = cacheUtil.getConfigInfo(EcsOrderConsts.ESEARCH_FLAG);//是否取消前置校验 0：关闭 1：开启
							if("1".equals(esearch_flag)) {
								Thread thread = new Thread(new Runnable() {
									@Override
									public void run() {
										orderQueueBaseManager.writeEsearch(co_id, inJson, out_param,out_order_id);
									}
								});
								thread.setName("TaobaoFenxiaoCtnWriteEsearchThread");
								thread.start();
							}
						}
					}
					
				}
			}
		}
		return orderList;
	}

	@Override
	public void callback(List<Outer> list) {
		// TODO Auto-generated method stub

	}

}
