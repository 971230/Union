package com.ztesoft.net.mall.server.servlet;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.drools.core.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.taobao.api.TaobaoParser;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.internal.parser.json.ObjectJsonParser;
import com.taobao.api.internal.util.RequestParametersHolder;
import com.taobao.api.request.AlibabaTianjiSupplierOrderQueryRequest;
import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse;
import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse.DistributionOrderInfo;
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
import com.ztesoft.net.mall.utils.ApiTest;
import com.ztesoft.net.mall.utils.MallUtils;
import com.ztesoft.net.model.Outer;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

public class TaobaoTianjiCtnService extends AbsTaobao {
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
		long pageSize = 10;
		boolean hasNextPage = true;
		List<Outer> orderList = new ArrayList<Outer>();

		while(hasNextPage){
			
			AlibabaTianjiSupplierOrderQueryRequest req = new AlibabaTianjiSupplierOrderQueryRequest();
			/*SupplierTopQueryModel obj1 = new SupplierTopQueryModel();*/
			//obj1.setBizType("10");
			//obj1.setDistributorName("distributorName");
			if(StringUtils.isEmpty(start_time))
				start_time = DF.format(new Date(System.currentTimeMillis()-30*60*1000));//"2014-04-02 21:12:00";
			/*obj1.setStartTime(DF.parse(start_time));
			obj1.setEndTime(DF.parse(end_time));
			//obj1.setOrderNo("130012345678");
			//obj1.setOrderStatusList();
			obj1.setPageNum(pageNo);
			obj1.setPageSize(pageSize);
			//obj1.setPhoneNo("18857158888");
			
			req.setParamSupplierTopQueryModel(obj1);
			AlibabaTianjiSupplierOrderQueryResponse response = (AlibabaTianjiSupplierOrderQueryResponse) TaoBaoHttpClientUtils.execute(req, strParams);
			*/Map new_map = new HashMap();
			new_map.put("end_time", end_time);
			new_map.put("start_time", DF.format((DF.parse(start_time).getTime()-3*1000*60)));
			new_map.put("page_num", pageNo);
			new_map.put("page_size", pageSize);
			String jsonString = JSON.toJSONString(JSONObject.fromObject(new_map));
			Map<String, String> params1 = new HashMap<String, String>();
		    // 公共参数
		    params1.put("method", "alibaba.tianji.supplier.order.query");
		    params1.put("app_key", appkey);
	        params1.put("session", sessionKey);
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        params1.put("timestamp", df.format(new Date()));
	        params1.put("format", "json");
	        params1.put("v", "2.0");
	        params1.put("sign_method", "hmac");
	        // 业务参数
	        params1.put("param_supplier_top_query_model", jsonString);
	        params1.put("num_iid", "123456789");
	        // 签名参数
	        params1.put("sign", ApiTest.signTopRequest(params1, secret, "hmac"));
	        // 请用API
	        String res = ApiTest.callApi(new URL(url), params1);
	        RequestParametersHolder requestHolder = new RequestParametersHolder();
	        requestHolder.setResponseBody(res);
	        TaobaoParser<?> parser = null;
	        parser = new ObjectJsonParser(AlibabaTianjiSupplierOrderQueryResponse.class, false);
	        TaobaoResponse tRsp = (TaobaoResponse)parser.parse(requestHolder.getResponseBody());
	        tRsp.setBody(requestHolder.getResponseBody());
	        AlibabaTianjiSupplierOrderQueryResponse response = (AlibabaTianjiSupplierOrderQueryResponse)tRsp;
	        JSONObject jsonobject = JSONObject.fromObject(res);
	        //AlibabaTianjiSupplierOrderQueryResponse response = (AlibabaTianjiSupplierOrderQueryResponse) JSONObject.toBean(jsonobject,AlibabaTianjiSupplierOrderQueryResponse.class);
			logger.info(response.getBody());
			//null 为成功
			if(response.getErrorCode()==null){
				List<DistributionOrderInfo> orders = response.getModelList();
				if(orders==null || orders.size()==0){
					//没有数据
					hasNextPage = false;
					break ;
				}
				
				pageNo ++;
				this.initBean();
				INetCache cache = CacheFactory.getCacheByType("");
				for(DistributionOrderInfo order:orders){
					/**
					 * 订购状态:NOT_ORDER-未订购，ORDER_AUDIT-订购中(无订购接口，提交给供应商，线下受理中)，
					 * ON_ORDER-订购中(有订购接口，线上受理中),SUCCESS-订购成功，FAILURE-订购失败,CANCEL-订购取消
					 */
					String status = order.getOrderStatus();
					if("NOT_ORDER".equals(status) || "ORDER_AUDIT".equals(status) || "ON_ORDER".equals(status) 
							|| "SUCCESS".equals(status) || "FAILURE".equals(status) || "CANCEL".equals(status)){
						String tid = order.getTbOrderNo();
						String key = MD5Util.MD5(tid);
						String def = String.valueOf(cache.get(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE, key));
						if (null != def && def.equals(key)) {
							logger.info("[TaobaoFenxiaoCtnService] 淘宝分销订单[" + tid + "]已获取");
							continue;
						} else {
							cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, key,OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
						}
						if(order.getPlanName().contains("冰激凌")){
							logger.info("[TaobaoFenxiaoCtnService] 淘宝分销订单[" + tid + "]商品是“冰激凌”，不接收订单");
							continue;
						}
						if(order.getPlanName().contains("4G")||order.getPlanName().contains("4g")){
							logger.info("[TaobaoFenxiaoCtnService] 淘宝分销订单[" + tid + "]商品是“4G”，不接收订单");
							continue;
						}
						final String inJson = JsonUtil.toJson(order);
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
						logger.info("淘宝天机平台订单[" + tid + "]抓取到本地环境");
						OrderCtnReq orderCtnReq  = new OrderCtnReq();
						orderCtnReq.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOTJORDERSTANDARD);
						orderCtnReq.setReqMsgStr(inJson);
						logger.info(strParams);
						orderCtnReq.setParams(strParams);
						orderCtnReq.setVersion(OrderCtnConsts.VERSION);
						orderCtnReq.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
						final String out_order_id = MallUtils.searchValue(inJson, "tbOrderNo");
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
								thread.setName("TaobaoTianjiCtnWriteEsearchThread");
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
