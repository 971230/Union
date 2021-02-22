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
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.OrderInfoSynReq;
import zte.net.ecsord.params.ecaop.resp.OrderInfoSynRsp;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 2.3.7. 订单列表查询接口
 * 
 * @author 宋琪
 *
 * @date 2017年6月1日
 */
public class TestQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;

	private static Logger logger = Logger.getLogger(TestQueryServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		OrderInfoSynRsp resp = null;
		OrderInfoSynReq requ = null;
		try {
			// 获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[test] 请求报文-request:" + requJsonStr);
			// 解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			// 请求入参
			JSONObject orderResultJson = requJson.getJSONObject("test");
			requ = (OrderInfoSynReq) JSONObject.toBean(orderResultJson, OrderInfoSynReq.class);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			resp = client.execute(requ, OrderInfoSynRsp.class);
			String orderId = resp.getResultMsg().getOrderId();
			if (!StringUtil.isEmpty(orderId)) {
				// OrderExtBusiRequest orderExt =
				// CommonDataFactory.getInstance()
				// .getOrderTree(requ.getNotNeedReqStrOrderId()).getOrderExtBusiRequest();
				// orderExt.setSrc_out_tid(orderId);
				// orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				// orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				// orderExt.store();
				CommonDataFactory.getInstance().updateAttrFieldValue(requ.getNotNeedReqStrOrderId(),
						new String[] { AttrConsts.SRC_OUT_TID }, new String[] { orderId });
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(requ.getNotNeedReqStrOrderId(),
					new String[] { AttrConsts.SYN_ORD_ZB }, new String[] { "1" });
			// Class<?> clazz =
			// Class.forName("zte.net.ecsord.params.ecaop.resp.OrderInfoSynRsp");
			// resp = (OrderInfoSynRsp) JsonUtil.fromJson(requJsonStr, clazz);
		} catch (Exception e) {
			resp = new OrderInfoSynRsp();
			// resp.setResp_code("1");
			// resp.setResp_msg("异常错误1：" + (e.getMessage() == null ?
			// e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("test", resp);
		// 将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		// 返回接口调用结果
		logger.info("[test] 响应报文-response:" + respJsonStr);
		PrintWriter out = response.getWriter();
		out.print(respJsonStr);
		out.close();
	}

	/**
	 * 获取请求的json数据
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 * @author 宋琪
	 *
	 * @date 2017年6月1日
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream in = null;
		String requString = "";
		try {
			// 获取流
			in = request.getInputStream();
			// 转为string
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
