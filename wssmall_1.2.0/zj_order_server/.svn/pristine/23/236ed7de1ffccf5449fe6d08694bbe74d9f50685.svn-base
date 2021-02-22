package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import net.sf.json.JSONObject;
import zte.net.ecsord.params.ecaop.req.CardDataSynRequest;
import zte.net.ecsord.params.ecaop.resp.CardDataSynResponse;

/**
 * 卡数据同步接口
 * 
 * @author xiang.yangbo@ztesoft.com
 *
 * @date 2017年4月10日
 */
public class CardDataSyncCBssServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;
	
	private static Logger logger = Logger.getLogger(CardDataSyncCBssServlet.class);

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
	 * 卡数据同步接口(总部)
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
		CardDataSynResponse resp = null;
		PrintWriter out = null;
		try {
			CardDataSynRequest requ = new CardDataSynRequest();
			String orderId = request.getParameter("orderId");
			if("".equals(orderId) || orderId == null){
				throw new Exception("订单参数为空.");
			}
			requ.setNotNeedReqStrOrderId(orderId);
			//根据ICCID和收货人号码查询订单信息
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, CardDataSynResponse.class);
			Map<String,Object> map = new HashMap<String,Object>();
			Integer httpCode = Integer.valueOf(resp.getCode());
			if(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 == httpCode){ //成功
				map.put("para", resp.getParas());
				map.put("taxNo", resp.getTaxNo());
				map.put("cardRealFee", resp.getCardRealFee());
			} else{ //失败
				map.put("code", resp.getCode());
				map.put("detail", resp.getDetail());
			}
			//将返回数据转成json格式的字符串
			respJsonStr = JSONObject.fromObject(map).toString();
			//返回接口调用结果
			logger.info("[CardDataSyncCBssServlet] 响应报文-response:"+respJsonStr);
			out = response.getWriter();
			out.print(respJsonStr);
		} catch (Exception e) {
			resp = new CardDataSynResponse(); 
			resp.setCode("-1");
			resp.setDetail("异常错误：" + (e.getMessage() == null? e.toString():e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
}
