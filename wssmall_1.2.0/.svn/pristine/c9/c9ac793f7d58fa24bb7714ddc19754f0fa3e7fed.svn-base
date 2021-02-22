package com.ztesoft.rop.webservice;

import params.ZteRequest;
import params.orderqueue.req.OrderCollectionReq;
import params.orderqueue.resp.OrderCollectionResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;


/**
 * @Description WebService入口 
 * @author  zhangJun
 * @date    2015-3-12
 * @version 1.0
 */
public class WebServiceMoreWayHandle { 
	
	/**
	 * WebService入口
	 * @param type 请求格式
	 * @param key 请求key值
	 * @param sec 请求密钥
	 * @param serv 方法名
	 * @param version 版本号
	 * @param req 请求报文
	 * @return 返回报文
	 */
	public String process(String type,String key, String sec, String serv, String version, String req) {
		IWSProcess processor = IWSProcess.getProcessor(type);
		ZteRequest zteRequest =null;
		try {
			//1、校验参数
			if(StringUtils.isEmpty(key)
					||StringUtils.isEmpty(sec)
					||StringUtils.isEmpty(serv)
					||StringUtils.isEmpty(version)
					||StringUtils.isEmpty(req)){
				return processor.getMsg(IWSProcess.MSG_CODE_ERROR,IWSProcess.ERROR_REQ_PARAM);
			}
			
			//2、调用订单归集总能力
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			OrderCollectionReq ocReq=new OrderCollectionReq();
			ocReq.setType(type);
			ocReq.setKey(key);
			ocReq.setSec(sec);
			ocReq.setVersion(version);
			ocReq.setServ(serv);
			ocReq.setReq(req);
			OrderCollectionResp ckResp=client.execute(ocReq, OrderCollectionResp.class);
			
			//3、根据调用结果返回报文
			if (!ConstsCore.ERROR_SUCC.equals(ckResp.getError_code())) {
				return processor.getMsg(IWSProcess.MSG_CODE_ERROR,ckResp.getError_msg() );
			} 
			return ckResp.getRespStr();
		} catch (Exception e) {//未知异常
			e.printStackTrace();
			return processor.getMsg(IWSProcess.MSG_CODE_ERROR, e.getMessage());
		}
		
	}

}
