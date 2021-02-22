package context;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import params.ZteBusiRequest;
import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;
import services.DefaultServiceContext;
import utils.CoreThreadLocalHolder;

import com.ztesoft.api.consts.ApiConsts;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.rop.common.ServiceMethodHandler;

import commons.CommonTools;

/**
 * 
 * @author wui Web请求上下文
 * 
 */
public abstract class WebContext {
	private static Logger logger = Logger.getLogger(WebContext.class);
    
	@SuppressWarnings("unchecked")
	public  void initContext(ZteRequest request){

	
	}
	//对象执行 dubbo调用
	@SuppressWarnings({ "unchecked", "static-access" })
	public void execContext(ZteRequest zteRequest){
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(zteRequest); //统一设置一次线程变量，解决非dubbo本地调用问题
		CoreThreadLocalHolder.getInstance().getZteCommonData().setAppKeys(zteRequest.getAppKey());
		DefaultServiceContext context =DefaultServiceContext.getInstance();
		String methodName = StringUtil.isEmpty(zteRequest.getApiMethodName())?zteRequest.getMethod():zteRequest.getApiMethodName();
		String version = zteRequest.getVersion();
		ZteResponse zteResponse = null;
		try{
			
			if(!StringUtil.isEmpty(zteRequest.userSessionId)){
				
				//设置回话延迟半个小时
				Member member = UserServiceFactory.getUserService().getCurrentMember();
				if(member !=null && zteRequest.getUserSessionId().equals(member.getSessionId())){ //同一个会员，设置会员延迟版本小时，防止session失效
					ThreadContextHolder.getCacheSessionContext(zteRequest.getUserSessionId()).setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
				}
			}
			//检测方法是否存在
			if(context.isValidMethodVersion(methodName, version))
			{
				long begin = System.currentTimeMillis();
				ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(methodName, version);
                zteResponse =(ZteResponse) serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler(),zteRequest);
                long end = System.currentTimeMillis();
                if(zteRequest.getApiMethodName().equals("zte.orderService.orderStandard.push"))
					logger.info("反射调用时间:"+(end-begin));
            }else{
            	CommonTools.addError("-1", "找不到服务::服务名称"+methodName+":版本："+version);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			String msg =e.getMessage();
			e.printStackTrace();
			ZteError et =  CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
			if(et!=null){
				CommonTools.addError(et);
			}else{
				 if (e instanceof InvocationTargetException) {
		             InvocationTargetException inve = (InvocationTargetException) e;
		             CommonTools.addError(new ZteError(ApiConsts.ERROR_FAIL,inve.getTargetException().getMessage()));
		         }
		     }
			//add by wui对外只暴露简单的异常信息，详细的异常不输出
             CommonTools.addError(new ZteError(ApiConsts.ERROR_FAIL, "==>调用"+methodName+"失败，请联系对端厂商,详细错误："+msg));
        }finally{
        	CoreThreadLocalHolder.getInstance().getZteCommonData().setZteResponse(zteResponse); //设置线程变量对象
      		if(!StringUtil.isEmpty(zteRequest.userSessionId) && CoreThreadLocalHolder.getInstance().getZteCommonData().getZteResponse() !=null)
      			CoreThreadLocalHolder.getInstance().getZteCommonData().getZteResponse().setUserSessionId((zteRequest.userSessionId));
      		
      		
        }
	}
	
	
	@SuppressWarnings("unchecked")
	public  void destoryContext(ZteRequest request){ //add by wui有异常立即销毁处理
		CoreThreadLocalHolder.getInstance().clear();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public  void initContext(ZteBusiRequest request){

	
	}
	//对象执行 dubbo调用
	@SuppressWarnings({ "unchecked", "static-access" })
	public void execContext(ZteBusiRequest zteRequest){
		
//		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(zteRequest); //统一设置一次线程变量，解决非dubbo本地调用问题
//		CoreThreadLocalHolder.getInstance().getZteCommonData().setAppKeys(zteRequest.getAppKey());
		DefaultServiceContext context =DefaultServiceContext.getInstance();
		String methodName  =zteRequest.getApiMethodName();
		String version = "1.0";//zteRequest.getVersion();
		ZteResponse zteResponse = null;
		try{
			
//			if(!StringUtil.isEmpty(zteRequest.userSessionId)){
//				
//				//设置回话延迟半个小时
//				Member member = UserServiceFactory.getUserService().getCurrentMember();
////				if(member !=null && zteRequest.getUserSessionId().equals(member.getSessionId())){ //同一个会员，设置会员延迟版本小时，防止session失效
////					ThreadContextHolder.getCacheSessionContext(zteRequest.getUserSessionId()).setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
////				}
//			}
			//检测方法是否存在
			if(context.isValidMethodVersion(methodName, version))
			{
				ServiceMethodHandler serviceMethodHandler = context.getServiceMethodHandler(methodName, version);
                zteResponse =(ZteResponse) serviceMethodHandler.getHandlerMethod().invoke(serviceMethodHandler.getHandler(),zteRequest);
            }else{
            	CommonTools.addError("-1", "找不到服务");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			String msg =e.getMessage();
			e.printStackTrace();
			ZteError et =  CoreThreadLocalHolder.getInstance().getZteCommonData().getZteError();
			if(et!=null){
				CommonTools.addError(et);
			}else{
				 if (e instanceof InvocationTargetException) {
		             InvocationTargetException inve = (InvocationTargetException) e;
		             CommonTools.addError(new ZteError(ApiConsts.ERROR_FAIL,inve.getTargetException().getMessage()));
		         }
		     }
			//add by wui对外只暴露简单的异常信息，详细的异常不输出
             CommonTools.addError(new ZteError(ApiConsts.ERROR_FAIL, "==>调用"+methodName+"失败，请联系对端厂商,详细错误："+msg));
        }finally{
        	CoreThreadLocalHolder.getInstance().getZteCommonData().setZteResponse(zteResponse); //设置线程变量对象
      		
        }
	}
	
	
	@SuppressWarnings("unchecked")
	public  void destoryContext(ZteBusiRequest request){ //add by wui有异常立即销毁处理
		CoreThreadLocalHolder.getInstance().clear();
	}
}