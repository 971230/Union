package com.ztesoft.net.mall.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import org.apache.log4j.Logger;

import zte.net.ecsord.params.zb.req.StateSynchronizationToSystemRequest;
import zte.net.ecsord.params.zb.resp.StateSynchronizationToSystemResponse;
import zte.net.ecsord.params.zb.vo.Order;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.utils.MallUtils;

import consts.ConstsCore;

/**
 * 
 * @Package com.ztesoft.net.mall.server.servlet
 * @Description: 与总部相关接口入口
 * @author MCC
 * @date 2016年11月18日 下午5:19:40
 */
public class CenterMallServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(CenterMallServlet.class);

	/**
	 * serialVersionUID:
	 */
	private static final long serialVersionUID = 4005042154799120750L;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String outJson = "";
		String eopAction = "";//总商接口名称
		String inJson = "";//总商完整报文
		String inJsonBody = "";//总商接口报文体
		//获取报文及接口编码
		try {
			//新增对其他接口的调用
			inJson = getRequestJson(request, response);
			logger.info("[CenterMallServlet] 完整请求报文:" + inJson);
			//解析总商接口名称
			net.sf.json.JSONObject temInJsonObj = net.sf.json.JSONObject.fromObject(inJson);
			eopAction = temInJsonObj.getString("eop_action")==null?"":temInJsonObj.getString("eop_action");
			inJsonBody = temInJsonObj.getString("REQ_STR")==null?"":temInJsonObj.getString("REQ_STR");
		} catch (Exception e) {
			outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"同步失败,"+e.getMessage()+"\"}}";
			logger.info(e.getMessage(), e);
		}
		//根据接口编码进行不同的处理
		if("".equals(outJson)){
			if(EcsOrderConsts.ZB_INF_ORDERSYN.equals(eopAction)){//订单信息同步（periphery.ordersyn）
				outJson = ordersyn(inJsonBody);
			}else if(EcsOrderConsts.ZB_INF_ORDERSTATENOTIFY.equals(eopAction)){//总商状态通知接口(periphery.orderstatenotify)
				outJson = orderstatenotify(inJsonBody);
			}else {
				outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"eop_action is illegal\"}}";
			}
		}	
		//返回接口调用结果
		logger.info("[CenterMallServlet] 返回报文:"+outJson);
		PrintWriter out = response.getWriter();
		out.print(outJson);
		out.close();
	}

	//订单信息同步（periphery.ordersyn）
	private String ordersyn(String inJsonBody){
		String outJson = "";
		// 序列号
		String activeNo = "";
		// 外部订单号
		final String orderId ;
		String orderSource = "";
		try {
			//解析总商请求报文体
			final String inJson = inJsonBody;
			logger.info("[CenterMallServlet]-[periphery.ordersyn] 请求报文体:" + inJson);
			if (null != inJson && !"".equals(inJson)) {
				//获取订单报文主要参数-订单唯一性校验
				activeNo = MallUtils.searchValue(inJson, "ActiveNo");
				orderId = MallUtils.searchValue(inJson, "OrderId");
				//orderSource = MallUtils.searchValue(inJson, "OrderSource");
				orderSource = "10003";	//总部商城固定10003
				if(StringUtils.isEmpty(activeNo) || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(orderSource)){
					outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败,缺少订单关键参数.\"}}";
				}
				
				// 订单唯一性校验-校验订单是否存在
			    INetCache cache = CacheFactory.getCacheByType("");
			    String key = new StringBuffer().append(orderSource).append(orderId).toString();
			    key = MD5Util.MD5(key);
			    String def = String.valueOf(cache.get(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE, key));
			    
			    if (null != def && def.equals(key)) {
			    	outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败,订单已接收.\"}}";
			    } else {
			    	// 写缓存
			    	cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, key,OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
			    	initBean();
					OrderCtnReq req = new OrderCtnReq();
					req.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_CENTERMALLORDERSTANDARD);
					req.setVersion(OrderCtnConsts.VERSION);
					req.setReqMsgStr(inJson);
					req.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
					req.setOutOrderId(orderId);
					long begin = System.currentTimeMillis();
					final OrderCtnResp resp = orderCtnService.orderCtn(req);
					long end = System.currentTimeMillis();
					logger.info("[CenterMallServlet] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
					if (null != resp) {
						String rpc_type = resp.getRpc_type();
						if (OrderCtnConsts.RPC_TYPE_DUBBO.equals(rpc_type)) {// dubbo 调用返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"OK\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"0000\",\"RespDesc\":\"订单[" + orderId + "]同步成功\"}}";
							} else {
								outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
							}
						} else if (OrderCtnConsts.RPC_TYPE_MQ.equals(rpc_type)) {// mq 返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"OK\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"0000\",\"RespDesc\":\"订单[" + orderId + "]同步成功\"}}";
							} else {
								outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
							}
						} else {
							outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\":\"" + activeNo + "\",\"RespCode\":\"9999\",\"RespDesc\":\"订单[" + orderId + "]同步失败\"}}";
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
								thread.setName("CenterMallWriteEsearchThread");
								thread.start();
							}
						}
						
					} else {
						outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"同步失败\"}}";
					}
			    }
			} else {
				outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"同步失败,接口报文为空\"}}";
			}
		} catch (Exception e) {
			outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"同步失败,"+e.getMessage()+"\"}}";
			logger.info(e.getMessage(), e);
		}
		return outJson;
	}
	
	//总商状态通知接口(periphery.orderstatenotify)
	private String orderstatenotify(String inJsonBody){
		String outJson = "";
		logger.info("[CenterMallServlet]-[periphery.orderstatenotify] 请求报文体:" + inJsonBody);
		try {
			if(null != inJsonBody && !"".equals(inJsonBody)){
				StateSynchronizationToSystemRequest request = getStateSyncReqObj(inJsonBody);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				StateSynchronizationToSystemResponse resp = client.execute(request, StateSynchronizationToSystemResponse.class);
				if(EcsOrderConsts.ZB_INF_RESP_CODE_0000.equals(resp.getError_code())){
					outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \""+resp.getActiveNo()+"\",\"RespCode\": \""+resp.getRespCode()+"\",\"RespDesc\": \"状态同步成功\"}}";
				}else{
					outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \""+resp.getActiveNo()+"\",\"RespCode\": \""+resp.getRespCode()+"\",\"RespDesc\": \"状态同步失败\"}}";
				}
			}else{
				outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"状态同步失败,接口报文为空\"}}";
			}
		} catch (Exception e) {
			outJson = "{\"rspcode\":\"00000\",\"rspdesc\":\"NO\",\"rspmsg\":{\"ActiveNo\": \"\",\"RespCode\": \"9999\",\"RespDesc\": \"状态同步失败,"+e.getMessage()+"\"}}";
			logger.info(e.getMessage(), e);
		}
		return outJson;
	}
	
	private StateSynchronizationToSystemRequest getStateSyncReqObj(String inJsonBody){
		//替换空格、回车、换行、制表位为空字符
		Pattern pattern = Pattern.compile("\r|\t|\n");
		Matcher m = pattern.matcher(inJsonBody);
		inJsonBody = m.replaceAll("");
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为StateSynchronizationToSystemRequest类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("Orders", Order.class);
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(StateSynchronizationToSystemRequest.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(inJsonBody);
		StateSynchronizationToSystemRequest request = (StateSynchronizationToSystemRequest)JSONObject.toBean(jsonObject, config);
		return request;
	}

	/**
	 * 获取总部商城请求的json数据
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
