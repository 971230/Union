package com.ztesoft.net.mall.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.IJSONUtil;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

/**
 * 
 * 
 * @Description: 河北单宽带订单归集
 * @author MCC
 * @date 2016-11-09 17:07
 */
public class HbSingleBroadbandCtnServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(HbSingleBroadbandCtnServlet.class);

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 9141574184927557898L;
	
	private IOrderCtnService orderCtnService;
	private IOrderQueueBaseManager orderQueueBaseManager;
	private ICacheUtil cacheUtil;

	private void initBean() {
		if(null == orderCtnService) {
			orderCtnService = SpringContextHolder.getBean("orderCtnService");
		}
		if(null == orderQueueBaseManager) {
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		}
		if(null == cacheUtil) {
			cacheUtil = SpringContextHolder.getBean("cacheUtil");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		execute(request, response);
	}

	/** 
	 * 获取Ip地址 
	 * @return 
	 */  
	protected String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String outJson = "";
		// 外部订单号
		final String orderId ;
		String orderSource = "";
		String serialNumber = "";
		try {
			//获取订单归集请求报文
			final String inJson = getRequestJson(request, response);
			logger.info("[HbSingleBroadbandCtnServlet] 完整请求报文:" + inJson);
			if (null != inJson && !"".equals(inJson)) {
				//外部订单号
				JSONObject json = JSONObject.fromObject(inJson);//MallUtils.searchValue 获取不到属性，改为转JSONObject取值
				orderId = json.getString("ORDER_ID");//MallUtils.searchValue(inJson, "outOrderId");
				serialNumber = json.getString("SERIAL_NUMBER");//预受理工单总费用
				json.put("ORDER_SOURCE", "10080");
				//订单来源
				orderSource = "ECS";
				if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(orderSource)){
					outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步失败,缺少订单关键参数.\"}";
					PrintWriter out = response.getWriter();
					out.print(outJson);
					out.close();
				}
				
				// 订单唯一性校验-校验订单是否存在
			    INetCache cache = CacheFactory.getCacheByType("");
			    String key = new StringBuffer().append(orderSource).append(orderId).toString();
			    key = MD5Util.MD5(key);
			    String def = String.valueOf(cache.get(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE, key));
			    
			    if (null != def && def.equals(key)) {
					outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步失败,订单已接收.\"}";
			    } else {
			    	// 写缓存
			    	cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, key,OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
			    	initBean();
			    	//调用订单归集接口
					OrderCtnReq req = new OrderCtnReq();
					req.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_HBBROADBANDORDERSTANDARD);
					req.setVersion(OrderCtnConsts.VERSION);
					req.setReqMsgStr(json.toString());
					req.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
					req.setOutOrderId(orderId);
					long begin = System.currentTimeMillis();
					final OrderCtnResp resp = orderCtnService.orderCtn(req);
					long end = System.currentTimeMillis();
					logger.info("[LocalMallServlet] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
					if (null != resp) {
						String rpc_type = resp.getRpc_type();
						if (OrderCtnConsts.RPC_TYPE_DUBBO.equals(rpc_type)) {// dubbo 调用返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								outJson = "{\"RESP_CODE\":\"0000\",\"TRADE_ID\":\""+resp.getBase_order_id()+"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步成功.\"}";
							} else {
								outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\""+resp.getBase_order_id()+"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步失败.\"}";
							}
						} else if (OrderCtnConsts.RPC_TYPE_MQ.equals(rpc_type)) {// mq 返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								outJson = "{\"RESP_CODE\":\"0000\",\"TRADE_ID\":\""+resp.getBase_order_id()+"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步成功.\"}";
							} else {
								outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\""+resp.getBase_order_id()+"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步失败.\"}";
							}
						} else {
							outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\""+resp.getBase_order_id()+"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步失败.\"}";
						}
						String esearch_flag = "0";//默认关闭
						esearch_flag = cacheUtil.getConfigInfo(EcsOrderConsts.ESEARCH_FLAG);//是否取消前置校验 0：关闭 1：开启
						if("1".equals(esearch_flag)) {
							final String outJsonStr = outJson;
							if (StringUtils.isNotEmpty(resp.getBase_co_id())) {
								Thread thread = new Thread(new Runnable() {
									@Override
									public void run() {
										orderQueueBaseManager.writeEsearch(resp.getBase_co_id(), inJson, outJsonStr,orderId);
									}
								});
								thread.setName("HBBroadbandWriteEsearchThread");
								thread.start();
							}
						}
						
					} else {
						outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\""+resp.getBase_order_id()+"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"订单[" + orderId + "]同步失败.\"}";
					}
			    }
			} else {
				outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"同步失败.接口报文为空\"}";
			}
		} catch (Exception e) {
			outJson = "{\"RESP_CODE\":\"8888\",\"TRADE_ID\":\"\",\"SERIAL_NUMBER\":\""+serialNumber+"\",\"RESP_DESC\":\"同步失败"+e.getMessage()+"\"}";
			logger.info(e.getMessage(), e);
		}
		logger.info("[HbSingleBroadbandCtnServlet] 返回报文:"+outJson);
		PrintWriter out = response.getWriter();
		out.print(outJson);
		out.close();
	}

	/**
	 * 获取河北单宽带订单归集请求的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//兼容能力开放平台（能开过来的请求，后面的方式获取不到报文）add by mcc 2016-10-09
		try{
			//request.setCharacterEncoding("gb2312");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String line = null;
	        StringBuilder sb = new StringBuilder();
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }
	        logger.info("value:"+sb);
	        if(sb.length()>0){
	        	return sb.toString();
	        }
			/*String json = IOUtils.toString(request.getInputStream());
			if(StringUtils.isNotEmpty(json)){
				return json;
			}*/
		}catch(IOException e){
			e.printStackTrace();
		}
		
		StringBuffer jsonBuffer = new StringBuffer();
		Map map = request.getParameterMap();
		Set keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		String strKey = null;
		String[] strVal = null;
		while (iterator.hasNext()) {
			strKey = iterator.next();
			strVal = (String[]) map.get(strKey);
			if (!"".equals(strVal[0])) {
				jsonBuffer.append(strKey).append("=").append(strVal[0]);
			} else {
				jsonBuffer.append(strKey);
			}
		}
		return jsonBuffer.toString();
	}
}
