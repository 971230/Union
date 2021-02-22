
package zte.net.common.annontion.context.request;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <pre>
 *     服务处理器的相关信息
 * </pre>
 *
 * @author 
 * @version 1.0
 */
public class ZteActionRequestHandler implements Serializable{

    //处理器对象
    private Object handler;

    //处理器的处理方法
    private Method handlerMethod;

    private RequestFieldDefinition zteActionRequestFieldDefinition;


    public ZteActionRequestHandler() {
    	
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }


    public RequestFieldDefinition getZteActionRequestFieldDefinition() {
		return zteActionRequestFieldDefinition;
	}

	public void setZteActionRequestFieldDefinition(
			RequestFieldDefinition zteActionRequestFieldDefinition) {
		this.zteActionRequestFieldDefinition = zteActionRequestFieldDefinition;
	}

	public static String methodWithVersion(String methodName, String version) {
        return methodName + "#" + version;
    }

   
}

