package com.ztesoft.net.mall.server.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

import com.taobao.api.domain.Trade;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.utils.MallUtils;
import com.ztesoft.net.model.Outer;

import consts.ConstsCore;

public class TaobaoErrorCtnService extends AbsTaobao{
	
	private static Logger logger = Logger.getLogger(TaobaoErrorCtnService.class);
	
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
		List<Map> list = outterECSTmplCtnManager.listNotSyncError(order_from);
		List<Outer> outerList = new ArrayList<Outer>();
		boolean isJST = true;
		if(url.indexOf("gw.api.taobao.com")!=-1)
			isJST = false;
		for(Map oe:list){
			try{
				TradeFullinfoGetResponse resp = AbsTaobao.getFullTrade(Long.parseLong(String.valueOf(oe.get("tid"))), strParams);
				Trade t = resp.getTrade();
				if(true || "SELLER_CONSIGNED_PART".equals(t.getStatus())
						|| "WAIT_SELLER_SEND_GOODS".equals(t.getStatus()) || "WAIT_BUYER_CONFIRM_GOODS".equals(t.getStatus())
						|| "TRADE_BUYER_SIGNED".equals(t.getStatus()) || "TRADE_FINISHED".equals(t.getStatus())){
					if(resp!=null && resp.getTrade()!=null){
						this.initBean();
						final String inJson = JsonUtil.toJson(t);
						String tid = t.getTid().toString();
						Map map = BeanUtils.getCurrHostEnv();
						String env_group = (String)map.get("env_group");
						String env_status = (String)map.get("env_status");
						String keyWord = Consts.ZTE_GRAY;
						if(StringUtil.isEmpty(env_group)){
							keyWord = Consts.ZTE_CESHI;
							if(inJson.indexOf(keyWord)==-1){
								logger.info("[TaobaoCtnService] 淘宝error订单[" + tid + "]不能抓取到测试环境,keyword:"+keyWord);
								continue;
							}
						}else{
							if(env_status.toUpperCase().equals("00A")){
								if(inJson.indexOf(keyWord)>-1){
									logger.info("[TaobaoCtnService] 淘宝error订单[" + tid + "]不能抓取到生产环境,keyword:"+keyWord);
									continue;
								}
							}else if(env_status.toUpperCase().equals("00X")){
								if(inJson.indexOf(keyWord)==-1){
									logger.info("[TaobaoCtnService] 淘宝error订单[" + tid + "]不能抓取到灰度环境,keyword:"+keyWord);
									continue;
								}
							}
						}
						logger.info("淘宝error订单[" + tid + "]抓取到本地环境");
						OrderCtnReq orderCtnReq  = new OrderCtnReq();
						orderCtnReq.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOMALLORDERSTANDARD);
						orderCtnReq.setReqMsgStr(inJson);
						orderCtnReq.setParams(strParams);
						orderCtnReq.setVersion(OrderCtnConsts.VERSION);
						orderCtnReq.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
						final String outOrderId = MallUtils.searchValue(inJson, "tid");
						orderCtnReq.setOutOrderId(outOrderId);
						Map<String, Object> dyn_field = new HashMap<String, Object>();
						dyn_field.put("order_from", order_from);
						orderCtnReq.setDyn_field(dyn_field);
						
						//----暂时增量方法------以后需完善参数传入
//						String server_ip = StringUtil.getLocalIpAddress();
//						String server_port = StringUtil.getContextPort();
//						logger.info("[TaobaoCtnService] 请求报文:" + inJson + "请求ip:["+server_ip + "] 请求端口: ["+server_port+"]");
//						Map<String,Object> paramsMap = new HashMap<String,Object>();
//						paramsMap.put("ip", server_ip);
//						paramsMap.put("port", server_port);
//						orderCtnReq.setDyn_field(paramsMap);
						//----暂时增量方法------以后需完善参数传入
						
						logger.info("[TaobaoErrorCtnService] 请求报文:" + inJson);
						long begin = System.currentTimeMillis();
						OrderCtnResp ctnResp = orderCtnService.orderCtn(orderCtnReq);
						long end = System.currentTimeMillis();
						logger.info("[TaobaoErrorCtnService] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
						if (null != ctnResp && StringUtils.isNotEmpty(ctnResp.getBase_co_id())) {
							final String out_param ;
							final String co_id = ctnResp.getBase_co_id();
							String deal_flag = "-1";
							if (ConstsCore.ERROR_SUCC.equals(ctnResp.getError_code())) {
								out_param = "队列ID为[" + co_id + "]订单标准化成功。";
								deal_flag = "1";
							} else {
								out_param = "队列ID为[" + co_id + "]订单标准化失败。";
							}
							//错单抓取后修改错单表deal_flag值,避免重复抓取及影响其它订单抓取
							outterECSTmplCtnManager.updateErrorDelFlag(order_from, t.getTid()+"",deal_flag);
							
							String esearch_flag = "0";//默认关闭
							esearch_flag = cacheUtil.getConfigInfo(EcsOrderConsts.ESEARCH_FLAG);//是否取消前置校验 0：关闭 1：开启
							if("1".equals(esearch_flag)) {
								Thread thread = new Thread(new Runnable() {
									@Override
									public void run() {
										orderQueueBaseManager.writeEsearch(co_id, inJson, out_param,outOrderId);
									}
								});
								thread.setName("TaobaoErrorCtnWriteEsearchThread");
								thread.start();
							}
						}
					}
				}else{
					outterECSTmplCtnManager.updateErrorDelFlag(order_from, t.getTid()+"","2");
				}
			}catch(Exception ex){
				continue;
			}
		}
		return outerList;
	}
	
	public List<Map> test(){
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("tid", "602685126589048");
		list.add(map);
		Map map2 = new HashMap();
		map2.put("tid", "604063535088491");
		list.add(map2);
		Map map3 = new HashMap();
		map3.put("tid", "610814750331994");
		list.add(map3);
		Map map4 = new HashMap();
		map4.put("tid", "615709946044271");
		list.add(map4);
		Map map5 = new HashMap();
		map5.put("tid", "621540588506374");
		list.add(map5);
		Map map6 = new HashMap();
		map6.put("tid", "622092894188744");
		list.add(map6);
		Map map7 = new HashMap();
		map7.put("tid", "622961062232928");
		list.add(map7);
		return list;
	}

	//@Override
	@Override
	public void callback(List<Outer> list) {
		if(list!=null && list.size()>0){
			for(Outer c:list){
				outterECSTmplCtnManager.updateErrorDelFlag(c.getOrder_from(), c.getOut_tid(),"1");
			}
		}
	}
}