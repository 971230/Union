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

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.CardDataSyncBssReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardDataSyncBssResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import net.sf.json.JSONObject;

/**
 * 卡数据同步接口
 * 
 * @author xiang.yangbo@ztesoft.com
 *
 * @date 2017年4月10日
 */
public class CardDataSyncBssServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(CardDataSyncBssServlet.class);

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
	 * 卡数据同步接口
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 *
	 * @author xiang.yangbo@ztesoft.com
	 *
	 * @date 2017年4月10日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		CardDataSyncBssResp resp = null;
		PrintWriter out = null;
		try {
//			//获取请求的json数据
//			requJsonStr = getRequestJson(request, response);
//			logger.info("[CardDataSyncBssServlet] 请求报文-request:" + requJsonStr);
//			//解析请求参数
//			JSONObject requJson = JSONObject.fromObject(requJsonStr);
//			//请求入参
//			JSONObject orderInfoJson = requJson.getJSONObject("order_info_qry_req");
//			CardDataSyncBssReq requ = (CardDataSyncBssReq) JSONObject.toBean(orderInfoJson, CardDataSyncBssReq.class);
			CardDataSyncBssReq requ = new CardDataSyncBssReq();
			requ.setService_num("15558163177");
			requ.setRegion_id("A");
			requ.setWrite_status("9");
			requ.setIccid("8986011683600078172Y");
			requ.setReason_id("0");
			requ.setImsi("460015058935142");
			requ.setProc_id("J3617050517222808816");
			requ.setOperator_id("AQHZH521");
			requ.setOffice_id("AQHZ96");
			//根据ICCID和收货人号码查询订单信息
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, CardDataSyncBssResp.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", resp.getCode());
			map.put("msg", resp.getMsg());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("order_info_qry_rsp", map);
			//将返回数据转成json格式的字符串
			respJsonStr = JSONObject.fromObject(respMap).toString();
			//返回接口调用结果
			logger.info("[CardDataSyncServlet] 响应报文-response:"+respJsonStr);
			out = response.getWriter();
			out.print(respJsonStr);
		} catch (Exception e) {
			resp = new CardDataSyncBssResp(); 
			resp.setCode("1");
			resp.setMsg("异常错误：" + (e.getMessage() == null? e.toString():e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
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
	 * @date 2017年4月10日
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
