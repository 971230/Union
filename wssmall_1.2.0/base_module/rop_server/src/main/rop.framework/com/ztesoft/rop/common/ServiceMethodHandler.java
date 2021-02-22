
package com.ztesoft.rop.common;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <pre>
 *     服务处理器的相关信息
 * </pre>
 *
 * @author 
 * @version 1.0
 */
public class ServiceMethodHandler implements Serializable{

    //处理器对象
    private Object handler;

    //处理器的处理方法
    private Method handlerMethod;

    private ServiceMethodDefinition serviceMethodDefinition;

    //处理方法的请求对象类
    private Class<? extends RopRequest> requestType = RopRequest.class;
    
    
    //处理方法的请求对象类
    private Class<? extends RopResp> respType = RopResp.class;
    

    //无需签名的字段列表
    private List<String> ignoreSignFieldNames;

    //属性类型为FileItem的字段列表
    private List<String> uploadFileFieldNames;

    //是否是实现类
    private boolean ropRequestImplType;

    public ServiceMethodHandler() {
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(Method handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public Class<? extends RopRequest> getRequestType() {
        return requestType;
    }

    public void setRequestType(Class<? extends RopRequest> requestType) {
        this.requestType = requestType;
    }

    public Class getRespTypeClass() {
        return respType;
    }

    
    public Class<? extends RopResp> getRespType() {
        return respType;
    }

    
    public void setRespType(Class<? extends RopResp> respType) {
        this.respType = respType;
    }
    

    public boolean isHandlerMethodWithParameter() {
        return this.requestType != null;
    }

    public void setIgnoreSignFieldNames(List<String> ignoreSignFieldNames) {
        this.ignoreSignFieldNames = ignoreSignFieldNames;
    }

    public List<String> getIgnoreSignFieldNames() {
        return ignoreSignFieldNames;
    }

    public ServiceMethodDefinition getServiceMethodDefinition() {
        return serviceMethodDefinition;
    }

    public void setServiceMethodDefinition(ServiceMethodDefinition serviceMethodDefinition) {
        this.serviceMethodDefinition = serviceMethodDefinition;
    }

    public static String methodWithVersion(String methodName, String version) {
        return methodName + "#" + version;
    }

    public boolean isRopRequestImplType() {
        return ropRequestImplType;
    }

    public void setRopRequestImplType(boolean ropRequestImplType) {
        this.ropRequestImplType = ropRequestImplType;
    }

    public List<String> getUploadFileFieldNames() {
        return uploadFileFieldNames;
    }

    public void setUploadFileFieldNames(List<String> uploadFileFieldNames) {
        this.uploadFileFieldNames = uploadFileFieldNames;
    }

    public boolean hasUploadFiles(){
        return uploadFileFieldNames != null && uploadFileFieldNames.size() > 0;
    }
}

