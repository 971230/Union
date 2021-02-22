package com.ztesoft.rop.webservice;

import params.ZteRequest;
import params.ZteResponse;
import services.DefaultServiceContext;

import com.ztesoft.api.DefaultZteDubboClient;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.rop.common.ServiceMethodHandler;

/**
 * @author Reason.Yea
 * @version 创建时间：2014-5-22
 */
public class WebServiceHandle {
	
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
			//校验参数
			if(StringUtils.isEmpty(key)
					||StringUtils.isEmpty(sec)
					||StringUtils.isEmpty(serv)
					||StringUtils.isEmpty(version)
					||StringUtils.isEmpty(req)){
				return processor.getMsg(IWSProcess.MSG_CODE_ERROR,IWSProcess.ERROR_REQ_PARAM);
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
				zteRequest = processor.getZteRequest(req, zteReqClass);
				if(zteRequest==null){//转换出错
					return processor.getMsg(IWSProcess.MSG_CODE_ERROR, IWSProcess.ERROR_REQ_FORMAT);
				}
				//dubbo调用
				ZteResponse zteResponse =  client.execute(zteRequest, zteResClass);
				//返回对象转换
				String ret = processor.getZteResponse(zteResponse);
				return ret;
			} else {//服务找不到
				return processor.getMsg(IWSProcess.MSG_CODE_ERROR, IWSProcess.ERROR_SERV_NOT_FOUND);
			}
			
		} catch (Exception e) {//未知异常
			e.printStackTrace();
			return processor.getMsg(IWSProcess.MSG_CODE_ERROR, e.getMessage());
		}
	}

}
