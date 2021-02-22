package com.ztesoft.net.mall.server.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.XmlUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.consts.OrderCtnConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.OpenServiceCfg;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import commons.CommonTools;
import consts.ConstsCore;
import params.orderqueue.resp.OrderCollectionResp;
import zte.net.iservice.IOrderCtnService;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;
import zte.params.orderctn.vo.HttpRespInfo;
import zte.params.orderctn.vo.HttpRespParame;

/**
 * 
 * @Package com.ztesoft.net.mall.server.servlet
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhouqiangang
 * @date 2015年11月30日 下午2:51:35
 */
public class HttpServiceHandle extends HttpServlet {

	private static Logger logger = Logger.getLogger(HttpServiceHandle.class);

	private IOrderCtnService orderCtnService;
	private IOrderQueueBaseManager orderQueueBaseManager;

	private static final long serialVersionUID = 1L;

	private ICacheUtil cacheUtil;

	private void initBean() {
		if(null == orderCtnService) {
			orderCtnService = SpringContextHolder.getBean("orderCtnService");
		}
		if (null == orderQueueBaseManager) {
			orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		}
		if(null == cacheUtil) {
			cacheUtil = SpringContextHolder.getBean("cacheUtil");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		service(req, resp);
	}

	/**
	 * 服务版本号固定为1.0
	 */
	private String version = "1.0";

	@Override
	@SuppressWarnings({ "unchecked" })
	public void service(HttpServletRequest request, HttpServletResponse response) {
		long req_time = System.currentTimeMillis();
		String ip = request.getRemoteAddr();
		OrderCtnResp ckResp = new OrderCtnResp();
		final String out_order_id;
		Map<String, Object> reqParamsMap = null;
		try {

			long begin = System.currentTimeMillis();
			initBean();
			// 1、获取请求参数
			String reqSourceMsgStr = getReqContent(request);
			final String reqMsgXmlStr = getInReq(reqSourceMsgStr);// 需要去除xml头部
			logger.info("[HttpServiceHandle] 去除xml头部信息报文:"+reqMsgXmlStr);
			// 2、报文转Map,只需要转换一次
			if (reqMsgXmlStr.indexOf("<root>") > -1) {
				reqParamsMap = XmlUtils.xmlToMap(reqMsgXmlStr);
			} else {
				reqParamsMap = XmlUtils.xmlToMap("<root>" + reqMsgXmlStr + "</root>");
			}
			out_order_id = "000000";
			//3、获取外部接口编码
			String outServiceCode = null;
			if(null != reqParamsMap && !reqParamsMap.isEmpty()){
				outServiceCode = getOutServiceCode(reqParamsMap);
				if(StringUtils.isEmpty(outServiceCode)){
					CommonTools.addBusiError(ConstsCore.ERROR_FAIL, "从报文 中解析接口编码失败！");
				}
			}else{
				CommonTools.addBusiError(ConstsCore.ERROR_FAIL, "请求格式有误！");
			}
			
			OrderCtnReq req = new OrderCtnReq();
			req.setReqParamsMap(reqParamsMap);
			req.setOutServiceCode(outServiceCode);
			req.setReqMsgStr(reqMsgXmlStr);
			req.setVersion(version);
			req.setIsTemplateCoversion(true);
			req.setFormat(OrderCtnConsts.ORDER_QUEUE_MSG_TYPE_XML);
			//4、调用处理服务进行xml转换
			
			ckResp = orderCtnService.orderCtn(req);
			if (null != ckResp && StringUtils.isEmpty(ckResp.getBase_co_id())) {
				final String out_param ;
				String co_id = ckResp.getBase_co_id();
				if (ConstsCore.ERROR_SUCC.equals(ckResp.getError_code())) {
					out_param = "队列ID为[" + co_id + "]订单标准化成功。";
				} else {
					out_param = "队列ID为[" + co_id + "]订单标准化失败。";
				}
				String esearch_flag = "0";//默认关闭
				esearch_flag = cacheUtil.getConfigInfo(EcsOrderConsts.ESEARCH_FLAG);//是否取消前置校验 0：关闭 1：开启
				if("1".equals(esearch_flag)) {
					final OrderCtnResp ctnResp = ckResp;
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							orderQueueBaseManager.writeEsearch(ctnResp.getBase_co_id(), reqMsgXmlStr, out_param,out_order_id);
						}
					});
					thread.setName("HttpServiceHandleWriteEsearchThread");
					thread.start();
				}
				
			}
			
			long end = System.currentTimeMillis();
			logger.info("[HttpServiceHandle] 步骤1-> 订单归集:写入队列、消息消费消耗时常------>" + (end - begin));
			//5、 处理返回结果
			HttpRespParame parame = new HttpRespParame();
			parame.setCo_id(ckResp.getBase_co_id());
			parame.setInReq(ckResp.getReqMsgStr());
			parame.setMsg(ckResp.getError_msg());
			parame.setOrder_id(ckResp.getBase_order_id());
			parame.setOut_service_code(ckResp.getOut_service_code());
			parame.setIp(ip);
			if (!ConstsCore.ERROR_SUCC.equals(ckResp.getError_code())) {
				parame.setResp_code(OrderCtnConsts.HTTP_RESP_CODE_FAIL);
				parame.setResp_result(OrderCtnConsts.HTTP_RESP_RESULT_FAIL);

			} else {
				parame.setResp_code(OrderCtnConsts.HTTP_RESP_CODE_SUCC);
				parame.setResp_result(OrderCtnConsts.HTTP_RESP_RESULT_SUCC);
			}
			exex_result(response, ckResp.getOrderCollectList(), ckResp.getReqParamsMap(), ckResp.getCfg(), parame,
					req_time);

		} catch (Exception e) {
			e.printStackTrace();
			HttpRespParame parame = new HttpRespParame();
			parame.setInReq(ckResp.getReqMsgStr());
			parame.setMsg(OrderCtnConsts.HTTP_RESP_MSG_FAIL + ",服务入口异常！:" + e.getMessage());
			parame.setOut_service_code(ckResp.getOut_service_code());
			parame.setResp_code(OrderCtnConsts.HTTP_RESP_CODE_FAIL);
			parame.setResp_result(OrderCtnConsts.HTTP_RESP_RESULT_FAIL);
			parame.setIp(ip);
			exex_result(response, null, ckResp.getReqParamsMap(), ckResp.getCfg(), parame, req_time);
		}
	}

	private void exex_result(HttpServletResponse response, List<OrderCollectionResp> ocList,
			Map<String, Object> inParams, OpenServiceCfg cfg, HttpRespParame parame, long req_time) {
		String respStr = null;
		Object order_from = null;
		HttpRespInfo httpRespInfo = new HttpRespInfo();
		if (inParams != null) {
			Object obj = inParams.get("operation_in");
			if (obj instanceof HashMap) {
				order_from = ((Map) obj).get("request_source");
			}
			httpRespInfo = getHttpRespInfo(inParams, httpRespInfo);
		}
		httpRespInfo.setProcess_code(parame.getOut_service_code());
		httpRespInfo.setResp_code(parame.getResp_code());
		httpRespInfo.setResp_result(parame.getResp_result());
		httpRespInfo.setResp_desc(parame.getMsg());
		if (ocList != null && !ocList.isEmpty()) {
			List<Map<String, Object>> infOutList = new ArrayList<Map<String, Object>>();
			for (OrderCollectionResp oc : ocList) {
				if (null == oc.getInfOutMap())
					continue;
				infOutList.add(oc.getInfOutMap());
			}
			String contentStr = XmlUtils.listToXml(infOutList);// 还有多余的root节点
			contentStr = contentStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
			if (contentStr != null) {
				contentStr = contentStr.substring(7);
				if (contentStr.length() > 7) {
					contentStr = contentStr.substring(0, contentStr.length() - 8);
				}
				httpRespInfo.setContent(contentStr);
			}
		}
		respStr = getRespStr(httpRespInfo);
		if (cfg == null || OrderCtnConsts.BASE_YES_FLAG_1.equals(cfg.getWrite_logs_flag())) {// 是否写接口日志z
			try {
				// 写接口
				final long _req_time = req_time;
				final String _inReq = parame.getInReq();
				final String _respString = respStr;
				final String _order_id = parame.getOrder_id();
				final String _service_code = parame.getOut_service_code();
				final String _order_from = order_from == null ? null : (String) order_from;
				final String ip = parame.getIp();
				new Thread(new Runnable() {
					@Override
					public void run() {
						insertInfLog(_inReq, _respString, _order_id, _order_from, _service_code, ip, _req_time);
					}
				}).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response(response, respStr);
	}

	/**
	 * 获取请求的报文内容
	 * 
	 * @param request
	 * @throws IOException
	 * @throws Exception
	 */
	private String getReqContent(HttpServletRequest request) throws Exception {
		InputStream input = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
		StringBuilder buffer = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String buStr = buffer.toString();
		return buStr;
	}

	/**
	 * 响应报文请求
	 * 
	 * @param response
	 *            HttpServletResponse对象
	 * @param respStr错误描述信息
	 */
	private void response(HttpServletResponse response, String respStr) {
		try {
			respStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + respStr;
			response.setContentType("text/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			OutputStream out = response.getOutputStream();
			out.write(respStr.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回的报文头部信息，硬编码报文用于异常情况返回。
	 * 
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String getRespStr(HttpRespInfo httpRespInfo) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String respTime = df.format(new Date());
		StringBuilder xml = new StringBuilder();
		xml.append("<operation_out>");
		xml.append("<process_code>" + httpRespInfo.getProcess_code() + "</process_code>");
		xml.append("<sysfunc_id>" + httpRespInfo.getSysfunc_id() + "</sysfunc_id>");
		xml.append("<response_time>" + respTime + "</response_time>");
		xml.append("<request_source>" + httpRespInfo.getRequest_source() + "</request_source>");
		xml.append("<response_seq>" + httpRespInfo.getResponse_seq() + "</response_seq>");
		xml.append("<request_type>" + httpRespInfo.getRequest_type() + "</request_type>");
		xml.append("<response>");
		xml.append("<resp_result>" + httpRespInfo.getResp_result() + "</resp_result>");
		xml.append("<resp_code>" + httpRespInfo.getResp_code() + "</resp_code>");
		xml.append("<resp_desc>" + httpRespInfo.getResp_desc() + "</resp_desc>");
		xml.append("</response>");
		xml.append(httpRespInfo.getContent());
		xml.append("</operation_out>");
		return xml.toString();
	}

	/**
	 * 接口日志
	 * 
	 * @Description:
	 * @param @param reqXml
	 * @param @param respXml
	 * @param @param order_id
	 * @param @param opCode
	 * @return void
	 * @throws
	 */
	private void insertInfLog(String reqXml, String respXml, String order_id, String order_from, String opCode,
			String ip, long req_time) {
		// InfServiceLogReq req=new InfServiceLogReq();
		// req.setReqXml(reqXml);
		// req.setRespXml(respXml);
		// Map map=new HashMap();
		// map.put("req_time", String.valueOf(req_time));
		// map.put("orderId", order_id);
		// map.put("orderFrom", order_from);
		// map.put("opCode", opCode);
		// map.put("ip", ip);
		// req.setOrderMap(map);
		// ZteClient client =
		// ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		// client.execute(req, InfServiceLogResp.class);
	}

	public static final String OPERATION_IN = "operation_in";
	public static final String REQUEST_SEQ = "request_seq";
	public static final String REQUEST_TYPE = "request_type";
	public static final String SYSFUNC_ID = "sysfunc_id";
	public static final String REQUEST_SOURCE = "request_source";

	private HttpRespInfo getHttpRespInfo(Map<String, Object> map, HttpRespInfo httpRespInfo) {
		try {
			// 1、从报文获取接口编码
			map = (Map<String, Object>) map.get(OPERATION_IN);
			httpRespInfo.setRequest_source((String) map.get(REQUEST_SOURCE));
			httpRespInfo.setRequest_type((String) map.get(REQUEST_TYPE));
			httpRespInfo.setSysfunc_id((String) map.get(SYSFUNC_ID));
			httpRespInfo.setResponse_seq((String) map.get(REQUEST_SEQ));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return httpRespInfo;
	}

	public static void main(String[] args) {

		Map map = XmlUtils
				.xmlToMap("<root><content><invoice_info_dt><invoice_type>1</invoice_type></invoice_info_dt></content></root>");
		// utils.SystemUtils.printLog("1");

		// String
		// test="<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response>ss</Response>";
		// HttpServiceHandle htt=new HttpServiceHandle();
		// try {
		// BigDecimal b1 = new BigDecimal(Double.valueOf("100"));
		// BigDecimal b2 = new BigDecimal(Double.valueOf("334.45"));
		//
		// Double doubleVal= b1.multiply(b2).doubleValue();//乘法
		// utils.SystemUtils.printLog(String.valueOf(doubleVal));
		// Double doubleVal2= b2.divide(b1,
		// 3,BigDecimal.ROUND_UP).doubleValue();;//除法
		// utils.SystemUtils.printLog(String.valueOf(doubleVal2));
		// utils.SystemUtils.printLog();
		// } catch (Exception e) {
		// // TODO Auto-generated catch
		// e.printStackTrace();
		// }
	}

	/**
	 * 读取xml文档，去除头部 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
	 * 
	 * @param xmlReq
	 * @return
	 * @throws ApiBusiException
	 */
	public String getInReq(String xmlReq) throws ApiBusiException {
		// 解析报文内容
		String InReq = "";
		Element root = null;
		try {
			SAXReader readerDom = new SAXReader();
			Document doc = readerDom.read(new ByteArrayInputStream(xmlReq.getBytes("UTF-8")));
			root = doc.getRootElement();
			InReq = root.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addBusiError(ConstsCore.ERROR_FAIL, "请求格式有误！");
		}
		return InReq;
	}
	
	/**
	 * 根据数据库配置的路径从报文Map对象解析对外的接口编码
	 * 
	 * @Description:
	 * @param @param map 报文Map对象
	 * @param @return
	 * @return String 接口编码
	 * @throws
	 */
	public String getOutServiceCode(Map<String, Object> map) {
		String out_service_code = null;
		try {
			// 从报文获取接口编码
			ICacheUtil cacheUtil1 = SpringContextHolder.getBean("cacheUtil");
			String service_code_paths = cacheUtil1.getConfigInfo(OrderCtnConsts.SERVICE_CODE_PATH);
			String[] service_code_path_array = service_code_paths.split("/");
			for (int i = 0; i < service_code_path_array.length; i++) {
				if (service_code_path_array.length - 1 != i) {
					map = (Map<String, Object>) map.get(service_code_path_array[i]);
				} else {
					out_service_code = (String) map.get(service_code_path_array[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out_service_code;
	}
}
