package com.ztesoft.rop.route;

import com.ztesoft.rop.common.Interceptor;
import com.ztesoft.rop.common.RopContext;
import com.ztesoft.rop.common.ThreadFerry;
import com.ztesoft.rop.event.RopEventListener;
import com.ztesoft.rop.security.InvokeTimesController;
import com.ztesoft.rop.session.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.format.support.FormattingConversionService;

import java.util.concurrent.ThreadPoolExecutor;

public interface ServiceRouter {
	
    void service(Object request, Object response);

    void startup();

    void shutdown();

    RopContext getRopContext();

    
    void setApplicationContext(ApplicationContext applicationContext);

 
    void addInterceptor(Interceptor interceptor);

   
    void addListener(RopEventListener listener);

    
    void setSecurityManager(com.ztesoft.rop.security.SecurityManager  securityManager);

    void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor);

    
    void setSignEnable(boolean signEnable);

  
    void setServiceTimeoutSeconds(int serviceTimeoutSeconds);

   
    void setExtErrorBasename(String extErrorBasename);

 
    void setFormattingConversionService(FormattingConversionService conversionService);

    void setSessionManager(SessionManager sessionManager);

   
    void setInvokeTimesController(InvokeTimesController invokeTimesController);

 
    void setThreadFerryClass(Class<? extends ThreadFerry> threadFerryClass);
}
