package com.ztesoft.common.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.log4j.Logger;

import params.ZteError;
import utils.CoreThreadLocalHolder;
import zte.net.common.annontion.context.action.DefaultActionBeanDefine;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.params.template.resp.AttrDefTableVO;

import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.rop.common.ServiceMethodHandler;

import commons.CommonTools;
import consts.ConstsCore;

public class BaseTools {
	private static Logger logger = Logger.getLogger(BaseTools.class);
	public static String getDatabaseType(){
		return EopSetting.DBTYPE;
	}
	
	public static BusiCompResponse execRule(BusiCompRequest prequest)
	{
		//组装参数
		String enginePath =prequest.getEnginePath();
		DefaultActionBeanDefine context = DefaultActionBeanDefine.getInstance();
		String methodName = enginePath,version="1.0";
		BusiCompResponse resp = new BusiCompResponse();
		try{
			CoreThreadLocalHolder.getInstance().getZteCommonData().setZteError(null);
			if (context.isValidMethodVersion(enginePath, version)) {
				ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(enginePath, version);
				long start = System.currentTimeMillis();
				logger.info("BusiCompResponse.execRule执行路径:"+prequest.getOrder_id()+":"+enginePath+":"+version+":"+serviceMethodHandler+":"+serviceMethodHandler.getHandler());
				resp =  (BusiCompResponse) serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler(),prequest); //todo
				
				long begin = System.currentTimeMillis();
//				MethodAccess access = context.getServiceMethodAccessHandler(methodName,version);
//				resp = (BusiCompResponse) access.invoke(serviceMethodHandler.getHandler(), serviceMethodHandler.getHandlerMethod().getName(), prequest);
				long end = System.currentTimeMillis();
				return resp;
			}
			//找不到服务去除,避免报错
			else {
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg(enginePath+"找不到服务");
				CommonTools.addError("-1", "找不到服务");
			}
		}catch(Exception e){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			String msg =e.getMessage();
			e.printStackTrace();
			ZteError et =  CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
			if(et!=null){
				resp.setError_msg(et.getError_msg());
			}else{
				 if (e instanceof InvocationTargetException) {
		             InvocationTargetException inve = (InvocationTargetException) e;
		             resp.setError_msg(inve.getTargetException().getMessage());
		         }
		     }
			return resp;
		}
		return resp;
	}
	
	
	/**
	 * <p>封装属性处理器的请求参数：</p>
	 * @author Qin Yingxiong
	 * @return AttrSwitchParams
	 * @param attrDefTableCache
	 * @param order_id
	 * @param busiRequest
	 * @param mapping_type
	 * @return
	 */
	public static AttrSwitchParams packageAttrSwitchParams(AttrDefTableVO attrDefTableCache, 
			String order_id, Object busiRequest, Map<String, Object> objMap, String mapping_type) {
		AttrSwitchParams params = new AttrSwitchParams();
//		
//		if(attrDefTableCache.getHandler_class() != null) {
//			//根据配置的处理器前缀，路由到本地或者核心开放服务类
//			String classPrefix = "core";
//			if(attrDefTableCache.getHandler_class().startsWith(classPrefix)) {//核心
//				params.setHander_class(attrDefTableCache.getHandler_class().substring(classPrefix.length()));
//				params.setApiMethodName("zte.net.ecsord.params.attr.req.attrswitchparams.core");
//			}else {//本地
//				params.setApiMethodName("zte.net.ecsord.params.attr.req.attrswitchparams");
//			}
//		}
		
		params.setHander_class(attrDefTableCache.getHandler_class());
		params.setOrder_id(order_id);
		AttrDef attrDef = new AttrDef();
		attrDef.setField_name(attrDefTableCache.getField_name());
		params.setAttrDef(attrDef);
		params.setBusiRequest(busiRequest);
		params.setObjMap(objMap);
		params.setMapping_type(mapping_type);
		return params;
	}
	
}
