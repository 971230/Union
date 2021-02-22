package com.ztesoft.net.server.servlet.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.wyg.req.MallOpIDSynInfoReq;
import zte.net.ecsord.params.wyg.resp.MallOpIDSynInfoResp;

import com.google.gson.Gson;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.server.servlet.ICommonServlet;

/**
 * 商城工号同步接口
 * 
 * @author Rapon
 */
public class MallOperatorIDSynServlet implements ICommonServlet {
	private final static Log log = LogFactory.getLog(MallOperatorIDSynServlet.class);
	private  static String interfaceName="【商城工号同步接口】";


	private String targetReqType;
	public MallOperatorIDSynServlet(String targetReqType) {
		this.targetReqType = targetReqType;
	}

	/**
	 * 商城工号同步接口
	 */
	@Override
	public String service(String reqString) throws Exception {
		log.info(interfaceName+"请求的字符串："+reqString);
		log.info(interfaceName+"请求的字符串："+reqString);
		String returnString = null;
		MallOpIDSynInfoResp zteResponse = new MallOpIDSynInfoResp();
		zteResponse.setResp_code("1");//默认失败
		MallOpIDSynInfoReq req = null;
		if(StringUtils.isNotBlank(reqString)) {
			Gson gson = new Gson();
			try {
				req = gson.fromJson(reqString, MallOpIDSynInfoReq.class);
				if(!targetReqType.equals(req.getReqType())){
					zteResponse.setResp_msg("请求类型[reqType]非法!");
				}else if(!CommonDataFactory.getInstance().checkCommonServletReq(req.getReqId(),req.getReqType())){
					zteResponse.setResp_msg("您无权访问此接口!");
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
		String StaffInfo = JsonUtil.toJson(zteResponse.getStaffInfo()).toString();
		returnString = "{\"serial_no\":\""+serial_no+"\",\"time\":\""+time+"\",\"resp_code\":\""+zteResponse.getResp_code()+"\",\"resp_msg\":\""+resp_msg+"\",\"StaffInfo\":"+StaffInfo+"}";
		log.info(interfaceName+"返回的字符串："+returnString);
		log.info(interfaceName+"返回的字符串："+returnString);
		return returnString;
	}

	/**
	 * 具体业务处理
	 * @param opidSynProtocol
	 * @return
	 */
	public MallOpIDSynInfoResp dealOtherDealUser(MallOpIDSynInfoReq req)throws Exception  {
		MallOpIDSynInfoResp zteResponse = new MallOpIDSynInfoResp();
		zteResponse.setResp_code("1");//默认失败
		try {
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			zteResponse = client.execute(req, MallOpIDSynInfoResp.class);//业务处理
		} catch (Exception e) {
			log.info(interfaceName+"接口处理错误描述:"+e.getMessage());
			zteResponse.setResp_msg("接口处理错误");
		}
		return zteResponse;
	}
}
