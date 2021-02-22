package zte.net.common.annontion.context.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import params.ZteError;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.common.ServiceMethodDefinition;
import com.ztesoft.rop.common.ServiceMethodHandler;
import com.ztesoft.rop.session.SessionManager;
import commons.CommonTools;

/**
 * 环节动作处理逻辑
 *
 * @author wui
 *
 */
@SuppressWarnings( { "unused", "unchecked" })
public class DefaultActionBeanDefine{

   // protected  Logger logger = LoggerFactory.getLogger(DefaultServiceContext.class);
    private  final Map<String, ServiceMethodHandler> serviceHandlerMap = new HashMap<String, ServiceMethodHandler>();

    private final Set<String> serviceMethods = new HashSet<String>();

    private static boolean doneLoad  = false;

    private boolean signEnable;

    private SessionManager sessionManager;

    private static List<String>  beanNames = new ArrayList<String>();

    public static DefaultActionBeanDefine getInstance(){
        return DefaultCmsBeanDefineLoader.instance;
    }

    private static class DefaultCmsBeanDefineLoader {
        public static DefaultActionBeanDefine instance = new DefaultActionBeanDefine();
    }


    public DefaultActionBeanDefine(){
    	if(doneLoad)
    		return;
        String[] beanNames = ApiContextHolder.getApplicationContext().getBeanNamesForType(Object.class);
        //默认启动时装载一次
        for (String beanName :beanNames) {
            registerFromContext(beanName);
        }
        doneLoad = true;
    }

    public void addServiceMethod(String methodName, String version, ServiceMethodHandler serviceMethodHandler) {
        serviceMethods.add(methodName);
        serviceHandlerMap.put(ServiceMethodHandler.methodWithVersion(methodName, version), serviceMethodHandler);
    }

    public ServiceMethodHandler getServiceMethodHandler(String methodName, String version) {
    	
        ServiceMethodHandler serviceMethodHandler = serviceHandlerMap.get(ServiceMethodHandler.methodWithVersion(methodName, version));
        if(serviceMethodHandler == null){
            CommonTools.addError(new ZteError("-1","找不到方法"));
        }
        return serviceMethodHandler;
    }
    public void registerFromContext(final String  beanName) throws BeansException {
        if(!(beanName.indexOf("TraceRule")>-1 || beanName.indexOf("EngineRule")>-1))
            return;
        Class<?> handlerType = null;
        try{
            handlerType = ApiContextHolder.getBean(beanName).getClass();
        }catch (Exception e) {}
        if(handlerType == null){
            return;
        }
        ReflectionUtils.doWithMethods(handlerType, new ReflectionUtils.MethodCallback() {
                    @Override
					public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                        ReflectionUtils.makeAccessible(method);

                        ZteMethodAnnontion urlPathMethod = method.getAnnotation(ZteMethodAnnontion.class);
                        ZteServiceAnnontion urlPathService = method.getDeclaringClass().getAnnotation(ZteServiceAnnontion.class);
                        ServiceMethodDefinition  definition = buildServiceMethodDefinition(urlPathService, urlPathMethod);
                        ServiceMethodHandler serviceMethodHandler = new ServiceMethodHandler();
                        serviceMethodHandler.setServiceMethodDefinition(definition);

                        serviceMethodHandler.setHandler(ApiContextHolder.getBean(beanName)); //handler
                        serviceMethodHandler.setHandlerMethod(method);
                        addServiceMethod(definition.getMethod(), definition.getVersion(), serviceMethodHandler);
                    }
                },
                new ReflectionUtils.MethodFilter() {
                    @Override
					public boolean matches(Method method) {
                        return AnnotationUtils.findAnnotation(method, ZteMethodAnnontion.class) != null;
                    }
                }

        );
    }


   
    private ServiceMethodDefinition buildServiceMethodDefinition(ZteServiceAnnontion serviceMethodBean, ZteMethodAnnontion serviceMethod) {
        ServiceMethodDefinition definition = new ServiceMethodDefinition();
        definition.setMethod(serviceMethod.path());
        definition.setMethodTitle(serviceMethod.name());
        definition.setMethodGroupTitle(serviceMethod.group_name());
        definition.setIgnoreSign(serviceMethod.page_show());
        definition.setVersion("1.0");
        return definition;
    }



    public Map<String, ServiceMethodHandler> getAllServiceMethodHandlers() {
        return serviceHandlerMap;
    }


    public boolean isValidMethod(String methodName) {
        return serviceMethods.contains(methodName);
    }

    public boolean isValidMethodVersion(String methodName, String version) {
        return serviceHandlerMap.containsKey(ServiceMethodHandler.methodWithVersion(methodName, version));
    }


    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

}