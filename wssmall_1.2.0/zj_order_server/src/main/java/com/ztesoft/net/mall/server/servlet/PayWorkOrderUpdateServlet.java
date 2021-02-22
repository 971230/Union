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
import com.ztesoft.net.ecsord.params.ecaop.req.PayWorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayWorkOrderUpdateResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 收费单同步接口
 * 
 * @author 宋琪
 * @date 2017年12月1日
 */
public class PayWorkOrderUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;

	private static Logger logger = Logger.getLogger(PayWorkOrderUpdateServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		execute(request, response);
	}

	/**
	 * 收费单同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		PayWorkOrderUpdateReq requ = null;
		PayWorkOrderUpdateResp resp = null;
		try {
			requJsonStr = getRequestJson(request, response);
			logger.info("[PayWorkOrderUpdateServlet] 请求报文-request:" + requJsonStr);
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			JSONObject orderInfoJson = requJson.getJSONObject("order_status_update_req");
			requ = (PayWorkOrderUpdateReq) JSONObject.toBean(orderInfoJson, PayWorkOrderUpdateReq.class);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, PayWorkOrderUpdateResp.class);
		} catch (Exception e) {
			resp = new PayWorkOrderUpdateResp();
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("order_status_update_resp", map);
		respJsonStr = JSONObject.fromObject(respMap).toString();
		logger.info("[PayWorkOrderUpdateServlet] 响应报文-response:" + respJsonStr);
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
