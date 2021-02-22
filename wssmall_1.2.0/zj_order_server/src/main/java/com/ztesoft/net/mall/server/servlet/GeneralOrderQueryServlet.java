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

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.GeneralOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.GeneralOrderQueryResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class GeneralOrderQueryServlet extends HttpServlet{

	private static final long serialVersionUID = 4351912905732590784L;
	
	private static Logger logger = Logger.getLogger(GeneralOrderQueryServlet.class);

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
		GeneralOrderQueryResp resp = null;
		try {
			requJsonStr = getRequestJson(request, response);
			logger.info("[GeneralOrderQueryServlet] 请求报文-request:" + requJsonStr);

			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			JSONObject orderInfoJson = requJson.getJSONObject("general_order_req");
			GeneralOrderQueryReq req = (GeneralOrderQueryReq) JSONObject.toBean(orderInfoJson, GeneralOrderQueryReq.class);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(req, GeneralOrderQueryResp.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respJsonStr = "{\"general_order_info\":{\"resp_code\":\"-1\",\"resp_msg\":\"异常错误："+e.getMessage()+"\"} }";
			logger.info("[GeneralOrderQueryServlet] 响应报文-response:"+respJsonStr);
			PrintWriter out = response.getWriter();
			out.print(respJsonStr);
			out.close(); 
		}		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		map.put("page_total", resp.getTotal_count());
		map.put("order_info", resp.getOrder_info());
		Map<String,Object> respMap = new HashMap<String,Object>();
		respMap.put("general_order_info", map);
		//将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		//返回接口调用结果
		logger.info("[GeneralOrderQueryServlet] 响应报文-response:"+respJsonStr);
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