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
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoRefundUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoRefundUpdateResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class OrderInfoUpdateRefundServlet extends HttpServlet {

	
private static final long serialVersionUID = 7900290024351083847L;
	
	private static Logger logger = Logger.getLogger(OrderInfoUpdateServlet.class);

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
	 * 订单信息更新接口
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		OrderInfoRefundUpdateResp resp = null;
		try {
			//获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[OrderInfoUpdateRefundServlet] 请求报文-request:" + requJsonStr);
			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			//请求入参
			JSONObject orderInfoJson = requJson.getJSONObject("order_cancle_req");
			OrderInfoRefundUpdateReq requ = (OrderInfoRefundUpdateReq) JSONObject.toBean(orderInfoJson, OrderInfoRefundUpdateReq.class);
			//根据请求参数更新订单信息
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, OrderInfoRefundUpdateResp.class);
		} catch (Exception e) {
			resp = new OrderInfoRefundUpdateResp(); 
			resp.setResp_code("1");
			resp.setResp_msg("接受失败：" + e.getMessage());
			logger.info(e.getMessage(), e);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		Map<String,Object> respMap = new HashMap<String,Object>();
		respMap.put("mall_resp", map);
		//将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		//返回接口调用结果
		logger.info("[OrderInfoUpdateRefundServlet] 响应报文-response:"+respJsonStr);
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
