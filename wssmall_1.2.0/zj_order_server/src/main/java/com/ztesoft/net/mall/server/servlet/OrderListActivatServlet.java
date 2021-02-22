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
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListActivateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListActivateResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 工单相关订单列表查询接口
 * 
 * @author 黄斯达
 * @date 2019年3月8日
 */
public class OrderListActivatServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;

	private static Logger logger = Logger.getLogger(OrderListActivatServlet.class);

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
		OrderListActivateResp resp = null;
		OrderListActivateReq requ = null;
		try {
			requJsonStr = getRequestJson(request, response);
			logger.info("[OrderListActivatServlet] 请求报文-request:" + requJsonStr);
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			JSONObject orderInfoJson = requJson.getJSONObject("order_info_qry_req");
			if (null == orderInfoJson) {
				resp = new OrderListActivateResp();
				resp.setResp_code("1");
				resp.setResp_msg("报文格式异常");
			} else {
				requ = (OrderListActivateReq) JSONObject.toBean(orderInfoJson, OrderListActivateReq.class);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				String source_system=requ.getSource_system();
				String receive_system=requ.getReceive_system();
				String templet_id=requ.getTemplet_id();
				if(StringUtil.isEmpty(source_system)||StringUtil.isEmpty(receive_system)||StringUtil.isEmpty(templet_id)) {
					resp = new OrderListActivateResp();
					resp.setResp_code("1");
					resp.setResp_msg("必填值缺失");
				}else {
					resp = client.execute(requ, OrderListActivateResp.class);
				}
			}
		} catch (Exception e) {
			resp = new OrderListActivateResp();
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		map.put("query_resp", resp.getQuery_list());
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("order_info_qry_rsp", map);
		
		respJsonStr = JSONObject.fromObject(map2).toString();
		logger.info("[OrderListActivatServlet] 响应报文-response:" + respJsonStr);
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
