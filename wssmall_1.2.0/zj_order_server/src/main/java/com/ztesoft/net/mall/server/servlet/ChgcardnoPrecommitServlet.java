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
import com.ztesoft.net.ecsord.params.ecaop.req.ChgcardnoPrecommitReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChgcardnoPrecommitResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class ChgcardnoPrecommitServlet extends HttpServlet{

private static final long serialVersionUID = 4351912905732590784L;
	
	private static Logger logger = Logger.getLogger(ChgcardnoPrecommitServlet.class);

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
		ChgcardnoPrecommitResp resp= null;
		try {
			requJsonStr = getRequestJson(request, response);
			logger.info("[ChgcardnoPrecommitServlet] 请求报文-request:" + requJsonStr);

			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			JSONObject orderInfoJson = requJson.getJSONObject("order_pre_submit_req");
			ChgcardnoPrecommitReq req = (ChgcardnoPrecommitReq) JSONObject.toBean(orderInfoJson, ChgcardnoPrecommitReq.class);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(req, ChgcardnoPrecommitResp.class);
		} catch (Exception e) {
			resp = new ChgcardnoPrecommitResp(); 
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null? e.toString():e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		map.put("order_id", resp.getBss_pre_order_id());
		Map<String,Object> respMap = new HashMap<String,Object>();
		respMap.put("order_pre_submit_rsp", map);
		//将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		//返回接口调用结果
		logger.info("[ChgcardnoPrecommitServlet] 响应报文-response:"+respJsonStr);
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
