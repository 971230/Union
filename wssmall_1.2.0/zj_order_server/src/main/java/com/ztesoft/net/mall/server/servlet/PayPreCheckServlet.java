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
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPayPreCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayPreCheckResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
 
/**
 *  支付前校验
 * 
 */
public class PayPreCheckServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(PayPreCheckServlet.class);

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
	 * 支付前校验
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		PayPreCheckResp resp = null;
		try {
			//获取请求的json数据
			requJsonStr = getRequestJson(request, response);			
						logger.info("[PayPreCheckServlet] 请求报文-request:" + requJsonStr);
			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			//请求入参
			JSONObject orderInfoJson = requJson.getJSONObject("order_pay_status_qry_req");
			OrderPayPreCheckReq requ = (OrderPayPreCheckReq)JSONObject.toBean(orderInfoJson, OrderPayPreCheckReq.class);
			//根据序列号查询商品发布报文
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, PayPreCheckResp.class);
		} catch (Exception e) {
			resp = new PayPreCheckResp(); 
			resp.setResp_code("1");
			resp.setResp_msg("校验失败：" + e.getMessage());
			logger.info(e.getMessage(), e);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg()); 
		map.put("query_resp", resp.getQuery_resp());
		
		Map outMap = new HashMap();
		outMap.put("order_pay_status_qry_rsp", map);
		respJsonStr = JSONObject.fromObject(outMap).toString();
		logger.info("[PayPreCheckServlet] 响应报文-response:" + respJsonStr);
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
