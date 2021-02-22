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
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderMixOrdUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderMixOrdUpdateResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 固移融合单状态更新接口
 * @author zhaoc
 *2018-03-19
 */
public class WorkOrderMixOrdUpdateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8214614708914819684L;

	private static Logger logger = Logger.getLogger(WorkOrderMixOrdUpdateServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置字符集为UTF-8
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		execute(request, response);
	}

	/**
	 * 
	 * 业务实现
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		
		WorkOrderMixOrdUpdateReq requ = null;
		WorkOrderMixOrdUpdateResp resp = null;
		
		try {
			//获取传入的JSON字符串
			requJsonStr = getRequestJson(request, response);
			logger.info("[WorkOrderUpdateServlet] 请求报文-request:" + requJsonStr);
			
			//将JSON字符串转换为JSON对象
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			
			JSONObject orderInfoJson = requJson.getJSONObject("work_order_update_requ");
			
			//json对象装换为业务数据请求对象
			requ = (WorkOrderMixOrdUpdateReq) JSONObject.toBean(orderInfoJson, WorkOrderMixOrdUpdateReq.class);
			
			//获取dubbo客户端
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			
			//调用业务接口
			resp = client.execute(requ, WorkOrderMixOrdUpdateResp.class);
			
		} catch (Exception e) {
			resp = new WorkOrderMixOrdUpdateResp();
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		
		//拼装返回的JSON字符串
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("work_order_update_resp", map);
		
		respJsonStr = JSONObject.fromObject(respMap).toString();
		
		logger.info("[WorkOrderUpdateServlet] 响应报文-response:" + respJsonStr);
		
		PrintWriter out = response.getWriter();
		out.print(respJsonStr);
		out.close();
	}

	/**
	 * 获取传入的JSON字符串
	 * @param request 请求对象
	 * @param response 返回对象
	 * @return json字符串
	 * @throws Exception
	 */
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
