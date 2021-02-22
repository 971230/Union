package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zte.net.iservice.IOrderCtnService;
import zte.params.orderctn.req.OrderCtnReq;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.utils.MallUtils;

public class TestServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IOrderCtnService orderCtnService = SpringContextHolder.getBean("orderCtnService");
		String inJson = getRequestJson(request, response);
		OrderCtnReq orderCtnReq  = new OrderCtnReq();
		orderCtnReq.setOutServiceCode(OrderCtnConsts.OUT_SERVICE_CODE_TAOBAOMALLORDERSTANDARD);
		orderCtnReq.setReqMsgStr(inJson);
		orderCtnReq.setParams("{appkey:'12469285',secret:'d7f3540761ae620397baaa27afc1c035',sessionKey:'6101a16779afeb26a73b1fa51ed9e05faa5c9a4b4bd0aa6747143122',url:'http://gw.api.taobao.com/router/rest'}");
		orderCtnReq.setVersion(OrderCtnConsts.VERSION);
		orderCtnReq.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_JSON);
		
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("ip", "10.123.188.116");
		p.put("port", "10001");
		orderCtnReq.setDyn_field(p);
		
		String outOrderId = MallUtils.searchValue(inJson, "tid");
		orderCtnReq.setOutOrderId(outOrderId);
		try {
			orderCtnService.orderCtn(orderCtnReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取总部商城请求的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		StringBuffer jsonBuffer = new StringBuffer();
		Map map = request.getParameterMap();
		Set keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		String strKey = null;
		String[] strVal = null;
		while (iterator.hasNext()) {
			strKey = iterator.next();
			strVal = (String[]) map.get(strKey);
			if (!"".equals(strVal[0])) {
				jsonBuffer.append(strKey).append("=").append(strVal[0]);
			} else {
				jsonBuffer.append(strKey);
			}
		}
		return jsonBuffer.toString();
	}

}
