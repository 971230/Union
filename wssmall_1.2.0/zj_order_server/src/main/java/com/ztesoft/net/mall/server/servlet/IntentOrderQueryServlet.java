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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.ecsord.params.ecaop.req.IntentOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.IntentOrderQueryResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class IntentOrderQueryServlet extends HttpServlet{

	private static final long serialVersionUID = 4351912905732590784L;
	
	private static Logger logger = Logger.getLogger(IntentOrderQueryServlet.class);

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
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		IntentOrderQueryResp resp = null;
		try {
			requJsonStr = getRequestJson(request, response);
			logger.info("[IntentOrderQueryServlet] 请求报文-request:" + requJsonStr);

			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			JSONObject orderInfoJson = requJson.getJSONObject("intent_order_req");
			IntentOrderQueryReq req = (IntentOrderQueryReq) JSONObject.toBean(orderInfoJson, IntentOrderQueryReq.class);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(req, IntentOrderQueryResp.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respJsonStr = "{\"intent_order_resp\":{\"resp_code\":\"-1\",\"resp_msg\":\"异常错误："+e.getMessage()+"\"} }";
			logger.info("[OrderGoodsQueryServlet] 响应报文-response:"+respJsonStr);
			PrintWriter out = response.getWriter();
			out.print(respJsonStr);
			out.close(); 
		}		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> param1 = new HashMap<String, Object>();
		if("0".equals(resp.getResp_code())){
			param.put("resp_code", resp.getResp_code());
			param.put("resp_msg", resp.getResp_msg());
			
			try {
				BeanUtils.bean2MapForYR(param1, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			param.put("intent_order_info", JSONArray.fromObject(param1.get("intent_order_info")));
		}else{
			param.put("resp_code", resp.getResp_code());
			param.put("resp_msg", resp.getResp_msg());
		}
		//将返回数据转成json格式的字符串
		Map resp_map = new HashMap();
		resp_map.put("intent_order_resp", JSONObject.fromObject(param));
		respJsonStr = JSONObject.fromObject(resp_map).toString();
		//返回接口调用结果 
		logger.info("[OrderGoodsQueryServlet] 响应报文-response:"+respJsonStr);
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
}
