
package com.ztesoft.rop.route.impl;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.HttpApiDateReq;

import com.rop.core.spring.SpringContextHolder;
import com.ztesoft.api.DefaultZteDubboClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.mall.core.model.HttpReqData;
import com.ztesoft.rop.common.MessageFormat;
import com.ztesoft.rop.common.RopRequestContext;
import com.ztesoft.rop.common.ServiceMethodAdapter;
import com.ztesoft.rop.common.ServiceMethodHandler;
import commons.CommonTools;


/**
 * <pre>
 *    通过该服务方法适配器调用目标的服务方法
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class AnnotationServiceMethodAdapter implements ServiceMethodAdapter {

    @Resource
//    private IAppLocalService appLocalService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 调用ROP服务方法
     *
     * @param ropRequestContext
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public Object invokeServiceMethod(RopRequestContext ropRequestContext) {
        try {
//            if(null==appLocalService){
//            	appLocalService= SpringContextHolder.getBean("appLocalService");
//            }
            //分析上下文中的错误
            ServiceMethodHandler serviceMethodHandler = ropRequestContext.getServiceMethodHandler();
            if (logger.isDebugEnabled()) {
                logger.debug("执行" + serviceMethodHandler.getHandler().getClass() +
                        "." + serviceMethodHandler.getHandlerMethod().getName());
            }
            //反射调用方法
           //AppThreadLocalHolder.set(appLocalService.validate(ropRequestContext.getAppKey()));
            Object obj=null;
            
            //add by wui走fastjson格式的数据,rop里面直接调用dubbo处理请求,用于数据统一管控处理
            if(MessageFormat.fjson.name().equals(ropRequestContext.getFormat()) || MessageFormat.httpjson.name().equals(ropRequestContext.getFormat()))
            {
	            DefaultZteDubboClient client = new DefaultZteDubboClient(ropRequestContext.getAppKey(),ropRequestContext.getSign());
	            //Class zteResponse = ropRequestContext.getServiceMethodHandler().getRespTypeClass();
	            Class zteResponse = ropRequestContext.getServiceMethodHandler().getRespType();
	        	ZteRequest zteRequest = (ZteRequest)ropRequestContext.getRopRequest();
	        	zteRequest.setRopRequestContext(null);
	            obj = client.execute(zteRequest,zteResponse);
	            
	           //插入http日志信息
	            try{
	            	if(MessageFormat.fjson.name().equals(ropRequestContext.getFormat()))
	            		insertHttpApi(ropRequestContext,zteRequest.getMethod(), zteRequest,(ZteResponse)obj); //异常捕获
	            }catch (Exception e) {
					// TODO: handle exception
				}
	    		
            }else if(MessageFormat.httpxml.name().equals(ropRequestContext.getFormat())){
            	DefaultZteDubboClient client = new DefaultZteDubboClient(ropRequestContext.getAppKey(),ropRequestContext.getSign());
	            Class zteResponse = ropRequestContext.getServiceMethodHandler().getRespTypeClass();
	        	ZteRequest zteRequest = (ZteRequest)ropRequestContext.getRopRequest();
	        	zteRequest.setRopRequestContext(null);
	            obj = client.execute(zteRequest,zteResponse);
	            
            }else {
	            if (serviceMethodHandler.isHandlerMethodWithParameter()) {
	                obj=serviceMethodHandler.getHandlerMethod().invoke(
	                        serviceMethodHandler.getHandler(), ropRequestContext.getRopRequest());
	            } else {
	                obj=serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler());
	            }
            }
            //AppThreadLocalHolder.clear();
            return obj;
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                InvocationTargetException inve = (InvocationTargetException) e;
                throw new RuntimeException(inve.getTargetException());
            } else {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * 提交http data请求数据
     * @param ropRequestContext
     * @param method_name
     * @param zteRequest
     * @param zteResponse
     */
    @SuppressWarnings("unchecked")
	public void insertHttpApi(RopRequestContext ropRequestContext,String method_name,ZteRequest zteRequest,ZteResponse zteResponse){
    	String httpReqJson = "",httpRespJson;
		Map httpReqMap =new HashMap();
		Map httpRespMap =new HashMap();
		try {
			BeanUtils.beanToMap(httpReqMap,zteRequest);
			httpReqJson = CommonTools.beanToJson(httpReqMap);
			BeanUtils.beanToMap(httpRespMap,zteResponse);
			httpRespJson = CommonTools.beanToJson(httpRespMap);
			
			HttpApiDateReq httpApiDateReq = new HttpApiDateReq();
			HttpReqData httpReqData = new HttpReqData();
//			httpReqData.setMethod(method_name);
			httpReqData.setCreate_time("sysdate");
			httpReqData.setHttp_req_exjson(httpReqJson);
			httpReqData.setHttp_resp_exjson(httpRespJson);
			httpReqData.setMethod_name(zteRequest.getMethod());
			httpApiDateReq.setHttpReqData(httpReqData);
			
			//数据插入处理
			IOrderServices orderServices = SpringContextHolder.getBean("orderServices");
			orderServices.addHttpData(httpApiDateReq);
	            
		} catch (Exception e) {	
			//e.printStackTrace();
		}
	}
    
}

