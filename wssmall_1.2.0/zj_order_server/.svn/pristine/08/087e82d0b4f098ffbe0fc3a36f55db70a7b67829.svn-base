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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.DeliveryInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.DeliveryInfoUpdateResp;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import net.sf.json.JSONObject;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

/**
 * 物流信息信息更新接口
 * 
 * @author GL
 *
 * @date 2019年4月30日
 */
public class OrderDeliveryInfoUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 7900290024351083847L;

	private static Logger logger = Logger.getLogger(OrderDeliveryInfoUpdateServlet.class);

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

	/**
	 * 物流信息更新接口
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 *
	 * @author GL
	 *
	 * @date 2019年4月30日
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		DeliveryInfoUpdateResp resp = null;
		try {
			// 获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[OrderDeliveryInfoUpdateServlet] 请求报文-request:" + requJsonStr);
			// 解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr.trim());
			// 请求入参
			JSONObject orderInfoJson = requJson.getJSONObject("order_delivery_update_req");
			DeliveryInfoUpdateReq requ = (DeliveryInfoUpdateReq) JSONObject.toBean(orderInfoJson,
					DeliveryInfoUpdateReq.class);

			// 数据校验
			resp = checkDliveryInfo(requ);

			if (!"1".equals(resp.getResp_code())) {
				// 根据请求参数更新订单信息
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				resp = client.execute(requ, DeliveryInfoUpdateResp.class);
			}

		} catch (Exception e) {
			resp = new DeliveryInfoUpdateResp();
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("order_delivery_update_resp", map);
		// 将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		// 返回接口调用结果
		logger.info("[OrderDeliveryInfoUpdateServlet] 响应报文-response:" + respJsonStr);
		PrintWriter out = response.getWriter();
		out.print(respJsonStr);
		out.close();
	}

	/**
	 * 数据校验 是否为空 订单系统是否存在订单
	 * 
	 * @author GL
	 * @param requ
	 * @return
	 */
	private DeliveryInfoUpdateResp checkDliveryInfo(DeliveryInfoUpdateReq requ) {

		DeliveryInfoUpdateResp resp = new DeliveryInfoUpdateResp();
		resp.setResp_code("1");

		// 校验订单编号是否为空
		if (StringUtils.isBlank(requ.getOrder_id().trim())) {
			resp.setResp_msg("订单中心订单编号不能为空");
			return resp;
		}

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(requ.getOrder_id().trim());

		// 订单中心 订单是否存在
		if (orderTree == null) {
			resp.setResp_msg("" + requ.getOrder_id() + ":订单不存在!");
			return resp;
		}

		// 校验物流编号
		if (StringUtils.isBlank(requ.getLogi_no())) {
			resp.setResp_msg("物流编号不能为空");
			return resp;
		}

		// 校验 物流公司编码
		if (StringUtils.isBlank(requ.getLogi_company_code())) {
			resp.setResp_msg("物流公司编码不能为空");
			return resp;
		}

		// 校验成功
		resp.setResp_code("0");

		return resp;
	}

	/**
	 * 获取请求的json数据
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 * @author GL
	 *
	 * @date 2019年4月30日
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream in = null;
		String requString = "";
		try {
			// 获取流
			in = request.getInputStream();
			// 转为string
			requString = IOUtils.toString(in, "utf-8").toString();
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
