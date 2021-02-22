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

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.IntentUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import net.sf.json.JSONObject;
import params.req.OrderDistributeCtnStandardReq;
import params.resp.OrderDistributeCtnStandardResp;

/**
 * 2.27. 订单下发接口
 * 
 *
 * @date 2018年11月15日
 */
public class OrderDistributeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(OrderDistributeServlet.class);
	
	private IntentUtil intentUtil=new IntentUtil();

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

	@SuppressWarnings("unchecked")
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		OrderDistributeCtnStandardResp resp = new OrderDistributeCtnStandardResp();
		IDaoSupport<?> baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		try {
			requJsonStr = getRequestJson(request, response);
			logger.error("[OrderDistributeServlet]-request:" + requJsonStr);
			if (!StringUtils.isEmpty(requJsonStr)) {
				Map map = (Map) JSON.parse(requJsonStr);
				
				if (!map.isEmpty()) {
					//总部蜂行动：100104
					String order_type="100104";
					String contact_name="";
					String contact_phone="";
					String num_city="";
					String pspt_id="";
					String serial_number="";
					//想意向单中间表插入数据
					Map<Object, Object> intentMid = new HashMap<Object, Object>();
					Map<Object, Object> ORDER = (Map<Object, Object>) map.get("ORDER");
					String order_id=Const.getStrValue(ORDER, "ORDER_ID");
					Map<Object, Object> CUST_INFO = (Map<Object, Object>) ORDER.get("CUST_INFO");
					//获取POST_CERT_ID
					String temp=Const.getStrValue(CUST_INFO, "PSPT_ID");
					if (!StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "PSPT_ID"))) {
						pspt_id= Const.getStrValue(CUST_INFO, "PSPT_ID");
					}
					//获取CONTACT_NAME
					if (!StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CONTACT_NAME"))) {
						contact_name=Const.getStrValue(CUST_INFO, "CONTACT_NAME");
					}
					//获取CONTACT_PHONE
					if (!StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CONTACT_PHONE"))) {
						contact_phone=Const.getStrValue(CUST_INFO, "CONTACT_PHONE");
					}
					if (null != ORDER.get("ORDER_LINE")) {
						List ORDER_LINE = (List) ORDER.get("ORDER_LINE");
						for (Object obj : ORDER_LINE) {
							Map<Object, Object> line_map = (Map<Object, Object>) obj;
							if (!StringUtils.isEmpty(Const.getStrValue(line_map, "SERIAL_NUMBER"))) {
								serial_number=Const.getStrValue(line_map, "SERIAL_NUMBER");
							}
							Map<Object,Object> MPHONE_INFO=(Map) line_map.get("MPHONE_INFO");
							if (!StringUtils.isEmpty(Const.getStrValue(line_map, "SERIAL_NUMBER"))) {
								num_city=Const.getStrValue(MPHONE_INFO, "NUM_CITY");
							}
						}
					}
					String sql = "select t.* from es_dc_public_ext t where t.stype='DIC_FXD_CITY_CODE'";
					List<Map> list = baseDaoSupport.queryForList(sql);
					// 正式环境只保留浙江省的地市，测试数据是全的
					for (Map<String, String> city_map : list) {
						if (num_city.equals(city_map.get("pkey"))) {
							num_city = city_map.get("pname");
							break;
						}
					}
					
					intentMid.put("pspt_id", pspt_id);
					intentMid.put("order_id", order_id);
					intentMid.put("order_type", order_type);
					intentMid.put("contact_name", contact_name);
					intentMid.put("contact_phone", contact_phone);
					intentMid.put("serial_number", serial_number);
					intentMid.put("num_city", num_city);
					intentMid.put("req", requJsonStr);
					
					intentUtil.intentMidOrderCreate(intentMid);
					
					return ;
					
				} else {
					resp.setRESP_CODE("0");
					resp.setRESP_DESC("请求报文参数异常");
					return ;
				}
			} else {
				resp.setRESP_CODE("0");
				resp.setRESP_DESC("请求报文为空");
				return ;
			}
		} catch (Exception e) {
			resp.setRESP_CODE("0");
			resp.setRESP_DESC("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}

		try {
			requJsonStr = getRequestJson(request, response);
			logger.error("[OrderDistributeServlet]-request:" + requJsonStr);
			if (!StringUtils.isEmpty(requJsonStr)) {
				Map map = (Map) JSON.parse(requJsonStr);

				if (!map.isEmpty()) {
					String desc = cheak(map);
					
					if (StringUtils.isEmpty(desc)) {
						OrderDistributeCtnStandardReq requ = new OrderDistributeCtnStandardReq();
						requ.setMapReq(map);
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						resp = client.execute(requ, OrderDistributeCtnStandardResp.class);
					} else {
						resp.setRESP_CODE("0");
						resp.setRESP_DESC(desc);
					}
				} else {
					resp.setRESP_CODE("0");
					resp.setRESP_DESC("请求报文参数异常");
				}
			} else {
				resp.setRESP_CODE("0");
				resp.setRESP_DESC("请求报文为空");
			}
		} catch (Exception e) {
			resp.setRESP_CODE("0");
			resp.setRESP_DESC("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String, Object> respMap = new HashMap<String, Object>();
		if ("1".equals(resp.getRESP_CODE())) {
			List<Map> DATA = new ArrayList<Map>();
			DATA.add(resp.getDATA());
			respMap.put("DATA", DATA);
		}
		respMap.put("RESP_CODE", resp.getRESP_CODE());
		respMap.put("RESP_DESC", resp.getRESP_DESC());
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("ORDER_PRE_CREATE_RSP", respMap);
		// 将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		// 返回接口调用结果
		logger.info("[OrderDistributeServlet]-response:" + respJsonStr);
		PrintWriter out = response.getWriter();
		out.print(respJsonStr);
		out.close();
	}

	/**
	 * 校验参数是否异常
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String cheak(Map mapReq) {
		if (mapReq.get("ORDER") == null) {
			return "ORDER为空";
		}
		Map<Object, Object> ORDER = (Map<Object, Object>) mapReq.get("ORDER");
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "ORDER_ID"))) {
			return "ORDER_ID为空";
		} else {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_order_intent where intent_order_id ='" + Const.getStrValue(ORDER, "ORDER_ID")
					+ "'";
			List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {
				return "ORDER_ID重复";
			}
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "IN_MODE_CODE"))) {
			return "IN_MODE_CODE为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "CHANNEL_ID"))) {
			return "CHANNEL_ID为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "CREATE_DATE"))) {
			return "CREATE_DATE为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(ORDER, "PAY_TAG"))) {
			return "PAY_TAG为空";
		}
		if (null != ORDER.get("ORDER_ATTR")) {
			List ORDER_ATTR = (List) ORDER.get("ORDER_ATTR");
			for (Object obj : ORDER_ATTR) {
				@SuppressWarnings("unchecked")
				Map<Object, Object> map = (Map<Object, Object>) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "ATTR_CODE"))) {
					return "ATTR_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "ATTR_VALUE"))) {
					return "ATTR_VALUE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "ATTR_VALUE_NAME"))) {
					return "ATTR_VALUE_NAME为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "ACTION"))) {
					return "ACTION为空";
				}
			}
		}
		if (null != ORDER.get("DEVELOPER_INFO")) {
			Map<Object, Object> DEVELOPER_INFO = (Map<Object, Object>) ORDER.get("DEVELOPER_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO, "DEVELOP_TYPE"))) {
				return "DEVELOP_TYPE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO, "DEVELOP_EPARCHY_CODE"))) {
				return "DEVELOP_EPARCHY_CODE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO, "DEVELOP_STAFF_ID"))) {
				return "DEVELOP_STAFF_ID为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DEVELOPER_INFO, "DEVELOP_DATE"))) {
				return "DEVELOP_DATE为空";
			}
		}
		if (null != ORDER.get("COMMON_INFO")) {
			List COMMON_INFO = (List) ORDER.get("COMMON_INFO");
			for (Object obj : COMMON_INFO) {
				Map<Object, Object> map = (Map<Object, Object>) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEPART_ID"))) {
					return "DEPART_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "CITY_CODE"))) {
					return "CITY_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "EPARCHY_CODE"))) {
					return "EPARCHY_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "PROVINCE_CODE"))) {
					return "PROVINCE_CODE为空";
				}
			}
		}
		if (null != ORDER.get("FEEPAY_INFO")) {
			List FEEPAY_INFO = (List) ORDER.get("FEEPAY_INFO");
			for (Object obj : FEEPAY_INFO) {
				Map<Object, Object> map = (Map<Object, Object>) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "PAY_ID"))) {
					return "PAY_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "PAY_MONEY_CODE"))) {
					return "PAY_MONEY_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "MONEY"))) {
					return "MONEY为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "PAY_CHANNEL"))) {
					return "PAY_CHANNEL为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "PAY_DATE"))) {
					return "PAY_DATE为空";
				}
			}
		}
		if (null != ORDER.get("CUST_INFO")) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> CUST_INFO = (Map<Object, Object>) ORDER.get("CUST_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CUST_NAME"))) {
				return "CUST_NAME为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "SEX"))) {
				return "SEX为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CUST_PHONE"))) {
				return "CUST_PHONE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "POST_ADDRESS"))) {
				return "POST_ADDRESS为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "PSPT_ID"))) {
				return "PSPT_ID为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "PSPT_TYPE_CODE"))) {
				return "PSPT_TYPE_CODE为空";
			}
		}
		if (null != ORDER.get("DELIVER_INFO")) {
			Map<Object, Object> DELIVER_INFO = (Map<Object, Object>) ORDER.get("DELIVER_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(DELIVER_INFO, "POST_MOBILE_1"))) {
				return "POST_MOBILE_1为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DELIVER_INFO, "POST_PROVINCE"))) {
				return "POST_PROVINCE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DELIVER_INFO, "POST_CITY"))) {
				return "POST_CITY为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(DELIVER_INFO, "POST_ADDRESS"))) {
				return "POST_ADDRESS为空";
			}
		}
		if (null != ORDER.get("ORDER_LINE")) {
			List ORDER_LINE = (List) ORDER.get("ORDER_LINE");
			for (Object obj : ORDER_LINE) {
				Map<Object, Object> map = (Map<Object, Object>) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "ORDER_LINE_ID"))) {
					return "ORDER_LINE_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "SERIAL_NUMBER"))) {
					return "SERIAL_NUMBER为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "TRADE_TYPE_CODE"))) {
					return "TRADE_TYPE_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "CANCEL_TAG"))) {
					return "CANCEL_TAG为空";
				}
			}
		}
		if (null != ORDER.get("INVOICE_INFO")) {
			List INVOICE_INFO = (List) ORDER.get("INVOICE_INFO");
			for (Object obj : INVOICE_INFO) {
				Map<Object, Object> map = (Map<Object, Object>) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "TICKET_TYPE_CODE"))) {
					return "TICKET_TYPE_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "TICKET_ID"))) {
					return "TICKET_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "INVOICE_CHECK_CODE"))) {
					return "INVOICE_CHECK_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "INVOICE_HEADER"))) {
					return "INVOICE_HEADER为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "RECEIVE_MESSAGE_NUM"))) {
					return "RECEIVE_MESSAGE_NUM为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "BUYER_TAXPAYER_ID"))) {
					return "BUYER_TAXPAYER_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "FEE"))) {
					return "FEE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "IS_PRINT"))) {
					return "IS_PRINT为空";
				}
			}
		}

		return "";
	}

	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletInputStream in = null;
		String requString = "";
		try {
			in = request.getInputStream();
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
