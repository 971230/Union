package com.ztesoft.api.internal.utils;

/*import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.context.webcontext.impl.WebSessionContextImpl;*/

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-17 16:33
 * To change this template use File | Settings | File Templates.
 * 
 * Dirty代码太多了，统一搬迁到CoreThreadLocalHolder线程变量中，静止后续使用ApiThreadLocalHolder类
 */
@Deprecated
public class ApiThreadLocalHolder {
	//private Logger logger= LoggerFactory.getLogger(getClass());

   // private static ThreadLocal<WebSessionContext> SessionContextThreadLocalHolder = new ThreadLocal<WebSessionContext>();
//    private static ThreadLocal<ZteCommonData> entityParamsHolder = new ThreadLocal<ZteCommonData>();
//    private static ThreadLocal<HttpServletRequest> HttpRequestThreadLocalHolder = new ThreadLocal<HttpServletRequest>();
//    private static ThreadLocal<HttpServletResponse> HttpResponseThreadLocalHolder = new ThreadLocal<HttpServletResponse>();
//
//
//    private static class ApiThreadLocalHolderClassLoader {
//        protected static final ApiThreadLocalHolder instance = new ApiThreadLocalHolder();
//    }
//
//    public static ApiThreadLocalHolder getInstance() {
//
//
//        return ApiThreadLocalHolderClassLoader.instance;
//    }
//
//    public ZteCommonData getZteCommonData() {
//        if (entityParamsHolder.get() == null) {
//            entityParamsHolder.set(new ZteCommonData());
//        } else {
//        }
//        return entityParamsHolder.get();
//    }
//
//    public void setHttpRequest(HttpServletRequest request){
//        HttpRequestThreadLocalHolder.set(request);
//    }
//
//    public HttpServletRequest getHttpRequest(){
//        return  HttpRequestThreadLocalHolder.get();
//    }
//
//
//    public void setHttpResponse(HttpServletResponse response){
//        HttpResponseThreadLocalHolder.set(response);
//    }
//
//    public HttpServletResponse getHttpResponse(){
//        return HttpResponseThreadLocalHolder.get();
//    }
//
//    public void clear(){
//        entityParamsHolder.remove();
//        HttpRequestThreadLocalHolder.remove();
//        HttpResponseThreadLocalHolder.remove();
//    }
   /* public void setSessionContext(WebSessionContext context) {
        SessionContextThreadLocalHolder.set(context);
    }

    public void destorySessionContext() {
        WebSessionContext context = SessionContextThreadLocalHolder.get();
        if (context != null) {
            context.destory();
        }
    }

    public   WebSessionContext  getSessionContext() {
        if (SessionContextThreadLocalHolder.get() == null) {
		    logger.debug("create new webSessionContext.");
            SessionContextThreadLocalHolder.set(new WebSessionContextImpl());
        }else{
				logger.debug(" webSessionContext not null and return ...");
        }
        return SessionContextThreadLocalHolder.get();
    }*/
}
