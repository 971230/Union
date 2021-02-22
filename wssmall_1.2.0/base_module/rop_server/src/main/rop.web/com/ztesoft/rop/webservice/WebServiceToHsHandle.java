package com.ztesoft.rop.webservice;

import params.ZteRequest;
import params.ZteResponse;
import services.DefaultServiceContext;
import zte.params.orderctn.req.HSOrderCtnB2BReq;
import zte.params.orderctn.req.HSOrderCtnReq;

import com.ztesoft.api.DefaultZteDubboClient;
import com.ztesoft.api.consts.ApiConsts;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.rop.common.ServiceMethodHandler;

/**
 * @author shusx
 * @version 创建时间：2016-7-20
 */
public class WebServiceToHsHandle {
	
	/**
	 * WebService入口
	 * @param type 请求格式
	 * @param key 请求key值
	 * @param sec 请求密钥
	 * @param serv 方法名
	 * @param version 版本号
	 * @param verifyCode 鉴权密钥
	 * @param req 请求报文
	 * @return 返回报文
	 */
	public String process(String type,String key, String sec, String serv, String version, String verifyCode, String req) {
		IWSProcess processor = IWSProcess.getProcessor(type);
		ZteRequest zteRequest =null;
		try {
			//校验参数
			if(StringUtils.isEmpty(key)
					||StringUtils.isEmpty(sec)
					||StringUtils.isEmpty(serv)
					||StringUtils.isEmpty(version)
					||StringUtils.isEmpty(verifyCode)
					||StringUtils.isEmpty(req)){
				return processor.getMsgToHs(IWSProcess.MSG_CODE_ERROR_HS,IWSProcess.ERROR_REQ_PARAM);
			}
			
			// 请求鉴权
			String checkword = "huasheng";
			String vc = processor.getVerifyCode(req + checkword);
			System.out.println("华盛请求报文：" + req + "\n鉴权码：" + vc);
			if (!StringUtil.equals(vc, verifyCode)) {
				return processor.getMsgToHs(IWSProcess.MSG_CODE_ERROR_HS,IWSProcess.ERROR_VERIFY_CODE_FORMAT);
			}
			
			DefaultServiceContext context = DefaultServiceContext.getInstance();
			// 检测服务是否存在
			if (context.isValidMethodVersion(serv, version)) {
				//获取方法
				ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(serv, version);
				Class zteReqClass =serviceMethodHandler.getRequestType();
				Class zteResClass = serviceMethodHandler.getRespType();
				
				//dubbo客户端
				DefaultZteDubboClient client = new DefaultZteDubboClient(key, sec);
				
				//请求对象转换
				if("zte.net.iservice.impl.ZteOrderCtnOpenService.hsOrderCtn".equals(serv)){
					HSOrderCtnReq hsReq = new HSOrderCtnReq();
					hsReq.setReqMsgStr(req);
					zteRequest = hsReq;
				}else if("zte.net.iservice.impl.ZteOrderCtnOpenService.hsOrderCtnB2B".equals(serv)){
					HSOrderCtnB2BReq hsReq = new HSOrderCtnB2BReq();
					hsReq.setReqMsgStr(req);
					zteRequest = hsReq;
				}else{
					zteRequest = processor.getZteRequest(req, zteReqClass);
				}
				if(zteRequest==null){//转换出错
					return processor.getMsgToHs(IWSProcess.MSG_CODE_ERROR_HS, IWSProcess.ERROR_REQ_FORMAT);
				}
				//dubbo调用
				ZteResponse zteResponse =  client.execute(zteRequest, zteResClass);
				
				if (zteResponse != null && ApiConsts.ERROR_FAIL.equals(zteResponse.getError_code())) {
					return processor.getMsgToHs(IWSProcess.MSG_CODE_ERROR_HS, zteResponse.getError_msg());
				}
				
				//返回对象转换
				String ret = processor.getZteResponse(zteResponse);
				return ret.replaceAll("\n", "").replaceAll("\r\n", "");
			} else {//服务找不到
				return processor.getMsgToHs(IWSProcess.MSG_CODE_ERROR_HS, IWSProcess.ERROR_SERV_NOT_FOUND);
			}
			
		} catch (Exception e) {//未知异常
			e.printStackTrace();
			return processor.getMsgToHs(IWSProcess.MSG_CODE_ERROR_HS, e.getMessage());
		}
	}
}
