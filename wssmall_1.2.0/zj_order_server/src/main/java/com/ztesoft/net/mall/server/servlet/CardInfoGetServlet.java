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
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGetAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetAPPResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import net.sf.json.JSONObject;

/**
 * 卡信息获取接口
 * 
 * @author liu.quan68@ztesoft.com
 *
 * @date 2017年2月25日
 */
public class CardInfoGetServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(CardInfoGetServlet.class);

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
	 * 卡信息获取接口
	 *
	 *@param request
	 *@param response
	 *@throws ServletException
	 *@throws IOException
	 *
	 * @author huang.zs 
	 *
	 * @date 2017年2月27日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String respJsonStr = "";//返回json字符串
		String requJsonStr = "";//请求json字符串
		CardInfoGetAPPResp resp = new CardInfoGetAPPResp();
		CardInfoGetAPPReq requ = new CardInfoGetAPPReq();
		PrintWriter out = null;
		try {
			//test start
//			CommCaller caller  = new CommCaller();
//			Map<String, Object> param = new HashMap<String, Object>();
//			String jsonString = "{\"operatorId\":\"AEDKH135\",\"province\":\"36\",\"city\":\"360\",\"district\":\"362005\",\"channelId\":\"36a0187\",\"channelType\":\"1010300\",\"tradeTypeCode\":\"9999\",\"serviceClassCode\":\"0000\",\"areaCode\":\"\",\"serialNumber\":\"18668246459\",\"infoList\":\"CUST|USER|ACCT\"}";
////			jsonString = jsonString.replaceAll("\\{", "\\{\"").replaceAll("\\}", "\"\\}").replaceAll("=", "\":\"").replaceAll(",", "\",\"");
//			logger.info(jsonString);
//			UserInfoCheck3BackReq bean = new UserInfoCheck3BackReq();
//			JSONObject requJson1 = JSONObject.fromObject(jsonString);
////			bean = (OpenDealApplyReq ) JSONObject.toBean(requJson1, OpenDealApplyReq.class);
//			bean.setNotNeedReqStrOrderId("171141753287000618");
//			logger.info(JSONObject.fromObject(bean));
//			BeanUtils.bean2MapForAOP(param, JSONObject.fromObject(jsonString));
//			String opcode = EcsOrderConsts.OLD_QUERY_CUST_USER_ACC;
//			String bizkey = EcsOrderConsts.AOP_BIZKEY.get(opcode);
//			if(!StringUtils.isEmpty(bizkey))param.put("bizkey", bizkey);
//			CardDataSynResponse rsp = new CardDataSynResponse();
//			rsp = (CardDataSynResponse) caller.invoke(opcode, param);//test end
			
//			//获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[CardInfoGetServlet] 请求报文-request:" + requJsonStr);
//			//解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
//			JSONObject requJson = JSONObject.fromObject(request.getParameter("data"));
//			//请求入参
			JSONObject CardInfoJson = requJson.getJSONObject("card_info_get_req");
			requ = (CardInfoGetAPPReq ) JSONObject.toBean(CardInfoJson, CardInfoGetAPPReq .class);
//			//根据ICCID和收货人号码查询订单信息
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, CardInfoGetAPPResp.class);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("resp_code", StringUtils.isEmpty(resp.getResp_code())?resp.getError_code():resp.getResp_code());
			map.put("resp_msg", StringUtils.isEmpty(resp.getResp_msg())?resp.getError_msg():resp.getResp_msg());
			map.put("card_info", resp.getCard_Info());
			Map<String,Object> respMap = new HashMap<String,Object>();
			respMap.put("card_info_get_rsp", map);
			//将返回数据转成json格式的字符串
			respJsonStr = JSONObject.fromObject(respMap).toString().replaceAll("\\\\", "");
			//返回接口调用结果
			logger.info("[GetWhiteCardInfoServlet] 响应报文-response:"+respJsonStr);
			out = response.getWriter();
			out.print(respJsonStr);
//			out.print("{\"res_code\":\"00000\",\"res_message\":\"Success\",\"result\":{\"resp\":{\"scriptseq\":\"A3636010270\",\"proc_id\":\"J3617050919310208826\",\"imsi\":\"460017155328009\"},\"msg\":\"操作成功\",\"code\":\"00000\"}}");
		} catch (Exception e) {
			resp = new CardInfoGetAPPResp(); 
			resp.setResp_code("1");
			resp.setResp_msg("异常错误：" + e.getMessage() == null? e.toString():e.getMessage());
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
	 * @author liu.quan68@ztesoft.com
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
