package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderMakeupReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderMakeupResp;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import net.sf.json.JSONObject;

/**
 * 订单接收接口
 * 
 * @author liu.quan68@ztesoft.com
 *
 * @date 2017年2月25日
 */
public class OrderMakeupServlet extends HttpServlet {

	private static final long serialVersionUID = 4351912905732590784L;
	
	private static Logger logger = Logger.getLogger(OrderMakeupServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
		execute(request, response);
	}
	
	/**
	 * 订单接收接口
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月27日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		OrderMakeupResp resp = new OrderMakeupResp();
		try {
			//获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[OrderMakeupServlet] 请求报文-request:" + requJsonStr);
			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			//请求入参
			JSONObject orderInfoJson = requJson.getJSONObject("mall_req");
			String inJson = orderInfoJson.toString();
			
			if(org.apache.commons.lang.StringUtils.isBlank(inJson)){
				throw new Exception("传入订单信息为空");
			}
			
			OrderMakeupReq requ = (OrderMakeupReq) JSONObject.toBean(orderInfoJson, OrderMakeupReq.class);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			
			String orderId = requ.getOut_order_id(); // 订单编号
			String orderSource = requ.getSource_from(); // 订单来源
			String is_update = requ.getIs_update();// 是否更新订单
			
			if("1".equals(is_update)){
				//修改订单信息
				resp = client.execute(requ, OrderMakeupResp.class);
			}else if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(orderSource)) {
				resp.setResp_code("1");
				resp.setResp_msg("订单[" + orderId+ "]同步失败,缺少订单关键参数.");
				/*respJsonStr = "{\"mall_resp\":{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId
						+ "]同步失败,缺少订单关键参数.\"}}";*/
			}else{
				// 订单唯一性校验-校验订单是否存在
				INetCache cache = CacheFactory.getCacheByType("");
				String key = new StringBuffer().append(orderSource).append(orderId).toString();// 缓存key
				key = MD5Util.MD5(key);
				// 根据key获取缓存,如果能取到值说明订单已存在
				String def = String.valueOf(cache.get(5000, key));

				if (null != def && def.equals(key)) {
					resp.setResp_code("1");
					resp.setResp_msg("订单[" + orderId + "]同步失败,订单已接收.");
					/*outJson = "{\"resp_code\":\"1\",\"resp_msg\":\"订单[" + orderId + "]同步失败,订单已接收.\"}";*/
				} else {
					
					String intent_order_id = requ.getRel_order_id();
					if(null!=intent_order_id){//传空节点会变成"\"\""
						intent_order_id =intent_order_id.replace("\"", "");
					}
					
					if (!StringUtils.isEmpty(intent_order_id)) {
						this.doOrderIntentCheck(intent_order_id, orderId, resp);
					}
					
					if(!"1".equals(resp.getResp_code())){
						// 写缓存
						cache.set(5000, key, key,2160000);
						
						//根据ICCID和收货人号码查询订单信息
						
						resp = client.execute(requ, OrderMakeupResp.class);
						
						if("1".equals(resp.getResp_code())){
							//收单调用失败，则缓存值设为空字符串，可以重新收单
							cache.set(5000, key, "",2160000);
						}
					}
				}
			}
		} catch (Exception e) {
			resp = new OrderMakeupResp(); 
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null? e.toString():e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		map.put("order_id", resp.getOrder_id());
		Map<String,Object> respMap = new HashMap<String,Object>();
		respMap.put("mall_resp", map);
		//将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		//返回接口调用结果
		logger.error("[OrderMakeupServlet] 响应报文-response:"+respJsonStr);
		PrintWriter out = response.getWriter();
		out.print(respJsonStr);
		out.close();
	}

	/**
	 * 获取请求的json数据
	 *
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月27日
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream in = null;
    	String requString = "";
		try {
		    //获取流
			in = request.getInputStream();
	        //转为string
	        requString = IOUtils.toString(in, "utf-8");
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				
			}
		}
		return requString;
	}
	
	/**
	 * 意向单校验
	 * @param intent_order_id
	 * @param orderId
	 * @param resp
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void doOrderIntentCheck(String intent_order_id,String orderId,OrderMakeupResp resp) throws Exception{
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		
		//检查意向单是否存在
		String sql = "SELECT count(1) FROM es_order_intent a WHERE source_from = 'ECS' and a.intent_order_id='"+intent_order_id+"'";
		
		int count = baseDaoSupport.queryForInt(sql);
		
		if(count < 1){
			resp.setResp_code("1");
			resp.setResp_msg("订单[" + orderId + "]同步失败,意向单" + intent_order_id + "不存在.");
			
			return;
		}
		
		if(count > 1){
			resp.setResp_code("1");
			resp.setResp_msg("订单[" + orderId + "]同步失败,意向单" + intent_order_id + "存在多条记录.");
			
			return;
		}
		
		//检查是否已转正式订单
		sql = "SELECT count(1) FROM es_order_extvtl a WHERE source_from = 'ECS' and a.intent_order_id='"+intent_order_id+"'";
		count = baseDaoSupport.queryForInt(sql);
		
		if(count > 0){
			resp.setResp_code("1");
			resp.setResp_msg("订单[" + orderId + "]同步失败,意向单" + intent_order_id + "已转正式订单.");
			
			return;
		}
	}
}
