package com.ztesoft.net.mall.server.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.utils.MallUtils;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;

/**
 * 
 * @Package com.ztesoft.net.mall.server.servlet
 * @Description: 与总部相关接口入口
 * @author MCC
 * @date 2016年11月18日 下午5:19:40
 */
public class CodePurchaseMallServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(CodePurchaseMallServlet.class);

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
//		String eopAction = "";//总商接口名称
		String inJson = "";//总商完整报文
		String inJsonBody = "";//总商接口报文体
		String orderId = "";//外部单号
		//获取报文及接口编码
		try {
			//新增对其他接口的调用
			inJson = getRequestJson(request, response);
			logger.info("[CenterMallServlet] 完整请求报文:" + inJson);
			//解析码上购接口名称
			net.sf.json.JSONObject temInJsonObj = net.sf.json.JSONObject.fromObject(inJson);
			//eopAction = temInJsonObj.getString("eop_action")==null?"":temInJsonObj.getString("eop_action");
			inJsonBody = temInJsonObj.getJSONObject("order_submit_req")==null?"":temInJsonObj.getJSONObject("order_submit_req").toString();
			JSONObject json = JSONObject.fromObject(inJsonBody);
			orderId = json.getString("mall_order_id");
		} catch (Exception e) {
			outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单接受失败\",\"order_id\":\""+orderId+"\"}}";
			logger.info(e.getMessage(), e);
		}
		//根据接口编码进行不同的处理
		if(!StringUtils.isEmpty(inJsonBody)){
			outJson = ordersyn(inJsonBody);
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
				activeNo = MallUtils.searchValue(inJson, "serial_no");
				orderId = MallUtils.searchValue(inJson, "mall_order_id");
				orderSource = MallUtils.searchValue(inJson, "source_system");
				//orderSource = "10003";	//总部商城固定10003
				if(StringUtils.isEmpty(activeNo) || StringUtils.isEmpty(orderId) || StringUtils.isEmpty(orderSource)){
					outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId + "]同步失败,缺少订单关键参数.\",\"order_id\":\""+orderId+"\"}}";
				}
				
				// 订单唯一性校验-校验订单是否存在
			    INetCache cache = CacheFactory.getCacheByType("");
			    String key = new StringBuffer().append(orderSource).append(orderId).toString();
			    key = MD5Util.MD5(key);
			    String def = String.valueOf(cache.get(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE, key));
			    String rel_order_id = MallUtils.searchValue(inJson, "rel_order_id");
			    if(null != rel_order_id){
			        rel_order_id = rel_order_id.replace("\"", "");
                }
                if (!StringUtils.isEmpty(rel_order_id)) {
                    IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
                    String sql = "select (select distinct order_id from es_order_intent where intent_order_id='" + rel_order_id  + "' and source_from = '" + ManagerUtils.getSourceFrom() + "') as intent, (select distinct order_id from es_order_extvtl where intent_order_id='" + rel_order_id + "' and source_from = '" + ManagerUtils.getSourceFrom() + "') as order_id from dual";
                    List<Map<String,String>> list = baseDaoSupport.queryForList(sql);
                    Map<String,String> map = list.get(0);
                    if (StringUtils.isEmpty(map.get("intent"))) {
                        outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId
                                + "]同步失败." + rel_order_id + "异常}}";
                        return outJson;
                    }
                    if (!StringUtils.isEmpty(map.get("order_id"))) {
                        outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId
                                + "]同步失败,订单已接收." + rel_order_id + "重复}}";
                        return outJson;
                    }
                }

			    if (null != def && def.equals(key)) {
					outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId + "]同步失败,订单已接收.\",\"order_id\":\""+orderId+"\"}}";
			    } else {
			    	// 写缓存
			    	cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, key,OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
			    	initBean();
					OrderCtnReq req = new OrderCtnReq();
					req.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_CODEPURCHASEMALLORDERSTANDARD);
					req.setVersion(OrderCtnConsts.VERSION);
					req.setReqMsgStr(inJson);
					req.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
					req.setOutOrderId(orderId);
					Map<String, Object> reqParamsMap = new HashMap<String, Object>();
                    reqParamsMap.put("orderSource", orderSource);
                    req.setReqParamsMap(reqParamsMap);
					long begin = System.currentTimeMillis();
					final OrderCtnResp resp = orderCtnService.orderCtn(req);
					long end = System.currentTimeMillis();
					logger.info("[CenterMallServlet] 执行标准化总时间为:[" + (end-begin) +"] 返回对象: "+ IJSONUtil.beanToJson(resp));
					if (null != resp) {
						String rpc_type = resp.getRpc_type();
						if (OrderCtnConsts.RPC_TYPE_DUBBO.equals(rpc_type)) {// dubbo 调用返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								String order_id_sql = " select order_id from es_order_ext where out_tid = '"+orderId+"' ";
								IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
								String order_id = baseDaoSupport.queryForString(order_id_sql);
								outJson = "{\"mall_resp\":{\"resp_code\":\"0\",\"resp_msg\":\"订单[" + orderId + "]同步成功\",\"order_id\":\""+order_id+"\"}}";
							} else {
								outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId + "]同步失败,"+(resp.getError_msg() == null? "":resp.getError_msg())+"\",\"order_id\":\""+orderId+"\"}}";
								cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, "",OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
							}
						} else if (OrderCtnConsts.RPC_TYPE_MQ.equals(rpc_type)) {// mq 返回
							String error_code = resp.getError_code();
							if (ConstsCore.ERROR_SUCC.equals(error_code)) {// 同步成功
								String order_id_sql = " select order_id from es_order_ext where out_tid = '"+orderId+"' ";
								IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
								String order_id = baseDaoSupport.queryForString(order_id_sql);
								outJson = "{\"mall_resp\":{\"resp_code\":\"0\",\"resp_msg\":\"订单[" + orderId + "]同步成功\",\"order_id\":\""+order_id+"\"}}";
							} else {
								outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId + "]同步失败\",\"order_id\":\""+orderId+"\"}}";
								cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, "",OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
							}
						} else {
							outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId + "]同步失败\",\"order_id\":\""+orderId+"\"}}";
							cache.set(OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE,key, "",OrderCtnConsts.ORDER_DUPLICATE_CHECK_NAMESPACE_CACHE_TIMEOUT);
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
						outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId + "]同步失败\",\"order_id\":\""+orderId+"\"}}";
					}
			    }
			} else {
				outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"同步失败,接口报文为空\",\"order_id\":\"\"}}";
			}
		} catch (Exception e) {
			outJson = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"同步失败,"+e.getMessage()+"\",\"order_id\":\"\"}}";
			logger.info(e.getMessage(), e);
		}
		return outJson;
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
