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
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListByWorkQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListByWorkQueryResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 工单相关订单列表查询接口
 * 
 * @author 宋琪
 * @date 2018年1月3日
 */
public class OrderListByWorkQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;

	private static Logger logger = Logger.getLogger(OrderListByWorkQueryServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		OrderListByWorkQueryResp resp = null;
		OrderListByWorkQueryReq requ = null;
		try {
			requJsonStr = getRequestJson(request, response);
			logger.info("[OrderListByWorkQueryServlet] 请求报文-request:" + requJsonStr);
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			JSONObject orderInfoJson = requJson.getJSONObject("work_order_list_qry");
			if (null == orderInfoJson) {
				resp = new OrderListByWorkQueryResp();
				resp.setResp_code("1");
				resp.setResp_msg("报文格式异常");
			} else {
				requ = (OrderListByWorkQueryReq) JSONObject.toBean(orderInfoJson, OrderListByWorkQueryReq.class);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				resp = client.execute(requ, OrderListByWorkQueryResp.class);
			}
		} catch (Exception e) {
			resp = new OrderListByWorkQueryResp();
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		map.put("work_order_list", resp.getWork_order_list());
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("work_order_list_qry_resp", map);
		
		respJsonStr = JSONObject.fromObject(map2).toString();
		logger.info("[OrderListByWorkQueryServlet] 响应报文-response:" + respJsonStr);
		PrintWriter out = response.getWriter();
		out.print(respJsonStr);
		out.close();
	}

	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream in = null;
		String requString = "";
		try {
			in = request.getInputStream();
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
