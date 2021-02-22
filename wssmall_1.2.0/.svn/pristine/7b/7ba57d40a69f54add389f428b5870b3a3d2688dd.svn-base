package params;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author wui
 * 
 * dubbo,rop方式调用统一入参，出参处理对象
 * 
 * zteRequest为rop、dubbo调用的入参对象
 * 
 * zteResponse为rop、dubbo调用的出参对象
 * 
 * zteError为rop、dubbo调用的错误参数对象
 * 
 *
 */
public class ZteCommonData {
	private static Logger logger = LoggerFactory.getLogger(ZteCommonData.class);
	private ZteRequest<ZteResponse> zteRequest =null; //基础入参
    private ZteResponse zteResponse = null;//基础出参
    private ZteError zteError = null; //错误参数
    private Stack<String> appKeys = new Stack<String>();
    
    private Stack<DubboParam> dubboParams = new Stack<DubboParam>();

	public String getUserSession_id() {
        return getZteRequest()==null?null:getZteRequest().getUserSessionId();
    }

    public ZteRequest getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(ZteRequest zteRequest) {
		this.zteRequest = zteRequest;
	}
	
	public void setAppKeys(String appKey) {
		this.appKeys.push(appKey);
	}
	
	public String getAppKey() {
		String appKey = "";
		if(!appKeys.isEmpty())
			appKey = appKeys.lastElement();
		return appKey;
	}
	
	public void removeAppKey() {
		if(!appKeys.isEmpty())
			this.appKeys.pop();
	}

	
	
	public void setDubboParam(DubboParam dubboParam) {
		this.dubboParams.push(dubboParam);
	}
	
	public DubboParam getDubboParam() {
		DubboParam dubboParam = null;
		if(!dubboParams.isEmpty())
			dubboParam = dubboParams.lastElement();
		return dubboParam;
	}
	
	public void removeDubboParam() {
		if(!dubboParams.isEmpty())
			this.dubboParams.pop();
	}
	
	
	public void clearDubboParam() {
		if(!dubboParams.isEmpty())
			this.dubboParams = new  Stack<DubboParam>();
	}
	
	
	public ZteResponse getZteResponse() {
		return zteResponse;
	}

	public void setZteResponse(ZteResponse zteResponse) {
		this.zteResponse = zteResponse;
	}
    public ZteError getZteError() {
		return zteError;
	}

	public void setZteError(ZteError zteError) {
		this.zteError = zteError;
	}
	
	public static void main(String[] args) {
		ZteCommonData zteCommonData = new ZteCommonData();
		DubboParam dubboParam = new DubboParam();
		for (int i = 0; i < 3; i++) {
			dubboParam.setApp_key("appkey"+i);
			dubboParam.setSource_from("sourcefrom"+i);
			zteCommonData.setDubboParam(dubboParam);
		}
		zteCommonData.removeDubboParam();
		DubboParam dubboParam2 = zteCommonData.getDubboParam();
		logger.info(dubboParam2.getApp_key()+":"+dubboParam2.getSource_from());
	}

	public Stack<DubboParam> getDubboParams() {
		return dubboParams;
	}

	public void setDubboParams(Stack<DubboParam> dubboParams) {
		this.dubboParams = dubboParams;
	}

	
	
}
