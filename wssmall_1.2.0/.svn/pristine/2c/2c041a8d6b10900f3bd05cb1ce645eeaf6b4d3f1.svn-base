package params;

import java.sql.Timestamp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteRequest;

public class CallerLogReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="参数",type="String",isNecessary="Y",desc="参数")
	private Object parameters;
	@ZteSoftCommentAnnotationParam(name="参数对象",type="String",isNecessary="Y",desc="参数对象")
	private String resultString;
	@ZteSoftCommentAnnotationParam(name="请求串",type="String",isNecessary="Y",desc="请求串")
	private String requestString;
	@ZteSoftCommentAnnotationParam(name="返回串",type="String",isNecessary="Y",desc="返回串")
	private String responeString;
	private String failure;
	private String operationCode;
	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="请求时间")
	private Timestamp requestTime;
	@ZteSoftCommentAnnotationParam(name="返回时间",type="String",isNecessary="Y",desc="返回时间")
	private Timestamp responseTime;
	@ZteSoftCommentAnnotationParam(name="请求方法全路基",type="String",isNecessary="Y",desc="请求方法全路基")
	private String endpoint;
	private String api_method;
	
	
	public Object getParameters() {
		return parameters;
	}

	public void setParameters(Object parameters) {
		this.parameters = parameters;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

	public String getResponeString() {
		return responeString;
	}

	public void setResponeString(String responeString) {
		this.responeString = responeString;
	}

	public String getFailure() {
		return failure;
	}

	public void setFailure(String failure) {
		this.failure = failure;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getApi_method() {
		return api_method;
	}

	public void setApi_method(String api_method) {
		this.api_method = api_method;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(parameters==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "参数对象不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return (null == this.api_method || this.api_method.equals(""))?"sysService.logCall":api_method;
	}

	public String setApiMethodName(String api_method) {
		return this.api_method=api_method;
	}
	
	public String paramsAsString(){
		return parameters.toString();
	}
}
