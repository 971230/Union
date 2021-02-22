package com.ztesoft.net.server.servlet.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.sf.req.RouteServiceRequest;
import zte.net.ecsord.params.sf.resp.RouteServiceResponse;
import zte.net.ecsord.params.sf.vo.Route;
import zte.net.ecsord.params.sf.vo.RouteResponse;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.server.servlet.ICommonServlet;

/**
 * 顺丰路由查询
 * 
 * @author Rapon
 */
public class QuerySFRouteServlet implements ICommonServlet {
	private final static Log log = LogFactory.getLog(QuerySFRouteServlet.class);
	private  static String interfaceName="【供商城使用，直接调用顺丰接口】";


	private String targetReqType;
	public QuerySFRouteServlet(String targetReqType) {
		this.targetReqType = targetReqType;
	}

	/**
	 * 顺丰路由查询接口
	 */
	@Override
	public String service(String reqString) throws Exception {
		log.info(interfaceName + "请求报文内容：" + reqString);
		JSONObject json = null;

		try {
			json = JSONObject.fromObject(reqString);
		} catch (Exception e) {
			log.info(interfaceName + "请求报文不满足JSON格式");
			throw new Exception(interfaceName + "请求报文不满足JSON格式");
		}

		String serialNo = null;
		String reqType = null;
		String reqId = null;
		try {
			reqType = json.getString("reqType");
			reqId = json.getString("reqId");// 请求ID
			serialNo = json.getString("serial_no");
			
			/** paramsMap 集合中有两个字段,"jk_mark:接口标识符，params_json:参数对象" **/
			Map<String, String> paramsMap = toMap(json);
			
			if (!CommonDataFactory.getInstance().checkCommonServletReq(reqId, reqType)) {
				return getResponseInfo(serialNo, EcsOrderConsts.JKZF_VALID_FAIL_CODE, "报文中repId["+reqId+"]验证不通过!", "");
			}
			
			RouteServiceRequest req = new RouteServiceRequest();
			String inf_params = paramsMap.get("inf_params");
			JSONObject inf_params_json = JSONObject.fromObject(inf_params);
			Map<String, String> inf_params_map = toMap(inf_params_json);
			String out_order_id = inf_params_map.get("out_order_id");
			String order_id = CommonDataFactory.getInstance().getOrderTreeByOutId(out_order_id).getOrderExtBusiRequest().getOrder_id();
//			String order_id = out_order_id;
			req.setTracking_number(order_id);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			RouteServiceResponse resp = client.execute(req, RouteServiceResponse.class);
			List<RouteResponse> routeList = resp.getRouteResponseList();
			List<Route> routes = null;
			if (routeList != null && routeList.size() > 0) {
				RouteResponse routeRsp = routeList.get(0);
				routes = routeRsp.getRouteList();
			}
			JSONArray respMsg = JSONArray.fromObject(routes);
			return getResponseInfo(serialNo, EcsOrderConsts.JKZF_VALID_SUCC_CODE, "成功！", respMsg.toString());
		} catch (Exception e) {
			log.info(interfaceName + e);
			return getResponseInfo(serialNo, EcsOrderConsts.JKZF_VALID_FAIL_CODE, "处理请求异常：" + e.getMessage(), "");
		}
	}
	
	/**
	 * 
	 * @param respCode
	 *            结果码 0000：成功 0001：失败
	 * @param respMsg
	 * @return
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	private String getResponseInfo(String activeNo, String resp_code, 
			String resp_msg, String respMsg) throws IOException, JsonGenerationException,
			JsonMappingException {
		String jsonString = "";
		Map<String, Object> respMap = new HashMap<String, Object>();
		String time = "";
		try {
			time = DateUtil.getTime5();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		respMap.put("serial_no", activeNo);
		respMap.put("time", time);
		respMap.put("resp_code", resp_code);
		respMap.put("resp_msg", resp_msg);
		respMap.put("RouteInfo", respMsg);
		ObjectMapper mapper = new ObjectMapper();
		jsonString = mapper.writeValueAsString(respMap);

		return jsonString;
	}
	
	/**
	 * 将json对象转换成Map
	 * 
	 * @param jsonObject
	 *            json对象
	 * @return Map对象
	 */
	@SuppressWarnings("unchecked")
	private Map toMap(JSONObject jsonObject) {
		Map result = new HashMap();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
	}
}
