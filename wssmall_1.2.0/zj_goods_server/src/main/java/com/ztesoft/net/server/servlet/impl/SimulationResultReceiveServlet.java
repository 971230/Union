package com.ztesoft.net.server.servlet.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.sr.req.SimulationResultReceiveRequset;
import zte.net.ecsord.params.sr.resp.SimulationResultReceiveResponse;

import com.google.gson.Gson;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.server.servlet.ICommonServlet;

/**
 * 森锐通知仿真回单结果
 * 
 * @author Rapon
 */
public class SimulationResultReceiveServlet implements ICommonServlet {
	private final static Log log = LogFactory.getLog(SimulationResultReceiveServlet.class);
	private  static String interfaceName="【森锐通知仿真结果】";


	private String targetReqType;
	public SimulationResultReceiveServlet(String targetReqType) {
		this.targetReqType = targetReqType;
	}

	/**
	 * 森锐通知仿真回单结果
	 */
	@Override
	public String service(String reqString) throws Exception {
		log.info(interfaceName+"请求的字符串："+reqString);
		log.info(interfaceName+"请求的字符串："+reqString);
		String returnString = null;
		SimulationResultReceiveResponse zteResponse = new SimulationResultReceiveResponse();
		zteResponse.setResp_code(EcsOrderConsts.SIMULATION_RECEIVE_RUSULT_F);//默认失败
		SimulationResultReceiveRequset req = null;
		if(StringUtils.isNotBlank(reqString)) {
			Gson gson = new Gson();
			try {
				req = gson.fromJson(reqString, SimulationResultReceiveRequset.class);
				String error_msg = checkReq(req);
				if(!targetReqType.equals(req.getReqType())){
					zteResponse.setResp_msg("请求类型[reqType]非法!");
				}else if(!CommonDataFactory.getInstance().checkCommonServletReq(req.getReqId(),req.getReqType())){
					zteResponse.setResp_msg("您无权访问此接口!");
				}else if(!"".equals(error_msg)){
					zteResponse.setResp_msg(error_msg);
				}else{
					zteResponse = dealOtherDealUser(req);//业务处理
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info(interfaceName+"解析JSON字符串出错");
				log.info(interfaceName+"解析JSON字符串出错");
				zteResponse.setResp_msg("解析JSON字符串出错");
			}
		} else {
			zteResponse.setResp_msg("请求参数为空");
		}

		String resp_msg = zteResponse.getResp_msg();
		if(null!=resp_msg&&resp_msg.length()>200){//返回信息限制在200字内
			resp_msg = resp_msg.substring(0,200);
		}
		String serial_no = "";
		String time = "";
		try{
			time = DateUtil.getTime5();
			serial_no = req.getSerial_no();
		}catch(Exception e){
			//什么都没做
		}
		returnString = "{\"serial_no\":\""+serial_no+"\",\"time\":\""+time+"\",\"resp_code\":\""+zteResponse.getResp_code()+"\",\"resp_msg\":\""+resp_msg+"\"}";
		log.info(interfaceName+"返回的字符串："+returnString);
		log.info(interfaceName+"返回的字符串："+returnString);
		return returnString;
	}

	/**
	 * 具体业务处理
	 * @param req
	 * @return
	 */
	public SimulationResultReceiveResponse dealOtherDealUser(SimulationResultReceiveRequset req)throws Exception  {
		SimulationResultReceiveResponse zteResponse = new SimulationResultReceiveResponse();
		zteResponse.setResp_code(EcsOrderConsts.SIMULATION_RECEIVE_RUSULT_F);//默认失败
		try {
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			zteResponse = client.execute(req, SimulationResultReceiveResponse.class);//业务处理
		} catch (Exception e) {
			e.printStackTrace();
			log.info(interfaceName+"接口处理错误描述:"+e.getMessage());
			zteResponse.setResp_msg("接口处理错误");
		}
		return zteResponse;
	}
	

	/**
	 * 检验必填参数及格式
	 * @param req
	 * @return
	 */
	private static String checkReq(SimulationResultReceiveRequset req){
		StringBuffer strBuf = new StringBuffer();
		if(StringUtils.isEmpty(req.getSerial_no())){
			strBuf.append("缺少必填字段serial_no.");
		}
		if(StringUtils.isEmpty(req.getTime())){
			strBuf.append("缺少必填字段time.");
		}else{
			strBuf.append(checkTimeFormat("time",req.getTime(),DateUtil.DATE_FORMAT_5));
		}
		//source_system、receive_system暂不校验，编码未确定
//		if(StringUtils.isEmpty(req.getSource_system())){
//			strBuf.append("缺少必填字段source_system.");
//		}
//		if(StringUtils.isEmpty(req.getReceive_system())){
//			strBuf.append("缺少必填字段receive_system.");
//		}
		if(StringUtils.isEmpty(req.getFlowInstId())){
			strBuf.append("缺少必填字段flowInstId.");
		}
		if(StringUtils.isEmpty(req.getIsSuccess())){
			strBuf.append("缺少必填字段isSuccess.");
		}else{
			strBuf.append(checkIsSuccess(req.getIsSuccess()));
		}
		if(StringUtils.isEmpty(req.getFailureDesc())){
			strBuf.append("缺少必填字段failureDesc.");
		}
		if(StringUtils.isEmpty(req.getFailureCode())){
			strBuf.append("缺少必填字段failureCode.");
		}
		return strBuf.toString();

	}
	
	/**
	 * 检验某时间字段是否符合某时间格式
	 * @param key
	 * @param value
	 * @param format
	 * @return
	 */
	private static String checkTimeFormat(String key,String value,String format){
		StringBuffer strBuf = new StringBuffer();
		if(!DateUtil.isDateFormat(value,format)){
			strBuf.append(key+":"+value+"不符合yyyymmddhhmiss格式.");
		}
		return strBuf.toString();
	}

	/**
	 * 检验isSuccess编码只能是	T-成功  F-失败
	 * @param isSuccess
	 * @return
	 */
	private static String checkIsSuccess(String isSuccess){
		StringBuffer strBuf = new StringBuffer();
		if(!("T".equals(isSuccess)||"F".equals(isSuccess))){
			strBuf.append("isSuccess编码只能是T-成功  F-失败.");
		}
		return strBuf.toString();
	}

}
