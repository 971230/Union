package services;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.parameters.AbstractRopRequest;
import com.ztesoft.rop.annotation.*;
import com.ztesoft.rop.common.*;
import com.ztesoft.rop.session.SessionManager;
import commons.CommonTools;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import params.ZteError;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 通用服务类
 *
 * @author wui
 *
 */
@SuppressWarnings( { "unused", "unchecked" })
public class DefaultServiceContext  implements RopContext{

   // protected  Logger logger = LoggerFactory.getLogger(DefaultServiceContext.class);
    private  final Map<String, ServiceMethodHandler> serviceHandlerMap = new HashMap<String, ServiceMethodHandler>();

    private final Set<String> serviceMethods = new HashSet<String>();

    private static boolean doneLoad  = false;

    private boolean signEnable;

    private SessionManager sessionManager;

    private static List<String>  beanNames = new ArrayList<String>();

    public static DefaultServiceContext getInstance(){
        return DefaultServiceContextClassLoader.instance;
    }

    private static class DefaultServiceContextClassLoader {
        public static DefaultServiceContext instance = new DefaultServiceContext();
    }


    public DefaultServiceContext(){
       // logger.debug("初始化加载方法!");
        if(doneLoad)
            return;
        String[] beanNames = ApiContextHolder.getApplicationContext().getBeanNamesForType(Object.class);
        //默认启动时装载一次
        for (String beanName :beanNames) {
            registerFromContext(beanName);
        }
        doneLoad = true;
    }

    @Override
	public void addServiceMethod(String methodName, String version, ServiceMethodHandler serviceMethodHandler) {
        serviceMethods.add(methodName);
        serviceHandlerMap.put(ServiceMethodHandler.methodWithVersion(methodName, version), serviceMethodHandler);
    }

    @Override
	public ServiceMethodHandler getServiceMethodHandler(String methodName, String version) {
        ServiceMethodHandler serviceMethodHandler = serviceHandlerMap.get(ServiceMethodHandler.methodWithVersion(methodName, version));
        if(serviceMethodHandler == null){
            CommonTools.addError(new ZteError("-1","找不到方法"));
        }
        return serviceMethodHandler;
    }
    public void registerFromContext(final String  beanName) throws BeansException {
        if(!(beanName.indexOf("OpenService")>-1))
            return;
//        logger.info("beanNamebeanNamebeanNamebeanNamebeanNamebeanNamebeanNamebeanNamebeanNamebeanName"+beanName);
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

                        ServiceMethod serviceMethod = method.getAnnotation(ServiceMethod.class);
                        ServiceMethodBean serviceMethodBean = method.getDeclaringClass().getAnnotation(ServiceMethodBean.class);

                        ServiceMethodDefinition definition = null;
                        if (serviceMethodBean != null) {
                            definition = buildServiceMethodDefinition(serviceMethodBean, serviceMethod);
                        } else {
                            definition = buildServiceMethodDefinition(serviceMethod);
                        }
                        ServiceMethodHandler serviceMethodHandler = new ServiceMethodHandler();
                        serviceMethodHandler.setServiceMethodDefinition(definition);

                        serviceMethodHandler.setHandler(ApiContextHolder.getBean(beanName)); //handler
                        serviceMethodHandler.setHandlerMethod(method);
                        if (method.getParameterTypes().length > 1) {//handler method's parameter
                            CommonTools.addFailError(method.getDeclaringClass().getName() + "." + method.getName()
                                    + "的入参只能是" + RopRequest.class.getName() + "或无入参。");
                        } else if (method.getParameterTypes().length == 1) {
                            Class<?> paramType = method.getParameterTypes()[0];
                            if (ClassUtils.isAssignable(RopRequest.class, paramType)) {

                                boolean ropRequestImplType = !(paramType.isAssignableFrom(RopRequest.class) || paramType.isAssignableFrom(AbstractRopRequest.class));
                                serviceMethodHandler.setRopRequestImplType(ropRequestImplType);
                                serviceMethodHandler.setRequestType((Class<? extends RopRequest>) paramType);
                                try{
                                    serviceMethodHandler.setRespType((Class<? extends RopResp>) method.getReturnType());
                                }catch (Exception e) {
                                    // TODO: handle exception
                                }
                            }
                        } else {
                           // logger.info(method.getDeclaringClass().getName() + "." + method.getName() + "无入参");
                        }

                        serviceMethodHandler.setIgnoreSignFieldNames(getIgnoreSignFieldNames(serviceMethodHandler.getRequestType()));

                        serviceMethodHandler.setUploadFileFieldNames(getFileItemFieldNames(serviceMethodHandler.getRequestType()));

                        addServiceMethod(definition.getMethod(), definition.getVersion(), serviceMethodHandler);

                      /*  if (logger.isDebugEnabled()) {
                            logger.debug("注册服务方法：" + method.getDeclaringClass().getCanonicalName() +
                                    "#" + method.getName() + "(..)");
                        }*/
                    }
                },
                new ReflectionUtils.MethodFilter() {
                    @Override
					public boolean matches(Method method) {
                        return AnnotationUtils.findAnnotation(method, ServiceMethod.class) != null;
                    }
                }

        );
    }


    public static List<String> getIgnoreSignFieldNames(Class<? extends RopRequest> requestType) {
        final ArrayList<String> igoreSignFieldNames = new ArrayList<String>(1);
        //igoreSignFieldNames.add(SystemParameterNames.getSign());
        if (requestType != null) {
          /*  if (logger.isDebugEnabled()) {
                logger.debug("获取" + requestType.getCanonicalName() + "不需要签名的属性");
            }*/
            ReflectionUtils.doWithFields(requestType, new ReflectionUtils.FieldCallback() {
                        @Override
						public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                            igoreSignFieldNames.add(field.getName());
                        }
                    },
                    new ReflectionUtils.FieldFilter() {
                        @Override
						public boolean matches(Field field) {

                            //属性类标注了@IgnoreSign
                            IgnoreSign typeIgnore = AnnotationUtils.findAnnotation(field.getType(), IgnoreSign.class);

                            //属性定义处标注了@IgnoreSign
                            IgnoreSign varIgnoreSign = field.getAnnotation(IgnoreSign.class);

                            //属性定义处标注了@Temporary
                            Temporary varTemporary = field.getAnnotation(Temporary.class);

                            return typeIgnore != null || varIgnoreSign != null || varTemporary != null;
                        }
                    }
            );
          /*  if (igoreSignFieldNames.size() > 1 && logger.isDebugEnabled()) {
                logger.debug(requestType.getCanonicalName() + "不需要签名的属性:" + igoreSignFieldNames.toString());
            }*/
        }
        return igoreSignFieldNames;
    }

    private List<String> getFileItemFieldNames(Class<? extends RopRequest> requestType) {
        final ArrayList<String> fileItemFieldNames = new ArrayList<String>(1);
        if (requestType != null) {
        /*    if (logger.isDebugEnabled()) {
                logger.debug("获取" + requestType.getCanonicalName() + "类型为FileItem的字段名");
            }*/

            ReflectionUtils.doWithFields(requestType, new ReflectionUtils.FieldCallback() {
                        @Override
						public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                            fileItemFieldNames.add(field.getName());
                        }
                    },
                    new ReflectionUtils.FieldFilter() {
                        @Override
						public boolean matches(Field field) {
                            // return ClassUtils.isAssignable(UploadFile.class, field.getType());
                            return false;
                        }
                    }
            );

        }
        return fileItemFieldNames;
    }
    private ServiceMethodDefinition buildServiceMethodDefinition(ServiceMethod serviceMethod) {
        ServiceMethodDefinition definition = new ServiceMethodDefinition();
        definition.setMethod(serviceMethod.method());
        definition.setMethodTitle(serviceMethod.title());
        definition.setMethodGroup(serviceMethod.group());
        definition.setMethodGroupTitle(serviceMethod.groupTitle());
        definition.setTags(serviceMethod.tags());
        definition.setTimeout(serviceMethod.timeout());
        definition.setIgnoreSign(IgnoreSignType.isIgnoreSign(serviceMethod.ignoreSign()));
        definition.setVersion(serviceMethod.version());
        definition.setNeedInSession(NeedInSessionType.isNeedInSession(serviceMethod.needInSession()));
        definition.setObsoleted(ObsoletedType.isObsoleted(serviceMethod.obsoleted()));
        definition.setHttpAction(serviceMethod.httpAction());
        return definition;
    }

    private ServiceMethodDefinition buildServiceMethodDefinition(ServiceMethodBean serviceMethodBean, ServiceMethod serviceMethod) {
        ServiceMethodDefinition definition = new ServiceMethodDefinition();
        definition.setMethodGroup(serviceMethodBean.group());
        definition.setMethodGroupTitle(serviceMethodBean.groupTitle());
        definition.setTags(serviceMethodBean.tags());
        definition.setTimeout(serviceMethodBean.timeout());
        definition.setIgnoreSign(IgnoreSignType.isIgnoreSign(serviceMethodBean.ignoreSign()));
        definition.setVersion(serviceMethodBean.version());
        definition.setNeedInSession(NeedInSessionType.isNeedInSession(serviceMethodBean.needInSession()));
        definition.setHttpAction(serviceMethodBean.httpAction());
        definition.setObsoleted(ObsoletedType.isObsoleted(serviceMethodBean.obsoleted()));

        // 如果ServiceMethod所提供的值和ServiceMethodGroup不一样，覆盖之
        definition.setMethod(serviceMethod.method());
        definition.setMethodTitle(serviceMethod.title());

        if (!ServiceMethodDefinition.DEFAULT_GROUP.equals(serviceMethod.group())) {
            definition.setMethodGroup(serviceMethod.group());
        }

        if (!ServiceMethodDefinition.DEFAULT_GROUP_TITLE.equals(serviceMethod.groupTitle())) {
            definition.setMethodGroupTitle(serviceMethod.groupTitle());
        }

        if (serviceMethod.tags() != null && serviceMethod.tags().length > 0) {
            definition.setTags(serviceMethod.tags());
        }

        if (serviceMethod.timeout() > 0) {
            definition.setTimeout(serviceMethod.timeout());
        }

        if (serviceMethod.ignoreSign() != IgnoreSignType.DEFAULT) {
            definition.setIgnoreSign(IgnoreSignType.isIgnoreSign(serviceMethod
                    .ignoreSign()));
        }

        if (StringUtils.hasText(serviceMethod.version())) {
            definition.setVersion(serviceMethod.version());
        }

        if (serviceMethod.needInSession() != NeedInSessionType.DEFAULT) {
            definition.setNeedInSession(NeedInSessionType
                    .isNeedInSession(serviceMethod.needInSession()));
        }

        if (serviceMethod.obsoleted() != ObsoletedType.DEFAULT) {
            definition.setObsoleted(ObsoletedType.isObsoleted(serviceMethod
                    .obsoleted()));
        }

        if (serviceMethod.httpAction().length > 0) {
            definition.setHttpAction(serviceMethod.httpAction());
        }

        return definition;
    }



    @Override
    public Map<String, ServiceMethodHandler> getAllServiceMethodHandlers() {
        return serviceHandlerMap;
    }


    @Override
    public boolean isSignEnable() {
        return signEnable;
    }



    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setSignEnable(boolean signEnable) {
        this.signEnable = signEnable;
    }


    @Override
    public boolean isValidMethod(String methodName) {
        return serviceMethods.contains(methodName);
    }

    @Override
    public boolean isValidMethodVersion(String methodName, String version) {
        return serviceHandlerMap.containsKey(ServiceMethodHandler.methodWithVersion(methodName, version));
    }


    @Override
    public SessionManager getSessionManager() {
        return this.sessionManager;
    }



}