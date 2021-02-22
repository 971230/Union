package com.ztesoft.net.mall.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.IntentUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import net.sf.json.JSONObject;
import params.resp.OrderPreCreateCtnStandardResp;

/**
 * 商品预定接口
 * 
 *
 * @date 2018年8月30日
 */
public class OrderPreCreateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(OrderPreCreateServlet.class);
	
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

	@SuppressWarnings({ "rawtypes", "unused" })
	private void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String respJsonStr = "";// 返回json字符串
		String requJsonStr = "";// 请求json字符串
		String inJsonBody = "";
		OrderPreCreateCtnStandardResp resp = new OrderPreCreateCtnStandardResp();
		IDaoSupport<?> baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		
		try {
			requJsonStr = getRequestJson(request, response);
			logger.error("[OrderPreCreateServlet]-request:" + requJsonStr);
			if (!StringUtils.isEmpty(requJsonStr)) {
				Map map = (Map) JSON.parse(requJsonStr);

				if (!map.isEmpty()) {
					String order_type="";
					String contact_name="";
					String contact_phone="";
					String num_city="";
					String pspt_id="";
					String serial_number="";
					//想意向单中间表插入数据
					Map<Object, Object> intentMid = new HashMap<Object, Object>();
					@SuppressWarnings("unchecked")
					Map<Object, Object> REQ_HEAD = (Map<Object, Object>) map.get("REQ_HEAD");
					String order_id=Const.getStrValue(REQ_HEAD, "TOUCH_ORDER_ID");
					order_type=Const.getStrValue(REQ_HEAD, "STORE_TYPE");
					if(order_type.equals("01"))
						order_type="100101";
					@SuppressWarnings("unchecked")
					Map<Object, Object> REQ_DATA = (Map<Object, Object>) map.get("REQ_DATA");
					//获取POST_CERT_ID
					@SuppressWarnings("unchecked")
					Map<Object, Object> CUST_INFO = (Map<Object, Object>) REQ_DATA.get("CUST_INFO");
					if (!StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CERT_ID"))) {
						pspt_id= Const.getStrValue(CUST_INFO, "CERT_ID");
					}
					//获取CONTACT_NAME
					if (!StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CUST_NAME"))) {
						contact_name=Const.getStrValue(CUST_INFO, "CUST_NAME");
					}
					//获取CONTACT_PHONE
					if (!StringUtils.isEmpty(Const.getStrValue(CUST_INFO, "CONTACT_PHONE"))) {
						contact_phone=Const.getStrValue(CUST_INFO, "CONTACT_PHONE");
					}
					String sql = "select t.* from es_dc_public_ext t where t.stype='DIC_BLD_CITY_CODE'";
					List<Map> list = baseDaoSupport.queryForList(sql);
					// 正式环境只保留浙江省的地市，测试数据是全的
					num_city = Const.getStrValue(REQ_HEAD, "ORDER_CITY");
					for (Map<String, String> city_map : list) {
						if (num_city.equals(city_map.get("pkey"))) {
							num_city = city_map.get("codea");
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
					
					/*String desc = cheak(map);
					if (StringUtils.isEmpty(desc)) {
						OrderPreCreateCtnStandardReq requ = new OrderPreCreateCtnStandardReq();
						requ.setMapReq(map);
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						resp = client.execute(requ, OrderPreCreateCtnStandardResp.class);
					} else {
						resp.setCODE("1002");
						resp.setDESC(desc);
					}*/
				} else {
					resp.setCODE("1002");
					resp.setDESC("请求报文参数异常");
					return ;
				}
			} else {
				resp.setCODE("1001");
				resp.setDESC("请求报文为空");
				return ;
			}
		} catch (Exception e) {
			resp.setCODE("2002");
			resp.setDESC("异常错误1：" + (e.getMessage() == null ? e.toString() : e.getMessage()));
			e.printStackTrace();
			logger.info(e, e);
		}
		Map<String, Object> respMap = new HashMap<String, Object>();
		if ("0000".equals(resp.getCODE())) {
			respMap.put("DATA", resp.getDATA());
		}
		respMap.put("CODE", resp.getCODE());
		respMap.put("DESC", resp.getDESC());
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("ORDER_PRE_CREATE_RSP", respMap);
		// 将返回数据转成json格式的字符串
		respJsonStr = JSONObject.fromObject(respMap).toString();
		// 返回接口调用结果
		logger.info("[OrderPreCreateServlet]-response:" + respJsonStr);
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
	public String cheak(Map mapReq) {
		if (mapReq.get("REQ_HEAD") == null) {
			return "REQ_HEAD为空";
		}
		if (mapReq.get("REQ_DATA") == null) {
			return "REQ_DATA为空";
		}
		Map mapReqHead = (Map) mapReq.get("REQ_HEAD");
		if (StringUtils.isEmpty(Const.getStrValue(mapReqHead, "TOUCH_ORDER_ID"))) {
			return "TOUCH_ORDER_ID为空";
		} else {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_order_intent where intent_order_id ='"
					+ Const.getStrValue(mapReqHead, "TOUCH_ORDER_ID") + "'";
			List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {
				return "TOUCH_ORDER_ID重复";
			}
		}
		if (StringUtils.isEmpty(Const.getStrValue(mapReqHead, "IN_MODE_CODE"))) {
			return "IN_MODE_CODE为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(mapReqHead, "ORDER_PROVINCE"))) {
			return "ORDER_PROVINCE为空";
		}
		if (StringUtils.isEmpty(Const.getStrValue(mapReqHead, "ORDER_TIME"))) {
			return "ORDER_TIME为空";
		}
		Map mapReqData = (Map) mapReq.get("REQ_DATA");
		if (mapReqData.get("COMM_OBJECT") != null) {
			List mapReqDataCommObject = (List) mapReqData.get("COMM_OBJECT");
			for (Object obj : mapReqDataCommObject) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "COMM_ID"))) {
					return "COMM_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "COMM_COUNT"))) {
					return "COMM_COUNT为空";
				}
			}
		} else {
			return "COMM_OBJECT为空";
		}
		if (mapReqData.get("CUST_INFO") != null) {
			Map mapReqDataCust = (Map) mapReqData.get("CUST_INFO");
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust, "CUST_NAME"))) {
				return "CUST_NAME为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust, "CONTACT_PHONE"))) {
				return "CONTACT_PHONE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust, "POST_ADDRESS"))) {
				return "POST_ADDRESS为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust, "CERT_TYPE"))) {
				return "CERT_TYPE为空";
			}
			if (StringUtils.isEmpty(Const.getStrValue(mapReqDataCust, "CERT_ID"))) {
				return "CERT_ID为空";
			}
		}
		if (mapReqData.get("DEVELOPER_INFO") != null) {
			List mapReqDataDeveloper = (List) mapReqData.get("DEVELOPER_INFO");
			for (Object obj : mapReqDataDeveloper) {
				Map map = (Map) obj;
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_TYPE"))) {
					return "DEVELOP_TYPE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_EPARCHY_CODE"))) {
					return "DEVELOP_EPARCHY_CODE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_STAFF_ID"))) {
					return "DEVELOP_STAFF_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_DATE"))) {
					return "DEVELOP_DATE为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_MANAGER_ID"))) {
					return "DEVELOP_MANAGER_ID为空";
				}
				if (StringUtils.isEmpty(Const.getStrValue(map, "DEVELOP_MANAGER_NAME"))) {
					return "DEVELOP_MANAGER_NAME为空";
				}
			}
		}

		return "";
	}

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
