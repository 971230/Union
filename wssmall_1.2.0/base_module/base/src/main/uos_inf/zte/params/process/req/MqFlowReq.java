package zte.params.process.req;

import java.util.HashMap;
import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class MqFlowReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "表名", type = "String", isNecessary = "Y", desc = "表名")
	private String tableName;
	
	@ZteSoftCommentAnnotationParam(name = "流程实例id", type = "String", isNecessary = "N", desc = "流程实例id")
	private String processInstanceid;
	
	@ZteSoftCommentAnnotationParam(name = "处理方法", type = "String", isNecessary = "Y", desc = "处理方法")
	private String dealMethod;
	
	@ZteSoftCommentAnnotationParam(name = "方法的入参", type = "Object", isNecessary = "Y", desc = "方法的入参")
	private Map<String,String> methodParams = new HashMap<String,String>();
	
	public String getProcessInstanceid() {
		return processInstanceid;
	}

	public void setProcessInstanceid(String processInstanceid) {
		this.processInstanceid = processInstanceid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDealMethod() {
		return dealMethod;
	}

	public void setDealMethod(String dealMethod) {
		this.dealMethod = dealMethod;
	}

	public Map<String, String> getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(Map<String, String> methodParams) {
		this.methodParams = methodParams;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.params.process.req.MqSend";
	}

}
