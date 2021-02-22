package com.ztesoft.net.server.servlet.impl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.wyg.req.ChargebackApplyWYGRequset;
import zte.net.ecsord.params.wyg.resp.ChargebackApplyWYGResponse;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.server.servlet.ICommonServlet;
import commons.CommonTools;


/**
 * 沃云购 单状态同步处理类
 * 
 * @author zhangJun
 * 
 */
public class WYGOrderStatusSynServlet implements ICommonServlet {

	private final static Log log = LogFactory.getLog(WYGOrderStatusSynServlet.class);
	private  static String interfaceName="【沃云购单状态同步接收】";

	private String targetReqType;
	public WYGOrderStatusSynServlet(String targetReqType) {
		this.targetReqType = targetReqType;
	}
	

	@Override
	public String service(String reqString) throws Exception {
		log.info(interfaceName+"请求报文内容：" + reqString);
		JSONObject json = null;
		
		try {
			json = JSONObject.fromObject(reqString);
			
		} catch (Exception e) {
			log.info(interfaceName+"请求报文不满足JSON格式");
			throw new Exception(interfaceName+"请求报文不满足JSON格式");
		}
		
	
		String serialNo = null;
		String reqType = null;
		 String reqId =null;
		try{
			
			serialNo = json.getString("serial_no");// 流水号/序列号
			reqType = json.getString("reqType");
			reqId = json.getString("reqId");// 请求ID
	
			// 验证请求ID
			if (!CommonDataFactory.getInstance().checkCommonServletReq(reqId,reqType)) {
		         return   getResponseInfo(serialNo, EcsOrderConsts.WYG_INF_FAIL_CODE,"报文中repId["+reqId+"]验证不通过!");
		    }
			// 验证请求类型
			if (!targetReqType.equals(reqType)) {
				// 返回订单信息不存在
				return   getResponseInfo(serialNo, EcsOrderConsts.WYG_INF_FAIL_CODE,"同步失败,请求类型[reqType]非法!");
			}
	
				
        	ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        	ChargebackApplyWYGRequset zteRequest = CommonTools.jsonToBean(json.toString(),ChargebackApplyWYGRequset.class );
    		if(zteRequest==null){//转换出错
    			throw new Exception("json字符串转zteRequest对象转换出错,json字符串："+json.toString());
    		}else{
    			ChargebackApplyWYGResponse zteResponse =  client.execute(zteRequest, ChargebackApplyWYGResponse.class);
    			return  getResponseInfo(serialNo, zteResponse.getRespCode(), zteResponse.getRespMsg());
    		}
		}catch(Exception e){
			log.info(interfaceName+e);
			return getResponseInfo(serialNo, EcsOrderConsts.WYG_INF_FAIL_CODE, "处理请求异常："+e.getMessage());
		}
	}

	/**
	 * 
	 * @param respCode
	 *            结果码 0：成功 1：失败
	 * @param respMsg
	 * @return
	 * @throws IOException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 */
	private String getResponseInfo(String activeNo, String respCode,
			String respMsg) throws IOException, JsonGenerationException,
			JsonMappingException {
		String jsonString = "";
		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("serial_no", activeNo);
		resMap.put("resp_code", respCode);
		resMap.put("resp_msg", respMsg);
		ObjectMapper mapper = new ObjectMapper();
		jsonString = mapper.writeValueAsString(resMap);

		return jsonString;
	}
}
