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
import com.ztesoft.net.ecsord.params.ecaop.req.OrderGoodsQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderGoodsQueryResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 商品信息查询接口
 * 
 */
public class OrderGoodsQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(OrderGoodsQueryServlet.class);

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
	 * 销售商品报文查询接口
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		OrderGoodsQueryResp resp = null;
		try {
			
			//获取请求的json数据
			requJsonStr = getRequestJson(request, response);			
						logger.info("[OrderGoodsQueryServlet] 请求报文-request:" + requJsonStr);
			
//						requJsonStr = "{'goods_req':{'seq_no':'170901120540000040'}}";
			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			//请求入参
			JSONObject orderInfoJson = requJson.getJSONObject("goods_req");
			OrderGoodsQueryReq requ = (OrderGoodsQueryReq)JSONObject.toBean(orderInfoJson, OrderGoodsQueryReq.class);
			//根据序列号查询商品发布报文
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, OrderGoodsQueryResp.class);
		} catch (Exception e) {
			resp = new OrderGoodsQueryResp(); 
			resp.setResp_code("1");
			resp.setResp_msg("接受失败：" + e.getMessage());
			logger.info(e.getMessage(), e);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg()); 
		
		String finishRequest = resp.getResp_content();
		JSONObject json = JSONObject.fromObject(finishRequest);

		map.put("resp_content", json);
		Map<String,Object> respMap = new HashMap<String,Object>();
		respMap.put("mall_resp", map);
		//将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
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