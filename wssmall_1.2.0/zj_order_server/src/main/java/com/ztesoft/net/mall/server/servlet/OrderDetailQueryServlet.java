package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderDetailReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderDetailResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderDetailVO;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 订单信息查询接口 wssmall.order.detailInfo.query
 * 
 * @author song.qi 2017年12月25日
 */
public class OrderDetailQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 3411911039617039211L;

	private static Logger logger = Logger.getLogger(OrderDetailQueryServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		OrderDetailReq requ = null;
		OrderDetailResp resp = null;
		try {
			// 获取请求的json数据
			requJsonStr = getRequestJson(request, response);
			logger.info("[DetailInfoQueryServlet] 请求报文-request:" + requJsonStr);
			// 解析请求参数
			JSONObject requJson = JSONObject.fromObject(requJsonStr);
			// 请求入参
			JSONObject orderInfoJson = requJson.getJSONObject("order_info_qry_req");
			// requ = (OrderDetailReq) JSONObject.toBean(orderInfoJson,
			// OrderDetailReq.class);
			//列表转换会类型异常
			JSONArray list = orderInfoJson.getJSONArray("query_info");
			if (null != list && list.size() > 0) {
				List<OrderDetailVO> query_info =new ArrayList<OrderDetailVO>();
				OrderDetailVO vo = null;
				for(Object o:list){
					Map<String,String> map = (Map<String,String>)o;
					vo = new OrderDetailVO();
					vo.setOrder_id(map.get("order_id"));
					query_info.add(vo);
				}
				requ = new OrderDetailReq();
				requ.setQuery_info(query_info);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				resp = client.execute(requ, OrderDetailResp.class);
			}else{
				resp.setResp_code("1");
				resp.setResp_msg("query_info节点为空");
			}
		} catch (Exception e) {
			resp = new OrderDetailResp();
			resp.setResp_code("1");
			resp.setResp_msg("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resp_code", resp.getResp_code());
		map.put("resp_msg", resp.getResp_msg());
		map.put("query_resp", resp.getQuery_resp());
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("order_info_qry_rsp", map);
		// 将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		// 返回接口调用结果
		logger.info("[DetailInfoQueryServlet] 响应报文-response:" + respJsonStr);
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
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月27日
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
