package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.WriteCardResultAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.WriteCardResultAPPResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import net.sf.json.JSONObject;

/**
 * 写卡结果通知接口
 * 
 * @author xiang.yangbo@ztesoft.com
 *
 * @date 2017年2月25日
 */
public class WriteCardResultServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(WriteCardResultServlet.class);

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
	 * 写卡结果通知接口
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 *
	 * @author xiang.yangbo
	 *
	 * @date 2017年2月27日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		WriteCardResultAPPResp resp = new WriteCardResultAPPResp();
		WriteCardResultAPPReq requ = new WriteCardResultAPPReq();
		PrintWriter out = null;
		try {
			//获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[WriteCardResultServlet] 请求报文-request:" + requJsonStr);
			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			//请求入参
			JSONObject CardInfoJson = requJson.getJSONObject("write_card_result_req");
			requ = (WriteCardResultAPPReq ) JSONObject.toBean(CardInfoJson, WriteCardResultAPPReq.class);
			//根据ICCID和收货人号码查询订单信息
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, WriteCardResultAPPResp.class);
			
		} catch (Exception e) {
			resp = new WriteCardResultAPPResp(); 
			resp.setRes_code("1");
			resp.setRes_msg("异常错误：" + e.getMessage() == null? e.toString():e.getMessage());
			e.printStackTrace();
			logger.info(e, e);
		}
		//返回接口调用结果
		logger.info("[WriteCardResultServlet] 响应报文-response:"+resp);
		logger.info("[WriteCardResultServlet] 响应报文-response:"+JSONObject.fromObject(resp).toString());
		respJsonStr = JSONObject.fromObject(resp).toString();
		out = response.getWriter();
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
	 * @author xiang.yangbo@ztesoft.com
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

}
